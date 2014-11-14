<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>

<bielu:configReader />
<bielu:ipInfo />

<html>
<head>
	<s:head theme="simple"/>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=${googleMapsKey}" type="text/javascript"></script>
	<script type="text/javascript">
		  window.onload=function load() {
		    <c:if test="${showMap}">
			    if (GBrowserIsCompatible()) {
			      var map = new GMap2(document.getElementById("map"));
					map.addControl(new GSmallMapControl());
					map.addControl(new GMapTypeControl());
					map.setCenter(new GLatLng(${latitude}, ${longitude}), 10);
			    }
		    </c:if>
		    
		    document.getElementById('_ip').focus();
            document.getElementById('_ip').select();
            
		    if(!NiftyCheck()) {
                return;
            }
            Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
            Rounded("div#outer-header", "all", "white", "#818EBD", "smooth border #434F7C");
            Rounded("div#footer", "all", "white", "#818EBD", "smooth border #434F7C");
		  }
		  
		  window.onunload=function unload() {
		  	GUnload();
		  }
	</script>
</head>
<body>

	<br/>
	<br/>

	<c:if test="${!empty errorMessage}">
		<blockquote>
			<p class="error">
				${errorMessage}
				<!-- 
					${exceptionText} 
				-->
			</p>
		</blockquote>
		<p/>
	</c:if>

	<table width="100%">
		<tr>
		<td valign="top">
			<s:form action="" method="GET">
				<c:set var="inputIp">${ip}</c:set>
				<c:if test="${!empty hostname}">
					<c:set var="inputIp">${hostname}</c:set>
				</c:if>
		        <s:textfield label="IP or hostname:" name="ip" required="true" value="${inputIp}"/>
		        <s:submit value="Check location"/>
			</s:form>
		
			<c:if test="${empty errorMessage}">
			  	<p>
			  		Location for 
			  		<c:choose>
			  			<c:when test="${!empty hostname}">
				  			hostname <code>${hostname}</code> (IP address ${ip}):
			  			</c:when>
			  			<c:otherwise>
					  		IP address ${ip}:
			  			</c:otherwise>
			  		</c:choose>
			  	</p>
		  		<ul>
		  			<li>Country: ${country}</li>
		  			<li>Flag: <img src="http://api.hostip.info/flag.php?ip=${ip}" /></li>
		  			<li>City: ${city}</li>
		  		</ul>
		  	</c:if>
	  	</td>
	  	
		<td>
			<c:if test="${empty errorMessage}">
			    <c:if test="${showMap}">
				  	<center>
					    <div id="map" style="border-width: 1px; border-style: solid; width: 640px; height: 480px"></div>
				    </center>
			    </c:if>
			</c:if>
		</td>
		</tr>
		<tr>
		<td colspan="2">
			<c:if test="${empty errorMessage}">
			  	<p>
			  		Is this wrong? <a href="http://www.hostip.info/correct.html?spip=${ip}">Make a correction</a>
			    </p>
		    </c:if>
		</td>
		</tr>
	</table>

</body>
</html>