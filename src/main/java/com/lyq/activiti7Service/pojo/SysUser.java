package com.lyq.activiti7Service.pojo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-02 22:16
 * @version: 1.0
 */
@Data
@ApiModel("用户实体")
@Table("sys_user")
public class SysUser implements UserDetails {
    @Id(keyType = KeyType.Generator)
    private String id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码（加密）")
    private String password;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像url")
    private String imageUrl;
    @ApiModelProperty("封装用户权限")
    @Column(ignore = true)
    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
