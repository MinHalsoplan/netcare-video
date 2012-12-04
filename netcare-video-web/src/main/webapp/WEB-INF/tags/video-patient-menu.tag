<%--

    Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="netcare"  uri="http://www.callistasoftware.org/netcare/tags"%>

<script type="text/javascript">
	$(function() {
		var crn = '<sec:authentication property="principal.civicRegistrationNumber" />';
		var format = NC.GLOBAL.formatCrn(crn);
		
		$('#crn').html(format);
	});
</script>

<div class="span3">
	<h3><netcare:image name="auth" size="16"/><spring:message code="menu.loggedInAs" /></h3>
	<p>
		<a href="#"><sec:authentication property="principal.username" /></a> | <a href="<spring:url value="/web/security/logout" htmlEscape="true"/>"><spring:message code="menu.logout" /></a>
	</p>
	<p>
		<strong><spring:message code="patient.crn" />:</strong> <span id="crn"></span>
	</p>
</div>
