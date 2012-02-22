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

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="application.title" /></title>
	
	<!-- Include Twitter bootstrap -->
	<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css" />" />
	<link rel="stylesheet" href="<c:url value="/css/smoothness/jquery-ui-1.8.17.custom.css" />" />
	
	<link rel="stylesheet" href="<c:url value="/css/netcare.css" />" />
	
	<style type="text/css">
		/* Override some defaults */
      html, body {
        background-color: #eee;
      }
      body {
        padding-top: 40px; /* 40px to make the container go all the way to the bottom of the topbar */
      }
      .container > footer p {
        text-align: center; /* center align it with the container */
      }

      /* The white background content wrapper */
      .content {
        background-color: #fff;
        padding: 20px;
        margin: 0 -20px; /* negative indent the amount of the padding to maintain the grid system */
        -webkit-border-radius: 6px 6px 6px 6px;
           -moz-border-radius: 6px 6px 6px 6px;
                border-radius: 6px 6px 6px 6px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
                box-shadow: 0 1px 2px rgba(0,0,0,.15);
      }

      /* Page header tweaks */
      .page-header {
        background-color: #f5f5f5;
        padding: 20px 20px 10px;
        margin: -20px -20px 20px;
      }

      /* Give a quick and non-cross-browser friendly divider */
      div.content .menu {
        margin-left: 0;
        padding-left: 19px;
        border-left: 1px solid #eee;
      }
	</style>
	
	<script type="text/javascript" src="<c:url value="/js/jquery-1.7.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.17.custom.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/bootstrap.min.js" />"></script>
	
	<script type="text/javascript" src="<c:url value="/js/netcare.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/netcare/Util.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/netcare/PageMessages.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/netcare/Ajax.js" />"></script>
	
	<jsp:doBody />
</head>
