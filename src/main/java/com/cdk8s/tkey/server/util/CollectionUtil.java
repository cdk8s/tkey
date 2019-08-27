package com.cdk8s.tkey.server.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 */
public final class CollectionUtil {


	//=====================================Apache Common 包 start=====================================

	/**
	 * 数组转换成 List
	 */
	public static <T> List<T> toList(T[] arrays) {
		List<T> list = new ArrayList<>();
		CollectionUtils.addAll(list, arrays);
		return list;
	}

	//=====================================Apache Common 包  end=====================================

	//=====================================Guava 包  end=====================================

	//=====================================Guava 包  end=====================================

	//=====================================其他包 start=====================================


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================

}



