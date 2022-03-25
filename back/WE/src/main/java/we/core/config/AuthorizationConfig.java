/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import we.core.CoreProperties;

/**
 * 开启授权服务
 *
 * @author cp0
 * @since 5.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	public PasswordEncoder passwordEncoder;

	// 配置客户端
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient(websiteProp.getClientIdSite())
				.secret(passwordEncoder.encode(websiteProp.getClientSecretSite()))
				.authorizedGrantTypes("password")
				.scopes(websiteProp.getScope())
				.accessTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.refreshTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.and()
				.withClient(websiteProp.getClientIdMobile())
				.secret(passwordEncoder.encode(websiteProp.getClientSecretMobile()))
				.authorizedGrantTypes("password")
				.scopes(websiteProp.getScope())
				.accessTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.refreshTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.and()
				.withClient(websiteProp.getClientIdQywx())
				.secret(passwordEncoder.encode(websiteProp.getClientSecretQywx()))
				.authorizedGrantTypes("password")
				.scopes(websiteProp.getScope())
				.accessTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.refreshTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.and()
				.withClient(websiteProp.getClientIdSpnego())
				.secret(passwordEncoder.encode(websiteProp.getClientSecretSpnego()))
				.authorizedGrantTypes("password")
				.scopes(websiteProp.getScope())
				.accessTokenValiditySeconds(websiteProp.getTokenTimeout() * 60)
				.refreshTokenValiditySeconds(websiteProp.getTokenTimeout() * 60);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()
				.checkTokenAccess("permitAll()");
	}
}
