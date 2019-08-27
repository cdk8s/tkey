package com.cdk8s.tkey.server.actuator;

import com.cdk8s.tkey.server.util.okhttp.OkHttpResponse;
import com.cdk8s.tkey.server.util.okhttp.OkHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * 模拟检测第三方验证用户名密码接口
 */
@Component
public class CustomUPMSApiServerHealthEndpoint extends AbstractHealthIndicator {

	@Autowired
	private OkHttpService okHttpService;

	//======================================================

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		OkHttpResponse okHttpResponse = okHttpService.get("https://www.baidu.com");
		if (okHttpResponse.getStatus() == HttpStatus.OK.value()) {
			builder.up();
		} else {
			builder.down();
		}
	}
}
