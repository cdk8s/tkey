package com.cdk8s.tkey.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@EnableRetry
@ServletComponentScan
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Value("${server.port:9091}")
	private String serverPort;

	@Value("${server.servlet.context-path:/sso}")
	private String serverContextPath;

	@Value("${management.server.servlet.context-path:/tkey-actuator}")
	private String managementContextPath;

	@Value("${management.server.port:19091}")
	private String managementPort;

	//=================================================================================

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {
		log.info("=================================Application Startup Success=================================");
		log.info("index >> http://sso.cdk8s.com:{}{}", serverPort, serverContextPath);
		log.info("actuator-health >> http://sso.cdk8s.com:{}{}/actuator/health", managementPort, managementContextPath);
		log.info("actuator-prometheus >> http://sso.cdk8s.com:{}{}/actuator/prometheus", managementPort, managementContextPath);
		log.info("=================================Application Startup Success=================================");
	}


}
