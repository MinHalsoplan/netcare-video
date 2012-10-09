<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>

<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags" %>

<mvk:page>
	<mvk:header title="Netcare Video 2.0" resourcePath="/web/resources" contextPath="${pageContext.request.contextPath}">
		<link rel="stylesheet" href="<c:url value="/css/netcare.css" />" />
		<netcare:js />
		<script type="text/javascript">
			$(function() {
				
				var careUnit = '<sec:authentication property="principal.careUnit.id" />';
				
				var users = new Array();
				
				var ajax = new NC.Ajax();
				var util = new NC.Util();
				
				util.validateTimeField($('#start'));
				util.validateTimeField($('#end'));
				
				$('#date').datepicker({
					dateFormat : 'yy-mm-dd',
					firstday : 1,
					minDate : +0,
				});
				
				$('#search').autocomplete({
					source : function(req, resp) {
						ajax.getWithParams('/user/search', { searchterm : req.term }, function(data) {
							resp($.map(data.data, function(value) {
								
								var display = '';
								if (value.careGiver) {
									display = value.name + ' (' + value.hsaId + ')';
								} else {
									display = value.name + ' (' + util.formatCrn(value.civicRegistrationNumber) + ')';
								}
								
								return { label : display, value : display, userId : value.id };
							}));
						});
					},
					select : function(e, ui) {
						users.push(ui.item.userId);
						
						var deleteIcon = util.createIcon('trash', 16, function() {
							$('#user-' + ui.item.userId).remove();
							for (var i = 0; i < users.length; i++) {
								if (users[i] == ui.item.userId) {
									users.splice(i, 1);
									break;
								}
							}
						});
						
						$('#selectedUsers ul').append(
							$('<li>').attr('id', 'user-' + ui.item.userId).html(ui.item.value).append(deleteIcon)
						);
						
						$('input[name="search"]').val('');
					}
				});
				
				/*
				 * FORM SUBMISSION
				 */
				$(':submit').click(function(e) {
					e.preventDefault();
					
					var formData = new Object();
					formData.name = $('#name').val();
					formData.description = $('#description').val();
					formData.date = $('#date').val();
					formData.start = $('#start').val();
					formData.end = $('#end').val();
					
					formData.participants = new Array();
					
					$.each(users, function(i, v) {
						formData.participants[i] = v;
					});
					
					var jsonObj = JSON.stringify(formData);
					NC.log("JSON: " + jsonObj);
					
					ajax.post('/meeting/new', jsonObj, function(data) {
						$(':reset').click();
						loadMeetings();
					}, true);
				});
				
				/*
				 * FORM RESET
				 */
				$(':reset').click(function(e) {
					users = new Array();
					$('#selectedUsers ul').empty();
					
					$('#booking-form').toggle();
				});
				
				$('#showBookingForm').click(function(e) {
					e.preventDefault();
					$('#booking-form').toggle();
				})
				
				/*
				 * Load meetings for care unit
				 */
				var loadMeetings = function() {
					 
					 ajax.get('/meeting/' + careUnit + '/list', function(data) {
						 
						 $('#myBookings tbody').empty();
						 
						 if (data.data.length == 0) {
							 $('#existingBookings div').show();
							 $('#myBookings').hide();
						 } else {
							 $('#existingBookings div').hide();
							 $('#myBookings').show();
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
								
								var deleteIcon = util.createIcon('trash', 16, function() {
									ajax.post('/meeting/' + v.id + '/delete', null, function(data) {
										loadMeetings();
									}, true);
								});
								
								tr.append(
									$('<td>').append(deleteIcon)
								)
								
								$('#myBookings tbody').append(tr);
							});
						 
					 }, true);
					 
				};
				
				loadMeetings();
				
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
			
			<spring:message code="bookings.header" var="bookingsHeader"/>
			<mvk:content title="${bookingsHeader}">
		
				<netcare:content>
			
					<h2>${bookingsHeader}</h2>
					<p>
						<span class="label label-info"><spring:message code="label.information" /></span>
						<spring:message code="bookings.description" />
					</p>
					
					<p style="text-align: right; padding-right: 20px">
						<a id="showBookingForm" class="btn addButton"><spring:message code="bookings.create" /></a>
					</p>
					
					<section id="booking-form" style="display:none;">
						<form>
							<fieldset>
								<legend><spring:message code="bookings.form.information" /></legend>
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
								<legend><spring:message code="bookings.form.dateTime" /></legend>
								<netcare:row>
									<netcare:col span="2">
										<div class="control-group">
											<label class="control-label" for="date"><spring:message code="bookings.form.date" /></label>
											<div class="controls">
												<input type="text" name="date" id="date" class="input-small" />
											</div>
										</div>
									</netcare:col>
									<netcare:col span="1">
										<div class="control-group">
											<label class="control-label" for="start"><spring:message code="bookings.form.start" /></label>
											<div class="controls">
												<input type="text" name="start" id="start" class="span1"/>
											</div>
										</div>
									</netcare:col>
									<netcare:col span="1">
										<div class="control-group">
											<label class="control-label" for="end"><spring:message code="bookings.form.end" /></label>
											<div class="controls">
												<input type="text" name="end" id="end" class="span1"/>
											</div>
										</div>
									</netcare:col>
								</netcare:row>
							</fieldset>
							<fieldset>
								<legend><spring:message code="bookings.form.participants" /></legend>
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
								<button type="reset" class="btn"><spring:message code="bookings.reset" /></button>
							</div>
							
						</form>
					</section>
					
					<section id="existingBookings">
						<div class="alert alert-info" style="display: none;">
							<p><spring:message code="bookings.noBookings" /></p>
						</div>				
						<table id="myBookings" class="table table-striped">
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
			</mvk:content>
		</mvk:pageContent>
		
		<mvk:pageFooter>
		
		</mvk:pageFooter>
	</mvk:body>
</mvk:page>