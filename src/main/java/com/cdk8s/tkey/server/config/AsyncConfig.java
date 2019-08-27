package com.cdk8s.tkey.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		int core = Runtime.getRuntime().availableProcessors();
		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 2 + 1);
		executor.setKeepAliveSeconds(5);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("custom-executor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

}
