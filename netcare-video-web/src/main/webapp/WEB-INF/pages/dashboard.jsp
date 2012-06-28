<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags" %>

<netcare:page>
	<netcare:header>
		<script type="text/javascript">
			$(function() {
				var connect = '<spring:message code="dashboard.booking.connect" />';
				var notes = '<spring:message code="notes.title" />';
				
				var ajax = new NC.Ajax();
				
				var loadBookings = function() {
					ajax.get('/meeting/list', function(data) {
						
						$('#myBookings tbody').empty();
						
						if (data.data.length == 0) {
							$('#myBookings').hide();
							$('#existingBookings div').show();
						} else {
							$('#myBookings').show();
							$('#existingBookings div').hide();
						}
						
						$.each(data.data, function(i, v) {
							var tr = $('<tr>');
							
							tr.append(
								$('<td>').html(v.name)
							);
							
							tr.append(
								$('<td>').html(v.start)
							);
							
							tr.append(
								$('<td>').html(v.end)
							);
							
							var parts = '';
							$.each(v.participants, function(idx, val) {
								parts += val.user.name + '<br />';
							});
							
							tr.append(
								$('<td>').html(parts)
							);
							
							tr.append(
								$('<td>').html(v.createdBy.name)
							);
							
							if (v.started) {
								tr.append(
									$('<td>').append(
										$('<a>').attr('href', NC.getContextPath() + '/web/video?booking=' + v.id).html(connect)
									).append('<br>').append(
										$('<a>').attr('href', NC.getContextPath() + '/web/notes?meeting=' + v.id).html(notes)
									)
								);
							} else {
								tr.append($('<td>'));
							}
							
							$('#myBookings tbody').append(tr);
						});
						
					}, true);
				};
				
				loadBookings();
				
			});
		</script>
	</netcare:header>
	<netcare:body>
		<netcare:content>
		
			<h2><spring:message code="dashboard.bookings" /></h2>
			<p>
				<span class="label label-info"><spring:message code="label.information" /></span>
				<spring:message code="dashboard.bookings.description" />
			</p>
			
			<section id="existingBookings">
				<div class="alert alert-info">
					<p><spring:message code="bookings.noBookings" /></p>
				</div>
				<table id="myBookings" class="table table-bordered table-striped">
					<thead>
						<tr>
							<th><spring:message code="dashboard.booking.name" /></th>
							<th><spring:message code="dashboard.booking.start" /></th>
							<th><spring:message code="dashboard.booking.end" /></th>
							<th><spring:message code="dashboard.booking.participants" /></th>
							<th><spring:message code="bookings.createdBy" /></th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</section>
		</netcare:content>
	</netcare:body>
</netcare:page>