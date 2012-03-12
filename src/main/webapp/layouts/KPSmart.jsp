<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-definition>
	<!DOCTYPE html>
	<html>
		<head>
			<title><stripes:layout-component name="title" /></title>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
			<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
		</head>
		
		<body>
			<div id="menu" class="navbar navbar-fixed-top">
				<div class="navbar-inner">
					<div class="container-fluid">
						<a href="${pageContext.request.contextPath}/home" class="brand">KPSmart</a>
						<div class="nav-collapse">
							<ul class="nav">
								<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">Enter Event <b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a href="${pageContext.request.contextPath }/event/mail">Mail Delivery</a>
									</ul>
								</li>
							</ul>
							
							<p class="navbar-text pull-right">You are logged in as ...</p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="container-fluid">
				<stripes:layout-component name="content" />
			</div>
		</body>
	</html>
</stripes:layout-definition>