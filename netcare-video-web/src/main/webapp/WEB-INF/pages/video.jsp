<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>

<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags/" %>

<mvk:page>
	<mvk:header title="Netcare Video 2.0" resourcePath="/web/resources" contextPath="${pageContext.request.contextPath}">
		<link rel="stylesheet" href="<c:url value="/css/netcare.css" />" />
		<netcare:js />
		<script type="text/javascript">
			var booking = "<c:out value='${requestScope.booking.id}' />";
			var ajax = new NC.Ajax();
			var leaveMeeting = function(callback) {
				ajax.postSynchronous('/meeting/' + booking + '/leave', null, callback); 
			};
		
			$(window).unload(function() {
				leaveMeeting();
			});
		
			$(function() {
				var checkStatus = function(booking) {
					
					ajax.get('/meeting/' + booking + '/status', function(data) {
						$.each(data.data.participants, function(i, v){
							$('#connectionStatus-' + v.user.id).empty();
							var present = $('#video-' + v.user.id + ' object').is(':visible');
							
							if (v.connected) {
								if (!present) {
									$('#video-' + v.user.id + ' object').show();
								}
								
								$('#connectionStatus-' + v.user.id).append(
									$('<img>').attr('src', NC.getContextPath() + '/img/icons/16/connected.png')
								);
							} else {
								$('#connectionStatus-' + v.user.id).append(
									$('<img>').attr('src', NC.getContextPath() + '/img/icons/16/disconnected.png')
								);
								
								if (present) {
									$('#video-' + v.user.id + ' object').hide();	
								}
							}
						});
					});
				};
				
				var submitComment = function() {
					var text = $('#comment').val();
					if (text.length != 0) {
						
						var data = new Object();
						data.note = text;
						data.meetingId = booking;
						
						ajax.post('/meeting/' + booking + '/note/new', JSON.stringify(data), function(data) {
							console.log("Note posted");
							$('#comment').val('');
						});
					}
				};
				
				$('#commentSubmit').click(function(e) {
					e.preventDefault();
					submitComment();
				});
				
				$('#leave-meeting').click(function(e) {
					e.preventDefault();
					leaveMeeting(function(data) {
						window.location = NC.getContextPath() + '/web/dashboard';
					});
				});
				
				setInterval(function() {
					checkStatus(booking);
				}, 10000);
			});
		</script>
	</mvk:header>
	<mvk:body>
		<sec:authentication property="principal.username" var="username"/>
		<mvk:pageHeader title="Videomöte"
			loggedInUser="${username}"
			loggedInAsText="Inloggad som : "
			logoutUrl="/web/security/logout"
			logoutText="Logga ut" />
			
		<mvk:pageContent>
			
			<c:url value="/home" var="start" />
		
			<mvk:leftMenu>
				<mvk:menuItem active="true" label="Startsida" url="${start}" />
			</mvk:leftMenu>
			
			<spring:message code="meeting.title" var="meetingTitle"/>
			<mvk:content title="${meetingTitle}">
				<div class="row-fluid">
					<div class="span12">
						<h2>${meetingTitle}</h2>
						<p>
							<span class="label label-info"><spring:message code="label.information" /></span>
							<spring:message code="meeting.titleDescription" />
						</p>
						<ul class="thumbnails">
							<c:forEach var="part" items="${requestScope.booking.participants}" >
							
								<sec:authentication property="principal.id" var="userId" scope="page"/>
							
								<c:choose>
									<c:when test="${part.user.id == userId}">
										<c:set var="id" value="video-self" scope="page" />
										<c:url value="/video-client.swf" var="videoUrl" scope="page">
											<c:param name="server" value="${requestScope.serverUrl}" />
											<c:param name="stream" value="${part.stream}" />
											<c:param name="type" value="producer" />
											<c:param name="width" value="288" />
											<c:param name="height" value="240" />
										</c:url>
									</c:when>
									<c:otherwise>
										<c:set var="id" value="video-${part.user.id}" scope="page" />
										<c:url value="/video-client.swf" var="videoUrl" scope="page">
											<c:param name="server" value="${requestScope.serverUrl}" />
											<c:param name="stream" value="${part.stream}" />
											<c:param name="type" value="consumer" />
											<c:param name="width" value="288" />
											<c:param name="height" value="240" />
										</c:url>
									</c:otherwise>
								</c:choose>
								
								<li class="span12">
									<div id="${id}" class="thumbnail" style="width: 300px; height: 252px;">
										<object width="288" height="240">
											<param name="movie" value="${videoUrl}" />
											<embed src="<c:out value="${videoUrl}" />" type="application/x-shockwave-flash" width="288" height="240"/>
										</object>
									</div>
									<div class="caption">
										<h5><c:out value="${part.user.username}" /></h5>
									</div>
								</li>
							</c:forEach>
						</ul>		
					</div>
					<div class="span3 menu">
						<h3><img src="<c:url value="/img/icons/16/auth.png" />" /> <spring:message code="menu.loggedInAs" /></h3>
						<p>
							<a href="#"><sec:authentication property="principal.username" /></a> | <a href="<c:url value="/j_spring_security_logout" />">Logga ut</a>
						</p>
					
						<h3><img src="<c:url value="/img/icons/16/user.png" />" /> <spring:message code="meeting.participants" /></h3>
						
						<c:forEach items="${requestScope.booking.participants}" var="part">
							<p>
								<span id="connectionStatus-${part.user.id}"><img src="<c:url value="/img/icons/16/disconnected.png" />" /></span>
								<strong><c:out value="${part.user.username}" /></strong>
							</p>
						</c:forEach>
						
						<p>
							<a id="leave-meeting" href="#"><spring:message code="meeting.clickHere" /></a> <spring:message code="meeting.toLeave" />
						</p>
						
						<h3>Anteckna</h3>
						<textarea id="comment" rows="3"></textarea> 
						<button id="commentSubmit" type="submit" class="btn btn-primary">Spara anteckning</button>
						
						<p></p>
						
						<h3><img src="<c:url value="/img/icons/16/meetinginfo.png" />" /> <spring:message code="meeting.info" /></h3>
						<p><strong><spring:message code="meeting.name" />: </strong> <c:out value="${requestScope.booking.name}" /></p>
						<blockquote>
							<p><c:out value="${requestScope.booking.description}" /></p>
						</blockquote>
						<p><strong><spring:message code="meeting.createdBy" />: </strong> <c:out value="${requestScope.booking.createdBy.name}" /></p>
						<p><strong><spring:message code="meeting.owner" />: </strong> <c:out value="${requestScope.booking.careUnit.name}" /></p>
						<p><strong><spring:message code="meeting.start" />:</strong> <c:out value="${requestScope.booking.start}" /></p>
						<p><strong><spring:message code="meeting.end" />:</strong> <c:out value="${requestScope.booking.end}" /></p>
					</div>
				</div>
			</mvk:content>
			
			<mvk:pageFooter></mvk:pageFooter>
			
		</mvk:pageContent>
	</mvk:body>
</mvk:page>



