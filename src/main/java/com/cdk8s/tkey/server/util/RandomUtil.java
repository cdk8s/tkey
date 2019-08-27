package com.cdk8s.tkey.server.util;

import org.apache.commons.lang3.RandomStringUtils;


public final class RandomUtil {

	public static String randomAlphanumeric(final int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}

}



