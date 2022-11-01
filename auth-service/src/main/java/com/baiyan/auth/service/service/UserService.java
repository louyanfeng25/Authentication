package com.baiyan.auth.service.service;

import com.baiyan.auth.service.model.user.dto.*;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.common.base.model.query.KeywordQuery;
import com.baiyan.common.base.result.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baiyan
 * @time 2020/11/13 14:31
 */
public interface UserService extends IService<UserPO> {

    /**
     * 根据用户名获取用户
     *
     * @param query
     * @return
     */
    Page<UserListDTO> list(KeywordQuery query);

    /**
     * 用户新增
     * @param dto
     * @return 系统生成默认密码
     */
    String add(UserAddDTO dto);

    /**
     * 用户密码重置
     * @param userId
     * @return 重置后的密码
     */
    String resetPassword(Long userId);

    /**
     * 用户修改
     * @param dto 用户信息
     */
    void update(UserUpdateDTO dto);

    /**
     * 用户修改
     * @param realName
     */
    void update(String realName);

    /**
     * 用户密码修改
     * @param dto 密码修改信息
     * @param times 加解密参数
     */
    void updatePassword(UserUpdatePasswordDTO dto,String times);

    /**
     * 用户状态修改
     * @param id 用户id
     * @param state 修改后的状态
     */
    void updateState(Long id, Integer state);

    /**
     * 用户删除
     * @param id
     */
    void delete(Long id);

    /**
     * 用户详情
     * @param id
     */
    UserDetailDTO detail(Long id);

    /**
     * 获取用户权限
     *
     * @param request
     * @return
     */
    UserAuthDTO getAuth(HttpServletRequest request);

}
