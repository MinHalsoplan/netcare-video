<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Netcare Video</title>
</head>

<c:url value="/video-client.swf" var="url" scope="page">
	<c:param name="server" value="${requestScope.serverUrl}" />
	<c:param name="consumeStream" value="${requestScope.consumeStream}" />
	<c:param name="produceStream" value="${requestScope.produceStream}" />
</c:url>

<body>
	<object width="1000" height="600">
		<param name="movie" value="${url}">
		<embed src="${url}" type="application/x-shockwave-flash" width="1000" height="600">	
	</object>
</body>
</html>



