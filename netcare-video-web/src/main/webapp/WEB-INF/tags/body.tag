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
<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags" %>
<body>
	<div id="pageLoading" class="modal-backdrop fade in" style="display:none;">
	</div>
	<div id="pageLoadingBox" class="modal" style="display: none;">
		<div class="modal-body" style="height: 100px; text-align:center;">
			<span><img src="<c:url value="/img/ajax-loader-large.gif" />" /></span>
			<h2>Vänligen vänta medan sidan laddas</h2>
		</div>
	</div>
	<div class="container">
		<div class="content">
			<div class="page-header">
				<div class="row">
					<div class="span9">
						<h1>Video</h1>
					</div>
					<div id="ajaxInProgress" class="span3" style="text-align: right; vertical-align: middle; display: none;">
						<span>Laddar...<img src="<c:url value="/img/ajax-loader-small.gif" />" /></span>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div id="pageMessages" class="span12">
					<c:forEach items="${requestScope.errors}" var="err">
						<div class="alert alert-error">
							<a class="close" data-dismiss="alert">×</a>
							<p>${err}</p>
						</div>
					</c:forEach>
				</div>
			</div>
			
			<div class="row">
				<jsp:doBody />
			</div>
		</div>
		<footer>
			<p>Callista Software</p>
		</footer>
	</div>
	
</body>
