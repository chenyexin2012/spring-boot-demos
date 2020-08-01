package com.holmes.springboot.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements GrantedAuthority, Serializable {

    private Integer id;

    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}
