package com.cdk8s.tkey.server.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Slf4j
@Component
public class MeterConfig implements MeterRegistryCustomizer {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.datasource.hikari.pool-name:/myHikariPoolName}")
	private String hikariPoolName;

	@Override
	public void customize(MeterRegistry registry) {
		registry.config().commonTags("application", applicationName);
		registry.config().commonTags("hikaricp", hikariPoolName);
		try {
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			log.debug("设置 metrics instance-id 为 ip:" + hostAddress);
			registry.config().commonTags("instance", hostAddress);
		} catch (UnknownHostException e) {
			String uuid = UUID.randomUUID().toString();
			registry.config().commonTags("instance", uuid);
			log.error("设置 metrics instance-id 为 uuid:" + uuid, e);
		}
	}
}
