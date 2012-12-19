<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="netcare"  uri="http://www.callistasoftware.org/netcare/tags"%>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>

<%@ taglib prefix="video" tagdir="/WEB-INF/tags"%>

<mvk:page>
	<video:viewHeader>
		<script type="text/javascript">
			$(function() {
				var booking = "<c:out value='${requestScope.booking.id}' />";
				var params = {
					meetingId : booking,
					serverUrl : '<c:out value="${requestScope.serverUrl}" />',
					userId : '<sec:authentication property="principal.id"/>'
				};
				
				NCV.VIDEO.init(params);
				NCV.NOTE_TAKING.init(params);
			});
		</script>
	</video:viewHeader>
	<mvk:body>
		<sec:authentication property="principal.username" var="username"/>
		<mvk:pageHeader title="Videomöte"
			loggedInUser="${username}"
			loggedInAsText="Inloggad som : "
			logoutUrl="/web/security/logout"
			logoutText="Logga ut" />
		
		<mvk:pageContent>
			<spring:message code="meeting.title" var="meetingTitle"/>
			<mvk:content title="${meeting.title}" plain="true" noMenu="true">
			
				<mvk:heading title="${meetingTitle}">
					<spring:message code="meeting.titleDescription" />
				</mvk:heading>
					
				<netcare:row>
					<netcare:col span="3">
						<div id="participants"></div>
					</netcare:col>
					<netcare:col span="9">
						<mvk:sheet>
							<div id="movieFrame" class="thumbnail" style="width: 640px; height: 480px; text-align: center; margin-bottom: 20px;">
								<p style="padding-top: 230px; text-align:center;"><i>Välj en deltagare i listan till vänster att visa i denna ruta.</i></p>
							</div>
						</mvk:sheet>
						
						
						<mvk:heading title="Anteckna">
							Genom att fylla i fältet och klicka på Spara kan du ta anteckningar
							från det pågående mötet.
						</mvk:heading>
						
						<mvk:sheet>
							<netcare:row>
								<netcare:col span="6">
									<form id="note-form" method="post">
										<netcare:field containerId="noteContainer" name="note">
											<textarea id="note" name="note" rows="4" class="span12"></textarea>
										</netcare:field>
										
										<button id="save-note" type="submit" class="btn">Spara</button>
									</form>
								</netcare:col>
								<netcare:col span="6">
									<div id="my-notes">
										<h3>Mina anteckningar</h3>
									</div>
								</netcare:col>
							</netcare:row>
						</mvk:sheet>
						
					</netcare:col>
				</netcare:row>
			</mvk:content>
		</mvk:pageContent>
	</mvk:body>
</mvk:page>



