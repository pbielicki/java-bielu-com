<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="bielu" uri="http://java.bielu.com/tags/bielu" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>java.bielu.com</title>
    <link href="/styles/main.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="/struts/niftycorners/niftyCorners.css" rel="stylesheet" type="text/css"/>
    <link href="/struts/niftycorners/niftyPrint.css" rel="stylesheet" type="text/css" media="print"/>

    <script language="JavaScript" type="text/javascript" src="/struts/niftycorners/nifty.js"></script>

    <script language="JavaScript" type="text/javascript">

        window.onload=function(){
            if(!NiftyCheck())
                return;
            Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
            Rounded("div#outer-header", "all", "white", "#818EBD", "smooth border #434F7C");
            Rounded("div#footer", "all", "white", "#818EBD", "smooth border #434F7C");
        }

    </script>

    <meta http-equiv="Refresh" content="10; URL=http://java.bielu.com"> 
    <script language="JavaScript" type="text/javascript">
    // Dojo configuration
    djConfig = {
        baseRelativePath: "/struts/dojo",
        isDebug: false,
        bindEncoding: "UTF-8",
        debugAtAllCosts: true // not needed, but allows the Venkman debugger to work with the includes
    };
</script>
<script language="JavaScript" type="text/javascript" src="/struts/dojo/dojo.js"></script>
<script language="JavaScript" type="text/javascript" src="/struts/simple/dojoRequire.js"></script>

</head>

<body id="page-home">


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
    
    <p/>