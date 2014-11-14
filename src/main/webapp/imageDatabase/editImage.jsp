<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>

<html>
<head>
    <s:head theme="simple"/>
</head>

<body>

<s:form action="save" enctype="multipart/form-data">
	<s:hidden name="country" value="${country}" />
	<s:hidden name="region" value="${region}" />
	<s:hidden name="dir" value="${dir}" />
	<s:hidden name="page" value="${page}" />
	<s:hidden name="thumbs" value="${thumbs}" />
	<s:hidden name="imageId" value="${image.id}" />
	<tr class="img">
		<th class="img">Path:</th>
		<td>${image.path}</td>
	</tr>
	
	<c:url var="url" value="${bielu:imageBaseUrl(image)}" />
	<c:set var="thumbUrl" value="${url}thumb/${image.thumbnail}" />

	<tr class="img">
		<th class="img">Thumbnail:</th>
		<td style="padding: 5px;"><img src="${bielu:encodeUrl(thumbUrl)}" ></td>
	</tr>
	<tr class="img">
		<th class="img">KMZ:</th>
		<td>
			<a href="${url}${image.directoryInfo.dir}${image.kmz}">${image.kmz}</a> 
			<c:if test="${!empty image.kmz}"><br><br></c:if>
			<input style="width: 100%;" name="kmz" type="file">
			<br><br>
			<input type="checkbox" name="clearKmz" id="clear"> <label for="clear" class="checkboxLabel">Clear KMZ</label>
		</td>
	</tr>
	<tr class="img">
		<th class="img">Count:</th>
		<td>${image.count}</td>
	</tr>
	<tr  class="img">
		<th class="img">Short description:</th>
		<td>
			<input style="width: 100%;" name="shortDescription" value="${image.shortDescription}" type="text" maxlength="50">
		</td>
	</tr>
	<tr class="img">
		<th class="img">Description:</th>
		<td>
			<textarea style="width: 100%;" name="description" cols="80" rows="5">${image.description}</textarea>
		</td>
	</tr>
	<s:submit value="Save" />
</s:form>

<s:url scheme="http" action="list" id="back">
	<s:param name="imageId" value="" />
</s:url>
<a href="${back}">Back to images list</a>

</body>
</html>
