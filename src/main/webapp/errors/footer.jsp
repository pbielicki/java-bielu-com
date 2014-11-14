<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>
        <div id="nav">
        	<div id="wrapper" align="center">
	        	<table>
	        	<tr>
	        		<td><a title="Home" href="<s:url value="/" includeParams='none' />"><img src="<s:url value="/img/icons/browser.png" />" /></a></td>
					<td><a title="About" href="<s:url value="/aboutMe/" includeParams='none' />"><img src="<s:url value="/img/icons/kdmconfig.png" />" /></a></td>
					<td><a title="Locate Me (GeoIP)" href="<s:url value="/locateMe/" includeParams='none' />"><img src="<s:url value="/img/icons/locator.png" />" /></a></td>
					<td><a title="Image Database" href="<s:url value="/list.jsps" includeParams='none' />"><img src="<s:url value="/img/icons/lphoto.png" />" /></a></td>
					<td><a title="Image Statistics" href="<s:url value="/imageStats/" includeParams='none' />"><img src="<s:url value="/img/icons/imageCharts.png" />" /></a></td>
					<td><a title="Site Statistics" href="<s:url value="/siteStats/" includeParams='none' />"><img src="<s:url value="/img/icons/siteCharts.png" />" /></a></td>
					<td><a title="Old Games" href="<s:url value="/oldGames/" includeParams='none' />"><img src="<s:url value="/img/icons/clanbomber.png" />" /></a></td>
					<td><a title="bielu.com" href="http://www.bielu.com"><img src="<s:url value="/img/icons/exit.png" />" /></a></td>
				</tr>
				</table>
			</div>
        </div><!-- end nav -->

    </div><!-- end content -->

    <div id="footer" class="clearfix">
    	<c:set var="startDate">2007</c:set>
        <p>Copyright &copy; ${bielu:copyrightDates(startDate)} Przemyslaw Bielicki</p>
    </div><!-- end footer -->

</div><!-- end page -->
</body>
</html>