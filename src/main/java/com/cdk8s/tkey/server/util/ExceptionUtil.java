package com.cdk8s.tkey.server.util;


import org.apache.commons.lang3.exception.ExceptionUtils;

public final class ExceptionUtil {

	public static String getStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}
		return ExceptionUtils.getStackTrace(e);
	}

}
