<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<netcare:page>
	<netcare:header>
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
									$('<img>').attr('src', '/img/icons/16/connected.png')
								);
							} else {
								$('#connectionStatus-' + v.user.id).append(
									$('<img>').attr('src', '/img/icons/16/disconnected.png')
								);
								
								if (present) {
									$('#video-' + v.user.id + ' object').hide();	
								}
							}
						});
					});
				};
				
				$('#leave-meeting').click(function(e) {
					e.preventDefault();
					leaveMeeting(function(data) {
						window.location = '/media/dashboard';
					});
				});
				
				setInterval(function() {
					checkStatus(booking);
				}, 10000);
			});
		</script>
	</netcare:header>
	<netcare:body>
		<div class="span9">
			<h2>Videomöte</h2>
			<p>
				<span class="label label-info">Information</span>
				Nedan visas deltagarna i videomötet, inklusive dig själv. I menyn till höger kan du välja
				att dölja de deltagare som du inte vill se på skärmen.
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
					
					<li class="span4">
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
			<h3><img src="<c:url value="/img/icons/16/auth.png" />" /> Inloggad som</h3>
			<p>
				<a href="#"><sec:authentication property="principal.username" /></a> | <a href="<c:url value="/j_spring_security_logout" />">Logga ut</a>
			</p>
		
			<h3><img src="<c:url value="/img/icons/16/user.png" />" /> Deltagare</h3>
			
			<c:forEach items="${requestScope.booking.participants}" var="part">
				<p>
					<span id="connectionStatus-${part.user.id}"><img src="<c:url value="/img/icons/16/disconnected.png" />" /></span>
					<strong><c:out value="${part.user.username}" /></strong>
				</p>
			</c:forEach>
			
			<p>
				<a id="leave-meeting" href="#">Klicka här</a> för att lämna videomötet.
			</p>
			
			<h3><img src="<c:url value="/img/icons/16/meetinginfo.png" />" /> Mötesinformation</h3>
			<p>
				<strong>Startar:</strong> <c:out value="${requestScope.booking.start}" /><br />
				<strong>Slutar:</strong> <c:out value="${requestScope.booking.end}" />
			</p>
		</div>
	</netcare:body>
</netcare:page>



