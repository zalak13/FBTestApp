<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@page import="com.johnhancock.myapp.fbloginmodel.FBConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	FBConnection fbConnection = new FBConnection();
%>
<html>
<head>
	<title>Home</title>
</head>
<body style="text-align: center; margin: 0 auto; background-image: url(./resources/img/fbloginbckgrnd.jpg)">
<c:set var="userID"  value="${userid}" />
<c:if test="${userID == null}">
	<div
		style="margin: 0 auto; ">
		<a href="<%=fbConnection.getFBAuthUrl()%>"> <img
			style="margin-top: 318px;" src="./resources/img/facebookloginbutton.png" />
		</a>
	</div>
</c:if>
<c:if test="${userID != null}">
	<div style="margin-top: 138px;">
	<h1>John Hancock Test Application</h1>
		<h2>Welcome ${name}</h2>
		<div>Your Email is ${email}</div>		
		<a href="logout.htm"> <img
			style="margin-top: 138px;" src="./resources/img/facebooklogoutbutton.png" height=100px width=500px; />
		</a>
	</div>
</c:if>
</body>

</html>
