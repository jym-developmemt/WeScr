/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 开启资源服务
 *
 * @author cp0
 * @since 5.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// 异常EntryPoint
	@Resource(name = "unauthorizedEntryPoint")
	private AuthenticationEntryPoint unauthorizedEntryPoint;

	// 权限决策器
	@Autowired
	private AccessDecisionManager accessDecisionManager;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//session创建策略
		http.sessionManagement().disable();
		http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
		//所有请求需要认证
		http.authorizeRequests()
				.accessDecisionManager(accessDecisionManager)
				.anyRequest().authenticated();
	}
}
