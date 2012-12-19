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
	<video:viewHeader>
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
	</video:viewHeader>
	<mvk:body>
		<sec:authentication property="principal.username" var="username"/>
		<mvk:pageHeader title="Videomöte"
			loggedInUser="${user}"
			loggedInAsText="Inloggad som : "
			logoutUrl="/web/security/logout"
			logoutText="Logga ut" />
			
		<mvk:pageContent>
			<c:url value="/web/dashboard" var="start" />
			
			<mvk:content backTitle="Tillbaka" noMenu="true" backUrl="${start}" backToWhat="till Startsidan">
				<section id="notes" style="margin-top: 20px;">
					<div id="noteInfo" class="alert alert-info" style="display: none;">
						<spring:message code="notes.noNotes" />
					</div>
					
					<div id="noteList">
					
					</div>	
				</section>
			</mvk:content>
		</mvk:pageContent>
		
		<mvk:pageFooter>
		
		</mvk:pageFooter>
	</mvk:body>
</mvk:page>