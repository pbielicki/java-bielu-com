<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <s:head theme="ajax"/>
</head>
<body>

<br>
<br>

<center>
	<c:set var="loadingText">
		<h3><blink>Loading Statistics...</blink></h3>
		<br>
		<img src='<s:url value="/img/ajax-loader.gif" />'>
	</c:set>
	<s:div
	        id="statistics"
	        href="charts.jspa"
	        theme="ajax"
	        errorText="<h4 style='color: #E00000;'><blink>There was an error</blink></h4>"
	        loadingText="${loadingText}" />
</center>
</body>
</html>