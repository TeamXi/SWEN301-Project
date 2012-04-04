<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Route List</stripes:layout-component>
	<stripes:layout-component name="content">
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/routes.js"></script>
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	</stripes:layout-component>
	
		<div class="span12">
			<h1 class="pull-left">Routes</h1>
			<button class="btn btn-success smallMargin" onclick="addRoute();">Add new</button>
		</div>
		<div id="routeListContainer">
			<jsp:include page="routeList.jsp"></jsp:include>
		</div>

	</stripes:layout-component>
</stripes:layout-render>


