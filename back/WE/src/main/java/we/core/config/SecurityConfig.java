/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import we.base.security.AuthenticationProvider;
import we.base.security.Md5PasswordEncoder;

/**
 * SpringSecurity配置
 *
 * @author cp0
 * @since 0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 异常EntryPoint
	@Resource(name = "unauthorizedEntryPoint")
	private AuthenticationEntryPoint unauthorizedEntryPoint;

	// 认证模式
	@Autowired
	private AuthenticationProvider provider;

	// 密码校验配置
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}

	// 密码加密方式
	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new Md5PasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// SpringSecurity配置
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement().disable();
		httpSecurity.csrf().disable();
		httpSecurity.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
		httpSecurity.authorizeRequests()
				.anyRequest().authenticated();
	}
}
