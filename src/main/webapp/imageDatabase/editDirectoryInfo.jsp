<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>

<html>
<head>
    <s:head theme="simple"/>
</head>

<body>

<s:form action="saveDirectory" enctype="multipart/form-data">
	<s:hidden name="country" value="${country}" />
	<s:hidden name="region" value="${region}" />
	<s:hidden name="dir" value="${dir}" />
	<s:hidden name="page" value="${page}" />
	<s:hidden name="thumbs" value="${thumbs}" />
	<s:hidden name="directoryId" value="${directoryInfo.id}" />

	<tr class="img">
		<th class="img">Name:</th>
		<td>${directoryInfo.name}</td>
	</tr>
	
	<c:if test="${!empty directoryInfo.countryInfo}">
		<c:url var="url" value="http://www.bielu.com/azja/${directoryInfo.countryInfo.dir}" />
	</c:if>
	<c:if test="${empty directoryInfo.countryInfo}">
		<c:url var="url" value="http://www.bielu.com/sycylia/zdjecia/" />
	</c:if>
	
	<tr class="img">
		<th class="img">KMZ:</th>
		<td>
			<a href="${url}${directoryInfo.dir}${directoryInfo.kmz}">${directoryInfo.kmz}</a> 
			<c:if test="${!empty directoryInfo.kmz}"><br><br></c:if>
			<input style="width: 100%;" name="kmz" type="file">
			<br><br>
			<input type="checkbox" name="clearKmz" id="clear">
			<input type="hidden" name="__checkbox_clearKmz" value="${param.clearKmz}"/>
		    <label for="clear" class="checkboxLabel">Clear KMZ</label>
		</td>
	</tr>
	
	<s:submit value="Save" />
</s:form>

<s:url scheme="http" action="list" id="back">
	<s:param name="directoryId" value="" />
</s:url>
<a href='${back}'>Back to images list</a>

</body>
</html>
