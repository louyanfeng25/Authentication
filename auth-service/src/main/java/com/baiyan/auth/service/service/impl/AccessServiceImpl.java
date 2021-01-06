package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.common.utils.CollectionUtil;
import com.baiyan.auth.common.utils.ValidationUtil;
import com.baiyan.auth.sdk.util.ThreadLocalUtil;
import com.baiyan.auth.service.mapper.AccessMapper;
import com.baiyan.auth.service.model.access.dto.AccessAddDTO;
import com.baiyan.auth.service.model.access.dto.AccessDTO;
import com.baiyan.auth.service.model.access.po.AccessPO;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.auth.service.service.AccessService;
import com.baiyan.auth.service.service.UserService;
import com.baiyan.auth.service.utils.TokenUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author baiyan
 * @time 2020/11/19
 */
@Service
@Slf4j
public class AccessServiceImpl extends ServiceImpl<AccessMapper, AccessPO> implements AccessService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addAccess(AccessAddDTO dto){
        //校验用户是否存在
        UserPO user = userService.getById(dto.getUserId());
        ValidationUtil.notNull(user,"user.query.is.not.exist");

        UserDTO creator = ThreadLocalUtil.getUser();
        String accessKey = TokenUtil.createToken();
        AccessPO po = AccessPO.builder()
                .userId(dto.getUserId())
                .accessKey(accessKey)
                .creator(creator.getId())
                .remark(dto.getRemark())
                .build();
        save(po);
        return accessKey;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAccess(Long userId,List<Long> accessKeyIds){
        if(CollectionUtil.isEmpty(accessKeyIds) || Objects.isNull(userId)){
            return;
        }
        getBaseMapper().delete(Wrappers.<AccessPO>lambdaQuery()
                .eq(AccessPO::getUserId,userId)
                .in(AccessPO::getId,accessKeyIds)
        );
    }

    @Override
    public List<AccessDTO> queryAccess(Long userId, String keyword){
        List<AccessPO> accessPOS = getBaseMapper().accessList(userId, keyword);
        return CollectionUtil.isEmpty(accessPOS) ? null : accessPOS.stream().map(AccessDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long accessAuth(String accessKey){
        AccessPO accessPO = getBaseMapper().selectOne(Wrappers.<AccessPO>lambdaQuery()
                .eq(AccessPO::getAccessKey, accessKey));
        ValidationUtil.notNull(accessPO,"access.key.is.useless");
        return accessPO.getUserId();
    }

}
