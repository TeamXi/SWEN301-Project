<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Routes</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/route.js"></script>
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1 class="pull-left">Routes</h1>
			<button class="btn btn-success smallMargin" onclick="KPS.event.route.addRoute();">Add new</button>
			
			<div id="routeListContainer">
				<jsp:include page="routeList.jsp"></jsp:include>
			</div>
		</div>
		
		<div style="display: none">
			<div id="newLocationMapWrapper">
				<input type="text" id="newLocationMapLocationName"></input> <button class="btn" onclick="KPS.event.location.search()">search</button>
				<br />
				<select id="newLocationMapLocationResults">
				</select>
				Is international: <input type="checkbox" checked="checked" id="newLocationMapLocationIsInternational" />
				<div id="newLocationMap"></div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>


