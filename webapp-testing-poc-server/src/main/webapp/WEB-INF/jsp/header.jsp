<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<style type="text/css" media="screen">
			@import url("<c:url value='/public/style.css'/>");
		</style>
		<title>
		    <fmt:message key="index.title" />
		</title>
	
	</head>

	<body class="spring">
		<div id="wrap">
			<div id="menu">
                <%@ include file="/WEB-INF/jsp/menu.jsp"%>
			</div>
            <div id="main">
                <div id="body">