<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ include file="/includes/main.jsp" %>
<stripes:layout-definition>
	<!DOCTYPE html>
	<html>
		<head>
			<title><stripes:layout-component name="title" /></title>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
			<stripes:layout-component name="styles" />
			<script type="text/javascript">
				var KPS = {
					siteRoot: "${pageContext.request.contextPath}",
				};
			</script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jQuery-1.7.0.min.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/main.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mail.js"></script>

			<stripes:layout-component name="scripts" />
		</head>
		
		<body>
			<div id="menu" class="navbar navbar-fixed-top">
				<div class="navbar-inner">
					<div class="container-fluid">
						<a href="${pageContext.request.contextPath}/dashboard" class="brand">KPSmart</a>
						<div class="nav-collapse">
							<ul class="nav">
								<li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
								<c:if test="<%= isClerk() %>">
									<li><a href="#" class="new-mail-delivery-link">Mail Delivery</a>
									<li class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown">Routes<b class="caret"></b></a>
										<ul class="dropdown-menu">
											<li><a href="${pageContext.request.contextPath}/event/route">List</a></li>
											<li><a id="menu-newRouteDropdown" href="${pageContext.request.contextPath}/event/route#new">New</a></li>
										</ul>
									</li>
									<li class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown">Carriers<b class="caret"></b></a>
										<ul class="dropdown-menu">
											<li><a href="${pageContext.request.contextPath}/event/carrier">List</a></li>
											<li><a id="menu-newCarrierDropdown" href="${pageContext.request.contextPath}/event/carrier#new">New</a></li>
										</ul>
									</li>
								</c:if>
							</ul>
							
							<ul class="nav pull-right">
								<li class="dropdown">
									<a href="" class="dropdown-toggle" data-toggle="dropdown">
									<%= getRoleText() %>
									<b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a onClick="performLogin('${pageContext.request.contextPath}','manager');">Manager</a></li>
										<li><a onClick="performLogin('${pageContext.request.contextPath}','clerk');">Clerk</a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="modal fade" id="emptyLayoutModal">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h3 id="emptyLayoutModalTitle"></h3>
				</div>
				<div class="modal-body" id="emptyLayoutModalBody">
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-warning" id="emptyLayoutModalCancel"></a>
					<a href="#" class="btn btn-success" id="emptyLayoutModalSubmit"></a>
				</div>
			</div>

			<div class="container">
				<div class="row-fluid">
					<stripes:layout-component name="content" />
				</div>
			</div>
		</body>
	</html>
</stripes:layout-definition>