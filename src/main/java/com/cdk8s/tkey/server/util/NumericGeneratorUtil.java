package com.cdk8s.tkey.server.util;

import java.util.concurrent.atomic.LongAdder;

public class NumericGeneratorUtil {

	private static final LongAdder LONG_ADDER = new LongAdder();

	public static long getNumber() {
		LONG_ADDER.increment();
		return LONG_ADDER.longValue();
	}
}
