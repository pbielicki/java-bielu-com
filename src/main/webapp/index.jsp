<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <s:head theme="simple"/>
</head>

<body>
    <blockquote>    
        <p>
            In this site I'll try to present the power of server-side Java (but not only). 
            I'll try to update this site regularly by adding new web applications. 
            If you have any comments or requests you can reach me at: <font style="color: blue;">pbielicki at gmail.com</font>.
            
            <p/>
            
            This site is powered by: 
            	<a href="<s:url value="http://tomcat.apache.org" />">
            	<img src="<s:url value="/img/logos/tomcat.png" />" title="Apache Tomcat 6.x" alt="Apache Tomcat 6.x" /></a>,

            	<a href="<s:url value="http://www.hibernate.org" />">
            	<img src="<s:url value="/img/logos/hibernate.png" />" title="Hibernate" alt="Hibernate" /></a>,

            	<a href="<s:url value="http://www.springframework.org" />">
            	<img src="<s:url value="/img/logos/spring_logo.png" />" title="Spring Framework" alt="Spring Framework" /></a>,
 
            	<a href="<s:url value="http://struts.apache.org/2.x" />"> 
            	<img src="<s:url value="/img/logos/struts2.png" />" title="Struts 2.x" alt="Struts 2.x" /></a>,
            	and some more  <a href="<s:url value="http://java.sun.com" />">Java</a> stuff ;)
            <p/>
            JavaScript and AJAX stuff are powered by <a href="http://www.dojotoolkit.com">
            <img src="<s:url value="/img/logos/dojo.png" />" title="Dojo Toolkit" alt="Dojo Toolkit" /></a> 
            and <a href="<s:url value="http://www.walterzorn.com" />">
            <img src="http://www.walterzorn.com/images/logo.gif" title="www.walterzorn.com" alt="www.walterzorn.com" /></a>
            <p/>
            Icons taken from <a href="http://www.everaldo.com/crystal/"><img src="<s:url value="/img/logos/eve_sign.png" />" title="Crystal" alt="Crystal" /></a>
            <p/>
            I build my projects using <a href="<s:url value="http://maven.apache.org" />">
            <img src="http://maven.apache.org/images/logos/maven-feather.png" title="http://maven.apache.org" alt="http://maven.apache.org" /></a>
            <p/>
            
            Enjoy!!!
            <p/>
        </p>
    </blockquote>
    
    <p/>
    
    <%-- THIS LIST IS MAINTAINED IN WEB-INF/decorators/main.jsp TO CREATE THE MENU BAR -- EDIT THERE AND COPY HERE --%>
    <ul>
        <li><a href="<s:url value="/ewa" includeParams='none' />">TIBCO EMS Web Admin</a> &nbsp;<blink style="font-weight: bold; color: red;">NEW!</blink></li>
        <li><a href="<s:url value="/" includeParams='none' />">Home</a></li>
        <li><a href="<s:url value="/aboutMe/" includeParams='none' />">About</a></li>
        <li><a href="<s:url value="/locateMe/" includeParams='none' />">Locate Me (GeoIP)</a></li>
        <li><a href="<s:url action="list" includeParams='none' />">Image Database</a></li>
        <li><a href="<s:url value="/statistics/" includeParams='none' />">Image Statistics</a></li>
        <li><a href="<s:url value="/stats/" includeParams='none' />">Site Statistics</a></li>
        <li><a href="<s:url value="/oldGames/" includeParams='none' />">Old Games</a></li>
        <li class="last"><a href="<s:url value="http://www.bielu.com" includeParams='none'/>">bielu.com</a></li>
     </ul>
	
</body>
</html>
