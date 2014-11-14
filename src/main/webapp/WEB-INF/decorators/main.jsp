<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><decorator:title default="java.bielu.com"/></title>
    <link href="<s:url  value='/styles/main.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/struts/niftycorners/niftyCorners.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css"/>
    <link href="<s:url value='/struts/niftycorners/niftyPrint.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css" media="print"/>
	<link rel="alternate" type="application/rss+xml" title="RSS" href="http://feeds.feedburner.com/Trojmiasto" />

    <script language="JavaScript" type="text/javascript" src="<s:url value='/struts/niftycorners/nifty.js' encode='false' includeParams='none'/>"></script>

    <script language="JavaScript" type="text/javascript">

        window.onload=function(){
            if(!NiftyCheck())
                return;
            Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
            Rounded("div#outer-header", "all", "white", "#818EBD", "smooth border #434F7C");
            Rounded("div#footer", "all", "white", "#818EBD", "smooth border #434F7C");
        }

    </script>

    <decorator:head/>
</head>

<body id="page-home">
<p style="color: #FFFFFF; font-size: 0.5pt;">
Java, JEE, Servlet, JSP, JFree, JFreeChart, AJAX, Struts, Hibernate, Spring Framework, Server Side, JavaScript,
Tomcat, JBoss, Ant, XDoclet, JUnit, Cocoon, Apache FOP, Jakarta POI, Eclipse, NetBeans, Design Patterns, 
Przemysław Bielicki, Przemyslaw Bielicki, Sun Certified Java Developer, SCJD, Sun Certified Developer for the Java 2 Platform,
Sun Certified Java Programmer, SCJP, Sun Certified Programmer for the Java 2 Platform, 
Java, J2EE, Java Developer, Java Programmer, Sun Certified Web Component Developer, SCWCD,
Aspect Oriented Programming, AspectJ, XML, XSL, XPath, UML, Unified Modelling Language, Code Refactoring, CVS,
Subversion, SVN, Mule ESB, Enterprise Service Bus, XFire, Axis, ActiveMQ, OSGi,
SOA, Service Oriented Architecture, Gdynia, Gdansk, Sopot, Trojmiasto,
Trójmiasto, Poland, Europe, From Java to JEE Blog,
http://java.bielu.com, http://www.bielu.com, http://java2jee.blogspot.com, http://www.szybkiprzewoz.pl
</p>

<div id="page">
    <div id="outer-header">
        <div id="header" class="clearfix">
            <div id="branding">
                <h1 class="title">java.bielu.com</h1>
                <c:set var="format">yyyy/MM/dd HH:mm</c:set>
                ${bielu:currentTime(format)}
            </div><!-- end branding -->

            <div id="search">
            	<br /><br />
            	<bielu:lastModified text="Last modified: "/>
            </div><!-- end search -->
        </div>
    </div><!-- end header -->

    <div id="content" class="clearfix">

        <decorator:body/>

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

	<div id="adsense" class="clearfix">
		<script type="text/javascript"><!--
		google_ad_client = "pub-7172588810444118";
		google_ad_width = 728;
		google_ad_height = 90;
		google_ad_format = "728x90_as";
		google_ad_type = "text_image";
		google_ad_channel = "";
		//-->
		</script>
		<script type="text/javascript"
		  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>
	</div>

    <div id="footer" class="clearfix">
    	<c:set var="startDate">2007</c:set>
        <p>Copyright &copy; ${bielu:copyrightDates(startDate)} Przemyslaw Bielicki</p>
    </div><!-- end footer -->

</div><!-- end page -->
<script src='http://www.google-analytics.com/urchin.js' type='text/javascript'>
</script>
<script type='text/javascript'>
_uacct = "UA-1876057-3";
urchinTracker();
</script>
</body>
</html>
