package com.cdk8s.tkey.server.util;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public final class IPUtil {

	public static String getIp(HttpServletRequest request) {
		return ServletUtil.getClientIP(request);
	}
}
