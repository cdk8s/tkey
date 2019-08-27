package com.cdk8s.tkey.server.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


public final class StringUtil {


	//=====================================Apache Common 包 start=====================================

	public static boolean isNotBlank(final String str) {
		return StringUtils.isNotBlank(str);
	}

	public static boolean isBlank(final String str) {
		return StringUtils.isBlank(str);
	}

	public static boolean containsIgnoreCase(final String str, final String search) {
		return StringUtils.containsIgnoreCase(str, search);
	}

	public static String substringAfter(final String str, final String search) {
		return StringUtils.substringAfter(str, search);
	}

	public static String replaceIgnoreCase(final String text, final String searchString, final String replacement) {
		return StringUtils.replaceIgnoreCase(text, searchString, replacement);
	}

	public static List<String> split(String str, String separator) {
		return CollectionUtil.toList(StringUtils.split(str, separator));
	}

	public static boolean equalsIgnoreCase(final String str1, final String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static boolean notEqualsIgnoreCase(final String str1, final String str2) {
		return !equalsIgnoreCase(str1, str2);
	}

	public static boolean endsWithAny(final CharSequence sequence, final CharSequence... searchStrings) {
		return StringUtils.endsWithAny(sequence, searchStrings);
	}

	//=====================================Apache Common 包  end=====================================

	//=====================================其他包 start=====================================


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}



