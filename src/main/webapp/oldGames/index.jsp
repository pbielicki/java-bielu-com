<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String browser = request.getHeader("User-Agent");
	if (browser != null && browser.indexOf("MSIE") != -1 && browser.indexOf("Firefox") == -1) {
		request.getRequestDispatcher("index_ie.jsp").forward(request, response);
		return;
	}
%>
<html>
<head>
    <s:head theme="simple"/>
    
    <style>
    .label {
    	font-size: 12pt;
    	font-weight: bold;
    	height: 20px;
    	cursor: pointer;
	}
    </style>
</head>

<body>
<h2>Some old games written by me using Turbo Pascal and Assembler under DOS when I was in high school</h2>
<h5>(click on title to hide/show the screenshot)</h5>

<p/>

<div dojoType="TitlePane" label="Arkanoid (paranoic :)" labelNodeClass="label">
<img src="ark.png" />
</div>
<a href="games/ark.zip">ark.zip</a>

<p/>
	
<div dojoType="TitlePane" label="Darts" labelNodeClass="label">
<img src="darts.png" />
</div>
<a href="games/darts.zip">darts.zip</a>

<p/>

<div dojoType="TitlePane" label="Locoman (Locomotion sequel)" labelNodeClass="label">
<img src="locoman.png" />
</div>
<a href="games/locoman.zip">locoman.zip</a>

<p/>

<div dojoType="TitlePane" label="Shoot Out" labelNodeClass="label">
<img src="shoot.png" />
</div>
<a href="games/shoot.zip">shoot.zip</a>

</body>
</html>