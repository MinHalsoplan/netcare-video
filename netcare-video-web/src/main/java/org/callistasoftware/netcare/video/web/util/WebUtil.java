package org.callistasoftware.netcare.video.web.util;
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


import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class WebUtil {
	
	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

	public static final WebApplicationContext getWebRequest(final ServletContext sc) {
		return WebApplicationContextUtils.getWebApplicationContext(sc);
	}
	
	public static final String[] getActiveProfiles(final ServletContext sc) {
		return WebUtil.getWebRequest(sc).getEnvironment().getActiveProfiles();
	}
	
	public static final boolean isProfileActive(final ServletContext sc, final String name) {
		final String[] profiles = WebUtil.getActiveProfiles(sc);
		for (final String s : profiles) {
			if (s.equals(name)) {
				return true;
			}
		}
		
		return false;
	}
}
