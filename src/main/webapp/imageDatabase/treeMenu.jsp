<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<table>
		<tr><td nowrap="true">
		<script type="text/javascript">
			<!--
			d = new dTree('d');
	
			d.add(0,-1,'Zawartość');
			d.add(1,0,'Azja','<s:url value="list.jsps?region=asia&country=&dir=" />');
	
			<c:set var="start" value="2" />
			<c:forEach items="${asiaDirs}" var="dir" varStatus="count">
				d.add('asia_${count.index}',1,'${dir.country}','<s:url value="list.jsps?country=${dir.id}&region=&dir=" />');
				
				<c:set var="pid" value="asia_${count.index}" />
				<c:forEach items="${dir.directories}" var="asia">
					<c:set var="dirName" value="${asia.name}" />
					<c:if test="${!empty asia.kmz}">
						<c:set var="dirName" value="<b>${asia.name}</b>" />
					</c:if>
					d.add('${asia.id}_${count.index}','${pid}','${dirName}','<s:url value="list.jsps?dir=${asia.id}&region=&country=" />','','','img/imgfolder.gif','img/imgfolder.gif');						
				</c:forEach>
			</c:forEach>
			
			d.add(2,0,'Sycylia','<s:url value="list.jsps?region=sicily&country=&dir=" />');
			<c:set var="start" value="3" />
			<c:forEach items="${sicilyDirs}" var="dir" varStatus="count">
				<c:set var="dirName" value="${dir.name}" />
				<c:if test="${!empty dir.kmz}">
					<c:set var="dirName" value="<b>${dir.name}</b>" />
				</c:if>
				d.add('sicily_${count.index}',2,'${dirName}','<s:url value="list.jsps?dir=${dir.id}&region=&country=" />','','','img/imgfolder.gif','img/imgfolder.gif');
			</c:forEach>
			document.write(d);
			//-->
		</script>
		</td></tr>
	</table>