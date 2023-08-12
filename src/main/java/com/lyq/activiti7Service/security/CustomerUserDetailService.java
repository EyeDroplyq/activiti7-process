package com.lyq.activiti7Service.security;

import com.lyq.activiti7Service.pojo.SysUser;
import com.lyq.activiti7Service.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 通过前端传过来的登录用户，来查询是否存在这个用户，如果存在这个用户的话，给这个用户附上权限
 * @author: lyq
 * @createDate: 2023-08-03 21:15
 * @version: 1.0
 */
@Component("customerUserDetailService")
public class CustomerUserDetailService implements UserDetailsService {
    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.findSysUserByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        Set<GrantedAuthority> authentications=new HashSet<>();
        authentications.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"));
        authentications.add(new SimpleGrantedAuthority("GROUP_MANAGER_TEAM"));
        user.setAuthorities(authentications);
        return user;
    }
}
