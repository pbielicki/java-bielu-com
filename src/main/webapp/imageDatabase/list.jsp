<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>

<html>
<head>
    <s:head theme="simple"/>
    <c:url var="marking" value="/js/marking.js" />
    <script language='javascript' src='${marking}'></script>
    <c:url var="dtree" value="/js/tree.js" />
    <script type="text/javascript" src="${dtree}"></script>
</head>

<body>
<c:if test="${! empty generalError}">
	<script language="JavaScript" type="text/javascript">
		alert("${generalError}");	
	</script>
</c:if>

<p/>

<s:form action="${actionUrl}" method="GET" id="thumb">
	<c:forEach items="${paramValues}" var="par">
		<c:if test="${par.key ne 'thumbs'}">
		<input type="hidden" name="${par.key}" value="${par.value[0]}">
		</c:if>
	</c:forEach>
	<s:checkbox label="Show thumbnails" name="thumbs" value="${param.thumbs}" onchange="javascript:document.getElementById('thumb').submit()" />
</s:form>

<p/>
<p/>

<!-- Page "slider" -->
<jsp:include flush="true" page="pageSlider.jsp" />
<!-- End of Page "slider" -->

<!-- Window title -->
<c:if test="${!empty windowTitle}">
	<center>
	<s:url action="editDirectory" id="editUrl">
		<s:param name="directoryId" value="${dir}" />
	</s:url>
	<h1 onClick="window.location='${editUrl}'" style="cursor: pointer;">
	${windowTitle}
	</h1>
	</center>
</c:if>
<!-- End of Window title -->

<!-- Root table -->
<div dojoType="LayoutContainer"
	layoutChildPriority='top-bottom'
	style="width: 100%; height: 600px;">
	
	<div dojoType="SplitContainer"
		orientation="horizontal"
		sizerWidth="5"
		activeSizing="true"
		layoutAlign="client"
	>
	
	<div dojoType="ContentPane" sizeMin="100" sizeShare="15" style="overflow: auto;">			
	<!-- Menu table -->
	<jsp:include flush="true" page="treeMenu.jsp" />
	<!-- End of Menu table -->
	</div>
	
	<div dojoType="ContentPane" sizeMin="300" sizeShare="85" style="overflow: auto;">
	<!-- Contents table -->
	<table class="img">
	<tr>
		<th class="img">Path</th>
		<th class="img">Thumbnail</th>
		<th class="img">Short Description</th>
		<th class="img">Description</th>
		<th class="img">KMZ</th>
		<th class="img">Google Maps</th>
		<th class="img">Count</th>
	</tr>
	
	<c:set var="markColor" value="#FFFFFF" />
	<c:set var="tdColor" value="#F0F0F0" />
	<c:set var="bgClause" value="bgcolor='${tdColor}'" />

	<c:forEach items="${images}" var="img" varStatus="count">
		
		<s:url id="editUrl" action="edit">
			<s:param name="imageId">${img.id}</s:param>
		</s:url>
	
		<tr class="img" id="${count.index}"  
		onMouseOver="setPointer(this, '${count.index}', 'over', '${tdColor}', '${markColor}', '${tdColor}');"
		onMouseOut="setPointer(this, '${count.index}', 'out', '${tdColor}', '${markColor}', '${tdColor}');"
		onDblClick="window.location='${editUrl}'">
		
			<c:url var="url" value="${bielu:imageBaseUrl(img)}" />
			
			<c:set var="thumbUrl" value="${url}thumb/${img.thumbnail}" />

			<c:if test="${empty param.thumbs or param.thumbs eq false}">
				<c:set var="tooltip">
				onMouseOver="this.T_BORDERCOLOR = '#818EBD'; this.T_BGCOLOR = '#818EBD'; this.T_WIDTH = 10; this.T_PADDING = 6; return escape('<img src=${bielu:encodeUrl(thumbUrl)}  >');"
				</c:set>
			</c:if>

			<td class="img" ${tooltip} ${bgClause}>${img.path}</td>
			<c:if test="${empty param.thumbs or param.thumbs eq false}">
				<td class="img" ${bgClause}>${img.thumbnail}</td>
			</c:if>
			<c:if test="${!empty param.thumbs and param.thumbs eq true}">
				<td class="img" align="center" ${bgClause}>
					<img src="${bielu:encodeUrl(thumbUrl)}" >
				</td>
			</c:if>
			<td class="img" ${bgClause}>${img.shortDescription}</td>
			<td class="img" ${bgClause}>${img.description}</td>
			
			<c:if test="${!empty img.kmz}"><c:set var="kmzName" value="${img.kmz}"/></c:if>
			<c:if test="${empty img.kmz}"><c:set var="kmzName" value="${img.directoryInfo.kmz}"/></c:if>
			
			<td class="img" ${bgClause}><a href="${url}${img.directoryInfo.dir}${kmzName}">${kmzName}</a></td>
			<td class="img" align="center" ${bgClause}>
				<c:if test="${!empty kmzName}">
				<a onClick="javascript:window.open('/gmaps.jsp?location=${url}${img.directoryInfo.dir}${kmzName}', 'help', 'width=800, height=600, toolbar=yes, location=yes, directories=no, status=yes, menubar=yes, resizable=yes, scrollbars=yes'); return true;">Click Me!</a>
				</c:if>
			</td>
			<td class="img" ${bgClause}>${img.count}</td>
		</tr>
	</c:forEach>
	</table>
	<!-- End of Contents table -->
	</div>
</div>
</div>

<!-- End of Root table -->
<script language="JavaScript" type="text/javascript" src="/js/wz_tooltip.js"></script>
</body>
</html>
