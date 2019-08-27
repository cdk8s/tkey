package com.cdk8s.tkey.server.util;

import cn.hutool.http.useragent.UserAgentParser;


public class UserAgentUtil {

	public static boolean isMobile(String userAgentString) {
		return UserAgentParser.parse(userAgentString).isMobile();
	}
}
