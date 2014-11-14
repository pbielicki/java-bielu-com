<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${pageCount gt 1}">
<div>
	<table>
	<tr>
		<td>Go to page:

		<c:if test="${page gt 1}">
			<s:url action="list" id="url">
				<s:param name="page">${page - 1}</s:param>
				<s:param name="thumbs">${param.thumbs}</s:param>
			</s:url>
			<a href="${url}"><b>&lt;</b></a>
		</c:if>
		<c:if test="${page le 1}">
			&lt;
		</c:if>

		<c:forEach begin="0" end="${pageCount - 1}" varStatus="count">
				<s:url action="list" id="url">
					<s:param name="page">${count.index + 1}</s:param>
					<s:param name="thumbs">${param.thumbs}</s:param>
				</s:url>
				
				<c:if test="${(count.index + 1) eq page}">
					${count.index + 1}
				</c:if>
				<c:if test="${(count.index + 1) ne page}">
					<a href="${url}"><b>${count.index + 1}</b></a>
				</c:if>
		</c:forEach>

		<c:if test="${page lt pageCount}">
			<s:url action="list" id="url">
				<s:param name="page">${page + 1}</s:param>
				<s:param name="thumbs">${param.thumbs}</s:param>
			</s:url>
			<a href="${url}"><b>&gt;</b></a>
		</c:if>
		<c:if test="${page ge pageCount}">
			&gt;
		</c:if>
		</td>
	</tr>
	</table>
</div>
<p/>
<p/>
</c:if>