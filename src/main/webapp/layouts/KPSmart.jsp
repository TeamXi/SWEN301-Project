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
						<a href="${pageContext.request.contextPath}/dashboard" class="brand">KPSmart</a>
						<div class="nav-collapse">
							<ul class="nav">
								<li><a href="${pageContext.request.contextPath}/dashboard">Home</a></li>
								<li><a href="${pageContext.request.contextPath }/event/mail">Mail Delivery</a>
								<li class="dropdown">
									<a href="${pageContext.request.contextPath }/event/route#list" class="dropdown-toggle" data-toggle="dropdown">View Routes<b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a href="${pageContext.request.contextPath }/event/route#new">New</a></li>
									</ul>
								</li>
							</ul>
							
							<ul class="nav pull-right">
								<li class="dropdown">
									<a href="" class="dropdown-toggle" data-toggle="dropdown">Log in<b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a href="">Manager</a></li>
										<li><a href="">Clerk</a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="container-fluid">
				<div class="row-fluid">
					<stripes:layout-component name="content" />
				</div>
			</div>
		</body>
	</html>
</stripes:layout-definition>