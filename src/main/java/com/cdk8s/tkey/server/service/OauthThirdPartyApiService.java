package com.cdk8s.tkey.server.service;


import com.cdk8s.tkey.server.exception.OauthApiException;
import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.server.util.JsonUtil;
import com.cdk8s.tkey.server.util.StringUtil;
import com.cdk8s.tkey.server.util.okhttp.OkHttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OauthThirdPartyApiService {

	@Autowired
	private OkHttpService okHttpService;

	//=====================================业务处理 start=====================================


	public OauthUserAttribute getOauthUserAttributeDTO(String username, String password) {
		log.debug("正在请求 UPMS 接口");
		log.debug("username=<{}>", username);
		log.debug("password=<{}>", password);


		if (StringUtil.notEqualsIgnoreCase(username, "admin")) {
			throw new OauthApiException("演示模式下，用户名是 admin");
		}

		if (StringUtil.notEqualsIgnoreCase(password, "123456")) {
			throw new OauthApiException("演示模式下，密码是 123456");
		}

		// 下面是真实场景下的 REST 调用方式。如果可以直连数据库的话，这里可以改为 Mapper 查询
		// OkHttpResponse okHttpResponse = okHttpService.get("https://www.baidu.com/");
		// log.debug("调用第三方接口返回=<{}>", okHttpResponse.toString());
		// if (okHttpResponse.getStatus() != HttpStatus.OK.value()) {
		// 	throw new OauthApiException("调用 UPMS 接口获取用户信息失败");
		// }

		return getUserInfoApi(username);
	}


	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	private OauthUserAttribute getUserInfoApi(String username) {
		String userInfoJson = "{\n" +
				"  \"email\": \"" + username + "@cdk8s.com\",\n" +
				"  \"userId\": \"111222333\",\n" +
				"  \"username\": \"" + username + "\"\n" +
				"}";

		return JsonUtil.toObject(userInfoJson, OauthUserAttribute.class);
	}

	//=====================================私有方法  end=====================================

}
