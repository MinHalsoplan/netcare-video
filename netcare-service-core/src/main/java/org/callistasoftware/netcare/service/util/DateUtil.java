/**
 * Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.callistasoftware.netcare.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
	private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_TIME_PATTERN);
	
	/**
	 * Format the give date object. Return null if date is null
	 * @param date
	 * @return
	 */
	public static String toDateTime(final Date date) {
		return date != null ? SDF.format(date) : null;
	}
	
	public static Date parseDateTime(final String date, final String time) {
		try {
			return SDF.parse(date + " " + time);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date parse(final String dateTime) {
		try {
			return SDF.parse(dateTime);
		} catch (final ParseException e) {
			return null;
		}
	}
}
