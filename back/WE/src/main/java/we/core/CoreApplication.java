/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core;

import java.util.concurrent.Executor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用程序入口
 *
 * @author cp0
 * @since 0.0
 */
@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(CoreProperties.class)
@EnableTransactionManagement
@ComponentScan(basePackages = "we")
@MapperScan("we.*.dao")
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
	
	@Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setThreadNamePrefix("TaskThread-");
        executor.initialize();
        return executor;
    }
}
