package com.lyq.activiti7Service.service;

import com.lyq.activiti7Service.pojo.SysUser;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-02 22:35
 * @version: 1.0
 */
public interface SysUserService {
    public SysUser findSysUserByUsername(String username);
}
