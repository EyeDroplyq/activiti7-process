package com.lyq.activiti7Service.service.impl;
import static com.lyq.activiti7Service.pojo.table.SysUserTableDef.SYS_USER;
import com.lyq.activiti7Service.pojo.SysUser;
import com.lyq.activiti7Service.mapper.SysUserMapper;
import com.lyq.activiti7Service.service.SysUserService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-02 22:35
 * @version: 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper userMapper;

    @Override
    public SysUser findSysUserByUsername(String username) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(SYS_USER.ALL_COLUMNS).from(SYS_USER).where(SYS_USER.USERNAME.eq(username));
        SysUser sysUser = userMapper.selectOneByQueryAs(queryWrapper, SysUser.class);
        return sysUser;
    }
}
