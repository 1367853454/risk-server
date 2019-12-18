package com.graduation.project.risk.project.util;

import org.apache.commons.lang.StringUtils;

public class VelocityHelper {

	public static boolean isBlank(Object obj) {

		if (obj instanceof String) {
			String str = (String) obj;
			if (StringUtils.isBlank(str)) {
				return true;
			}
		}

		if (null == obj) {
			return true;
		}

		return false;
	}

}
