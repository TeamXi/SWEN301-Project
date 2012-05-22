<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/includes/main.jsp" %>

<fmt:setLocale value="${pageContext.request.locale}"/>

<stripes:layout-definition>
	<!DOCTYPE html>
	<html>
		<head>
			<title><stripes:layout-component name="title" /></title>
			<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
			<stripes:layout-component name="styles" />
			<script type="text/javascript">
				var KPS = {
					siteRoot: "${pageContext.request.contextPath}",
				};
			</script>
			<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/modernizr.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jQuery-1.7.0.min.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dateformat.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/main.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mail.js"></script>
			<script type="text/javascript">
				if(Modernizr.touch) {
					$(document).ready(function() {
			            KPS.util.loadStyleSheet(KPS.siteRoot+'/resources/css/touch.css');
					});
				}
			</script>
			<stripes:layout-component name="scripts" />
		</head>
		
		<body data-spy="scroll" data-target=".subnav" data-offset="50" data-twttr-rendered="true">
			<div id="menu" class="navbar navbar-fixed-top">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			            	<span class="icon-bar"></span>
			            	<span class="icon-bar"></span>
			            	<span class="icon-bar"></span>
						</a>
						<a <c:if test="<%= isLoggedIn() %>">href="${pageContext.request.contextPath}/"</c:if> class="brand">KPSmart</a>
						<div class="nav-collapse">
							<ul class="nav">
								<c:if test="<%= isLoggedIn() %>">
									<li><a href="${pageContext.request.contextPath}/dashboard"><i class="icon-picture icon-white"></i> Dashboard</a></li>
									<c:if test="<%= isClerk() %>">
										<li><a href="#" class="new-mail-delivery-link"><i class="icon-envelope icon-white"></i> Mail delivery</a></li>
										<li class="dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-road icon-white"></i> Routes<b class="caret"></b></a>
											<ul class="dropdown-menu">
												<li class="hover-icon-white"><a href="${pageContext.request.contextPath}/event/route"><i class="icon-th-list"></i> List</a></li>
												<li class="hover-icon-white"><a id="menu-newRouteDropdown" href="${pageContext.request.contextPath}/event/route#new"><i class="icon-plus"></i> New</a></li>
											</ul>
										</li>
										<li class="dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-plane icon-white"></i> Carriers<b class="caret"></b></a>
											<ul class="dropdown-menu">
												<li class="hover-icon-white"><a href="${pageContext.request.contextPath}/event/carrier"><i class="icon-th-list"></i> List</a></li>
												<li class="hover-icon-white"><a id="menu-newCarrierDropdown" href="${pageContext.request.contextPath}/event/carrier#new"><i class="icon-plus"></i> New</a></li>
											</ul>
										</li>
										<li class="dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-gift icon-white"></i> Customer prices<b class="caret"></b></a>
											<ul class="dropdown-menu">
												<li class="hover-icon-white"><a href="${pageContext.request.contextPath}/event/customerprice"><i class="icon-th-list"></i> List</a></li>
												<li class="hover-icon-white"><a id="menu-newCustomerPriceDropdown" href="${pageContext.request.contextPath}/event/customerprice#new"><i class="icon-plus"></i> New</a></li>
											</ul>
										</li>
									</c:if>
									<li><a href="${pageContext.request.contextPath}/event/location?map"><i class="icon-map-marker icon-white"></i> Locations map</a></li>
								</c:if>
							</ul>
							
							<ul class="nav pull-right">
								<li class="dropdown">
									<a href="" class="dropdown-toggle" data-toggle="dropdown">
									<i class="icon-user icon-white"></i> <%= getRoleText() %>
									<b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a onClick="KPS.util.user.login('manager');">Manager</a></li>
										<li><a onClick="KPS.util.user.login('clerk');">Clerk</a></li>
										<c:if test="<%= isLoggedIn() %>">
											<li><a onClick="KPS.util.user.logout();">Sign out</a></li>
										</c:if>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div class="container content">
				<stripes:layout-component name="content" />
			</div>
			<div id="loadingMask">​
				<p id="loadingMessage">
					Loading. Please be patient..
				</p>
				<div class="progress loadingOverlay progress-striped active">
 					 <div class="bar"style="width: 100%; border-radius: 5px; height: 100%;"></div>
				</div>
			</div >
		</body>
	</html>
</stripes:layout-definition>