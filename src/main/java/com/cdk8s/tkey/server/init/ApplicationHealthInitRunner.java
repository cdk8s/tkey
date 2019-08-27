package com.cdk8s.tkey.server.init;

import com.cdk8s.tkey.server.actuator.CustomUPMSApiServerHealthEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ApplicationHealthInitRunner implements ApplicationRunner {

	@Autowired
	private CustomUPMSApiServerHealthEndpoint customUPMSApiServerHealthEndpoint;

	@Autowired
	private ReactiveHealthIndicator redisReactiveHealthIndicator;

	//=====================================业务处理 start=====================================

	@Override
	public void run(ApplicationArguments args) {
		http();
		redis();
	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private void http() {
		Health customUPMSHealth = customUPMSApiServerHealthEndpoint.health();
		if (customUPMSHealth.getStatus().equals(Status.DOWN)) {
			log.error("启动请求 UPMS 接口失败");
		}
	}

	private void redis() {
		Mono<Health> redisHealth = redisReactiveHealthIndicator.health();
		redisHealth.subscribe(h -> {
			if (h.getStatus().equals(Status.DOWN)) {
				log.error("启动连接 Redis 失败");
			}
		});
	}

	//=====================================私有方法  end=====================================

}
