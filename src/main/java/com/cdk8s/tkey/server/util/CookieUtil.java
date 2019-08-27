package com.cdk8s.tkey.server.util;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
public final class CookieUtil {

	public static void setCookie(final HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly, boolean secure) {
		setCookie(response, name, value, "/", maxAge, httpOnly, secure);
	}

	@SneakyThrows
	public static void setCookie(final HttpServletResponse response, String name, String value, String path, int maxAge, boolean httpOnly, boolean secure) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setVersion(1);
		cookie.setHttpOnly(httpOnly);
		cookie.setSecure(secure);
		cookie.setValue(URLEncoder.encode(value, "utf-8"));
		response.addCookie(cookie);
	}

	// ============================================================================================================

	public static void deleteCookie(final HttpServletRequest request, HttpServletResponse response, String name) {
		deleteCookie(request, response, name, "/");
	}

	public static void deleteCookie(final HttpServletRequest request, HttpServletResponse response, String name, String path) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setPath(path);
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	// ============================================================================================================

	public static String getCookie(final HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}

	@SneakyThrows
	public static String getCookie(final HttpServletRequest request, final HttpServletResponse response, String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtil.equalsIgnoreCase(cookie.getName(), name)) {
					value = URLDecoder.decode(cookie.getValue(), "utf-8");
					if (isRemove) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}
}

