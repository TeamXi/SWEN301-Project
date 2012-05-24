<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ include file="/includes/main.jsp" %>

<c:choose>
	<c:when test="<%= isLoggedIn() %>">
		<% response.sendRedirect(request.getContextPath()+"/dashboard"); %>
	</c:when>
	<c:otherwise>
		<stripes:layout-render name="/layouts/KPSmart.jsp">
			<stripes:layout-component name="title">KPSmart</stripes:layout-component>
			<stripes:layout-component name="styles">
				<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home.css" />
			</stripes:layout-component>
			<stripes:layout-component name="content">
				<div class="row-fluid">
					<div class="span12">
						<div class="login-arrow"></div>
						<div class="hero-unit no-background-color">
							<h1>Welcome to KPSmart</h1>
							<p>Please sign in above to begin delivering mail!</p>
							<small class="copyright">&copy; 2012 Team ξ - Ashleigh Cains, Daniel Hodder, Melby Ruarus, Shreya Patel and Wouter Coppieters.</small>
						</div>
					</div>
				</div>
			</stripes:layout-component>
		</stripes:layout-render>
	</c:otherwise>
</c:choose>