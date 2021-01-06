package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.api.enums.MenuTypeEnum;
import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.api.model.permission.PermissionDTO;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.common.constant.RegexpConstant;
import com.baiyan.auth.common.model.query.KeywordQuery;
import com.baiyan.auth.common.result.Page;
import com.baiyan.auth.common.utils.CollectionUtil;
import com.baiyan.auth.common.utils.JavaUtil;
import com.baiyan.auth.common.utils.ValidationUtil;
import com.baiyan.auth.sdk.extractor.token.BearerTokenAuthExtractorProvider;
import com.baiyan.auth.sdk.util.ThreadLocalUtil;
import com.baiyan.auth.service.enums.YesNoEnum;
import com.baiyan.auth.service.mapper.UserMapper;
import com.baiyan.auth.service.model.user.bo.UserBO;
import com.baiyan.auth.service.model.user.dto.*;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.auth.service.service.*;
import com.baiyan.auth.service.utils.PasswordUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author baiyan
 * @time 2020/11/13 14:34
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TokenStoreService tokenStoreService;

    @Autowired
    AuthService authService;

    @Override
    public Page<UserListDTO> list(KeywordQuery query){
        Page<UserBO> page = getBaseMapper().page(query);
        return page.convert(UserListDTO::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(UserAddDTO dto){
        //参数校验
        ValidationUtil.notNull(dto,"user.add.dto.is.null");
        UserPO exist = getOne(Wrappers.<UserPO>lambdaQuery().eq(UserPO::getUserName, dto.getUserName()));
        ValidationUtil.isTrue(Objects.isNull(exist),"user.add.username.is.exist");
        ValidationUtil.isTrue(dto.getUserName().matches(RegexpConstant.REGEXP_USERNAME),"user.add.username.format.error");
        //用户新增
        UserPO userPO = new UserPO(dto);
        //默认密码
        String password = PasswordUtil.defaultPassword();
        userPO.setPassword(PasswordUtil.encodePassword(password));
        //插入
        getBaseMapper().insert(userPO);
        roleService.addRoles(userPO.getId(),dto.getRoleIds());
        return password;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String resetPassword(Long userId){
        //校验用户是否可编辑
        UserPO po = this.userCheck(userId,Boolean.FALSE);
        //重置密码
        String newPassword = po.resetPassword();
        log.debug("用户:{}，重置后的密码为：{}",po.getRealName(),newPassword);
        updateById(po);
        //重置密码后需要删除当下的token
        tokenStoreService.delete(userId);
        return newPassword;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateDTO dto){
        //校验用户是否可编辑
        this.userCheck(dto.getId(), Boolean.FALSE);
        UserPO target = new UserPO(dto);
        target.setId(dto.getId());
        getBaseMapper().updateById(target);
        //更新用户角色信息
        this.updateRoles(dto.getId(),dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String realName){
        UserPO po = new UserPO();
        po.setId(ThreadLocalUtil.getUser().getId());
        po.setRealName(realName);
        updateById(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UserUpdatePasswordDTO dto,String times){
        ValidationUtil.isFalse(Objects.equals(dto.getNewPassword(),dto.getOldPassword()),
                "user.update.password.is.same");
        UserPO po = this.userCheck(ThreadLocalUtil.getUser().getId(), Boolean.FALSE);
        //校验原密码
        String sourcePassword = PasswordUtil.decode(dto.getOldPassword(),times);
        PasswordUtil.checkPassword(sourcePassword,po.getPassword());
        //生成加密密码
        String newPassword = PasswordUtil.decode(dto.getNewPassword(),times);
        //校验用户密码长度与强度
        PasswordUtil.checkPasswordLength(newPassword);
        ValidationUtil.isTrue(newPassword.matches(RegexpConstant.USER_PWD),"user.update.password.format.error");
        po.updatePassword(newPassword);
        //更新
        updateById(po);
        //删除旧密码下的token数据
        tokenStoreService.delete(po.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateState(Long id, Integer state){
        UserPO userPO = this.userCheck(id, Boolean.FALSE);
        userPO.setState(UserStateEnum.getByKey(state).getKey());
        updateById(userPO);
        //用户禁用删除登录token
        JavaUtil.getJavaUtil().acceptIfCondition(Objects.equals(state,UserStateEnum.DISABLE.getKey()),
                id,
                tokenStoreService::delete);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        //校验用户是否可删除
        this.userCheck(id,Boolean.TRUE);
        //删除用
        getBaseMapper().deleteById(id);
        //删除用户角色关联关系
        roleService.deleteUserRoleByUserId(Lists.newArrayList(id));
        //删除token状态
        tokenStoreService.delete(id);
    }

    @Override
    public UserDetailDTO detail(Long id){
        ValidationUtil.notNull(id,"user.query.id.is.not.null");
        UserPO userPO = getBaseMapper().selectById(id);
        ValidationUtil.isTrue(Objects.nonNull(userPO),"user.query.is.not.exist");
        UserDetailDTO userDetailDTO = new UserDetailDTO(userPO);
        userDetailDTO.setRoleIds(roleService.getRoleIdByUserId(id));
        return userDetailDTO;
    }

    @Override
    public UserAuthDTO getAuth(HttpServletRequest request){
        //校验token
        String token = new BearerTokenAuthExtractorProvider().extract(request);
        //获取数据
        UserDTO user = authService.getUserDetail(token,UserStateEnum.AVAILABLE.getKey());
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        BeanUtils.copyProperties(user,userAuthDTO);
        //按钮权限
        JavaUtil.getJavaUtil().acceptIfCondition(CollectionUtil.isNotEmpty(user.getPermissions()),
                user.getPermissions(),value->{
                    List<String> buttons = value.stream().filter(dto -> Objects.equals(dto.getType(), MenuTypeEnum.BUTTON.getKey()))
                            .map(PermissionDTO::getCode).collect(Collectors.toList());
                    userAuthDTO.setButtons(buttons);
                });
        //菜单权限
        userAuthDTO.setMenus(menuService.tree(user.getId()));
        return userAuthDTO;
    }

    /**
     * 更新用户角色
     *
     * @param id 用户id
     * @param updateRoleIds 更新的角色id列表
     */
    private void updateRoles(Long id,List<Long> updateRoleIds){
        List<Long> roleIds =roleService.getRoleIdByUserId(id);
        //新增角色
        List<Long> add = updateRoleIds.stream().filter(e->!roleIds.contains(e)).collect(Collectors.toList());
        //删除角色
        List<Long> remove = roleIds.stream().filter(e->!updateRoleIds.contains(e)).collect(Collectors.toList());
        JavaUtil.getJavaUtil()
                .acceptIfCondition(CollectionUtil.isNotEmpty(add),add,value->roleService.addRoles(id,value))
                .acceptIfCondition(CollectionUtil.isNotEmpty(remove),remove,value->roleService.removeRoles(id,remove));
    }

    /**
     * 用户信息校验
     * @param userId 用户id
     * @return
     */
    private UserPO userCheck(Long userId,Boolean isDelete){
        UserPO po = getById(userId);
        ValidationUtil.isTrue(Objects.nonNull(po),"user.query.is.not.exist");
        JavaUtil.getJavaUtil()
                .acceptIfCondition(!isDelete, Objects.equals(po.getCanEdit(), YesNoEnum.YES.getKey()),
                value->ValidationUtil.isTrue(value,"user.is.not.allow.edit"))
                .acceptIfCondition(isDelete, Objects.equals(po.getCanDelete(), YesNoEnum.YES.getKey()),
                value->ValidationUtil.isTrue(value,"user.is.not.allow.delete")
        );
        return po;
    }
}
