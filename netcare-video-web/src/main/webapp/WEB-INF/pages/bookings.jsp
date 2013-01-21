<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>
<%@ taglib prefix="netcare" uri="http://www.callistasoftware.org/netcare/tags"%>

<%@ taglib prefix="video" tagdir="/WEB-INF/tags" %>

<mvk:page>
	<sec:authentication property="principal" var="p"/>
	<video:viewHeader>
		<script type="text/javascript">
			$(function() {
				
				var params = {
					booking : '<c:out value="${requestScope.booking}" />',
					careUnitHsa : '<c:out value="${p.careUnit.hsaId}" />'
				};
				
				NCV.CREATE_BOOKING.init(params);
			});
		</script>
	</video:viewHeader>
	<mvk:body>
		
		<mvk:pageHeader title="Videomöte"
			loggedInUser="${p.username}"
			loggedInAsText="Inloggad som : "
			logoutUrl="/web/security/logout"
			logoutText="Logga ut" />
			
		<mvk:pageContent>
			
			<c:url value="/home" var="start" />
			
			<c:url value="/web/dashboard" var="start" />
			<mvk:content backTitle="Tillbaka" backUrl="${start}" backToWhat="till Startsidan" noMenu="true">
				<form id="booking-form">
					<fieldset>
						<legend>1. <spring:message code="bookings.form.information" /></legend>
						<netcare:row>
							<netcare:col span="3">
								<div class="control-group">
									<label class="control-label" for="name"><spring:message code="bookings.form.name" /></label>
									<div class="controls">
										<input type="text" name="name" id="name" class="input-large" />
									</div>
								</div>
							</netcare:col>
						</netcare:row>
						<netcare:row>
							<netcare:col span="3">
								<div class="control-group">
									<label class="control-label" for="description"><spring:message code="bookings.form.description" /></label>
									<div class="controls">
										<textarea rows="3" name="description" id="description" class="input-xlarge"></textarea>
									</div>
								</div>
							</netcare:col>
						</netcare:row>
					</fieldset>
					<fieldset>
						<legend>2. <spring:message code="bookings.form.dateTime" /></legend>
						<netcare:row>
							<netcare:col span="3">
								<div class="control-group">
									<label class="control-label" for="date"><spring:message code="bookings.form.date" /></label>
									<div class="controls">
										<input type="text" name="date" id="date" class="span12 dateInput"/>
									</div>
								</div>
							</netcare:col>
							<netcare:col span="2">
								<div class="control-group">
									<label class="control-label" for="start"><spring:message code="bookings.form.start" /></label>
									<div class="controls">
										<input type="text" name="start" id="start" class="span12"/>
									</div>
								</div>
							</netcare:col>
							<netcare:col span="2">
								<div class="control-group">
									<label class="control-label" for="end"><spring:message code="bookings.form.end" /></label>
									<div class="controls">
										<input type="text" name="end" id="end" class="span12"/>
									</div>
								</div>
							</netcare:col>
						</netcare:row>
					</fieldset>
					<fieldset>
						<legend>3. <spring:message code="bookings.form.participants" /></legend>
						<netcare:row>
							<netcare:col span="9">
								<label for="search"><spring:message code="bookings.form.search" /></label>
								<input type="text" id="search" name="search" class="input-xlarge search-query" />
							</netcare:col>
						</netcare:row>
						<netcare:row>
							<netcare:col span="9" id="selectedUsers">
								<p>
									<ul></ul>
								</p>
							</netcare:col>
						</netcare:row>
					</fieldset>
					
					<div class="form-actions">
						<button type="submit" class="btn btn-primary"><spring:message code="bookings.create" /></button>
						<button id="delete-booking" type="button" class="btn btn-primary" style="display:none;">Ta bort</button>
					</div>
					
				</form>
			</mvk:content>
		</mvk:pageContent>
		
		<mvk:pageFooter>
		
		</mvk:pageFooter>
	</mvk:body>
</mvk:page>