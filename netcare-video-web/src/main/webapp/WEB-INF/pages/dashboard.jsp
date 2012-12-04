<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="netcare"  uri="http://www.callistasoftware.org/netcare/tags"%>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>
<%@ taglib prefix="video" tagdir="/WEB-INF/tags"%>

<mvk:page>
	<sec:authentication property="principal" var="p"/>
	<video:viewHeader>
	
		<sec:authorize access="hasRole('ROLE_CAREGIVER')">
			<c:set var="isCareGiver" value="true" />
			<c:set var="careUnitId" value="${p.careUnit.id}" />
			<c:set var="user" value="${p.name} (${p.careUnit.name})" />
		</sec:authorize>
		
		<sec:authorize access="hasRole('ROLE_PATIENT')">
			<c:set var="user" value="${p.name}" />
		</sec:authorize>
	
		<script type="text/javascript">
			$(function() {
				
				var bParams = {
					connect : '<spring:message code="dashboard.booking.connect" />',
					notes : '<spring:message code="notes.title" />'
				};
				
				NCV.BOOKINGS.init(bParams);
				
				if ('${isCareGiver}' == 'true') {
					
					var aParams = {
						careUnit : '${careUnitId}'
					}
					
					NCV.ADMIN.init(aParams);
				}
			});
		</script>
	</video:viewHeader>
	<mvk:body>
		<mvk:pageHeader title="Videomöte"
			loggedInUser="${user}"
			loggedInAsText="Inloggad som : "
			logoutUrl="/web/security/logout"
			logoutText="Logga ut" />
			
		<mvk:pageContent>
			<c:url value="/home" var="start" />
			
			<mvk:content title="Startsida" plain="true" noMenu="true">
			
				<sec:authorize access="hasRole('ROLE_CAREGIVER')">
					<a id="create-new" href="<c:url value="/web/bookings/new" />" class="btn">Skapa videomöte</a>
				</sec:authorize>
				
				<section id="bookings">
					<div class="sectionLoader" style="display: none;">
						<img src="<c:url value="/web/resources/images/loaders/ajax-loader-medium.gif" />" />
						<span class="loaderMessage"></span>
					</div>
					<div id="bookingsContainer" style="display: none;">
						<mvk:heading title="Just nu">
							Klicka på ett möte för att ansluta till det
						</mvk:heading>
						<mvk:touch-list id="bookingsList"></mvk:touch-list>
					</div>
				</section>
				
				<sec:authorize access="hasRole('ROLE_CAREGIVER')">
					<br />
				
					<section id="upcoming">
						<div class="sectionLoader" style="display: none;">
							<img src="<c:url value="/web/resources/images/loaders/ajax-loader-medium.gif" />" />
							<span class="loaderMessage"></span>
						</div>
						<div id="upcomingContainer" style="display: none;">
							<mvk:heading title="Kommande">
								<spring:message code="bookings.description" />
							</mvk:heading>
							<mvk:touch-list id="upcomingList"></mvk:touch-list>
						</div>
					</section>
				</sec:authorize>
			</mvk:content>
		
		</mvk:pageContent>
	
		<mvk:pageFooter>
		
		</mvk:pageFooter>
	</mvk:body>
</mvk:page>