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
				var meeting = '<c:out value="${param.meeting}" />';
				var ajax = new NC.Ajax();
				
				var loadNotes = function() {
					ajax.get('/meeting/' + meeting + '/note/list', function(data) {
						
						if (data.data.length == 0) {
							$('#noteInfo').show();
							$('#noteList').hide();
						} else {
							$('#noteInfo').hide();
							$('#noteList').show();
						}
						
						$('#noteList').empty();
						
						$.each(data.data, function(i, v) {
							var quote = $('<blockquote>');
							quote.append(
								$('<p>').html(v.note)
							);
							
							quote.append(
								$('<small>').html(v.createdBy.name + ' (' + v.createdAt + ')')
							);
							
							$('#noteList').append(quote);
						});
						
					}, true);
				};
				
				loadNotes();
				
			});
		</script>
	</netcare:header>
	<netcare:body>
		<netcare:content>
		
			<h2><spring:message code="notes.title" /></h2>
			<p>
				<span class="label label-info"><spring:message code="label.information" /></span>
				<spring:message code="notes.titleDescription" />
			</p>
			
			<section id="notes">
				<div id="noteInfo" class="alert alert-info" style="display: none;">
					<p><spring:message code="notes.noNotes" /></p>
				</div>
				
				<div id="noteList">
				
				</div>
				
			</section>
		</netcare:content>
	</netcare:body>
</netcare:page>