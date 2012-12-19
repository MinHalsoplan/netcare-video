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
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>
<%@ taglib prefix="netcare" uri="http://www.callistasoftware.org/netcare/tags"%>
<%@ taglib prefix="video" tagdir="/WEB-INF/tags" %>

<sec:authentication property="principal" var="p" scope="request" />

<sec:authorize access="hasRole('ROLE_CAREGIVER')">
	<c:set var="isCareGiver" value="true" scope="request"/>
	<c:set var="careUnitId" value="${p.careUnit.id}" scope="request"/>
	<c:set var="user" value="${p.name} (${p.careUnit.name})" scope="request"/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_PATIENT')">
	<c:set var="user" value="${p.name}" scope="request"/>
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="/web/resources" />

<mvk:header title="Videomöte" resourcePath="${resourcePath}" contextPath="${contextPath}">
	<netcare:css resourcePath="${resourcePath}" />
	<link rel="stylesheet" href="<c:url value='${contextPath}/css/netcare-video.css' />" type="text/css" />
	
	<netcare:js resourcePath="${resourcePath}"/>
	<script type="text/javascript" src="${contextPath}/js/netcare-video.js"></script>
	
	<video:templates />
	
	<%-- Custom javascripts go here --%>
	<jsp:doBody />
	
</mvk:header>


