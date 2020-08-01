package com.holmes.springboot.security.config;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.security.entity.User;
import com.holmes.springboot.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserService userService;

    @Autowired
    public MyAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 启动自定义的登录验证逻辑
        auth.authenticationProvider(authenticationProvider);
        // 自定义用户身份验证功能
        auth.userDetailsService(userService);
    }

    /**
     * 配置认证策略
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 开启允许iframe 嵌套
        http.headers().frameOptions().disable();
        http.csrf().disable()
                // 开启跨域
                .cors()
                .and()
        // 开始一个请求权限的配置
                .authorizeRequests()
                // 配置请求匹配路径，permitAll表示所有用户都可以访问
                .antMatchers("/static/**", "/login").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                // 表示匹配的请求需要拥有USER角色的用户才能访问
//                .antMatchers("/user/**").hasRole("USER")
//                // 表示匹配的请求需要同时拥有ADMIN和DBA角色的用户才能访问
//                .antMatchers("/admin/**").access("hasRole('ADMIN') and hasRole('DBA')")
                // 其余的所有请求都需要认证才能访问
                .anyRequest().authenticated()
                .and()
                // 开始配置登录操作
                .formLogin()
                // 登录操作调用的后台接口
                .loginProcessingUrl("/login")
//                // 登录页面的访问地址
//                .loginPage("/login")
                // 用户名和密码的参数名
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录成功的处理类
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        Authentication authentication) throws IOException, ServletException {

                        User userDetails = (User) authentication.getPrincipal();
                        log.info("用户{}登录成功", userDetails.getUsername());
                        JSONObject result = new JSONObject();
                        result.put("code", "200");
                        result.put("msg", "登录成功");
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.getWriter().write(result.toJSONString());
                    }
                })
                // 登录失败的处理类
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        AuthenticationException e) throws IOException, ServletException {

                        log.info("登录失败");
                        JSONObject result = new JSONObject();
                        result.put("code", "412");
                        result.put("msg", "登录失败");
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.getWriter().write(result.toJSONString());
                    }
                })
//                // 登录成功后转向的页面
//                .defaultSuccessUrl("/success")
//                // 登录失败后转向的页面和传递的参数
//                .failureUrl("/login?error")
//                .failureForwardUrl("/login?error")
                // 所有用户都可以访问
                .permitAll()
                .and()
                // 开始配置注销操作
                .logout()
                // 注销调用的接口路径
                .logoutUrl("/logout")
                // 注销成功的处理类
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                        User userDetails = (User) authentication.getPrincipal();
                        log.info("用户{}注销成功", userDetails.getUsername());
                        JSONObject result = new JSONObject();
                        result.put("code", "200");
                        result.put("msg", "注销成功");
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.getWriter().write(result.toJSONString());
                    }
                })
//                // 注销成功跳转的页面
//                .logoutSuccessUrl("/index")
                // 所有用户都可以访问
                .permitAll();
    }
}
