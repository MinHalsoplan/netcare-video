package org.callistasoftware.netcare.service.util;

public final class EntityUtil {

	private EntityUtil() {
		throw new UnsupportedOperationException();
	}
	
	public static String formatCrn(final String crn) {
		return (crn.indexOf("-") == -1) ? crn : crn.replaceAll("-", "");
	}
}
