<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Dashboard</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1>Dashboard</h1>
		</div>
	</stripes:layout-component>
</stripes:layout-render>