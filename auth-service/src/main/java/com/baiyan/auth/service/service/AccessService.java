package com.baiyan.auth.service.service;

import com.baiyan.auth.service.model.access.dto.AccessAddDTO;
import com.baiyan.auth.service.model.access.dto.AccessDTO;
import com.baiyan.auth.service.model.access.po.AccessPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 通行证服务
 *
 * @author baiyan
 * @time 2020/12/09
 */
public interface AccessService extends IService<AccessPO> {

    /**
     * 增加三方鉴权码
     * @param dto
     * @return
     */
    String addAccess(AccessAddDTO dto);

    /**
     * 删除accessKey
     *
     * @param userId 用户id
     * @param accessKeyIds 通行证id
     */
    void deleteAccess(Long userId, List<Long> accessKeyIds);

    /**
     * 查询accessKey
     * @param userId
     * @param keyword
     * @return
     */
    List<AccessDTO> queryAccess(Long userId, String keyword);

    /**
     * 根据鉴权码获取用户id
     *
     * @param accessKey
     * @return
     */
    Long accessAuth(String accessKey);

}
