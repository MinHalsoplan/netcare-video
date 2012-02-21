<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags" %>

<netcare:page>
	<netcare:header>
		<script type="text/javascript">
			$(function() {
				
			});
		</script>
	</netcare:header>
	<netcare:body>
		<netcare:content>
		
			<h2>Mina bokningar</h2>
			<p>
				<span class="label label-info">Information</span>
				I tabellen nedan visas dina bokade videomöten. När ett videomöte startar kommer syns det en länk
				där du kan ansluta till videomötet.
			</p>
			
			<table id="myBookings" class="table table-bordered table-striped">
				<thead>
					<tr>
						<th>Start</th>
						<th>Deltagare</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="booking" items="${requestScope.bookings}">
						<c:url value="/media/video" scope="page" var="videoUrl">
							<c:param name="booking" value="${booking.id}" />
						</c:url>
						<tr>
							<td><fmt:formatDate value="${booking.start}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td>
								<c:forEach var="part" items="${booking.participants}">
									<c:out value="${part.user.username}" /><br />
								</c:forEach>
							</td>
							<td><a href="${videoUrl}">Starta videomöte</a>
						</tr>
							
					</c:forEach>
				</tbody>
			</table>
		
		</netcare:content>
	</netcare:body>
</netcare:page>