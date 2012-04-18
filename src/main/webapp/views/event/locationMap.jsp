<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Carriers</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/locationmap.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="styles">
		<style type="text/css">
			#allLocationsMap {
				position: absolute;
				top: 0;
				bottom: 0;
				left: 0;
				right: 0;
			}
		</style>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div id="allLocationsMap">
		</div>
	</stripes:layout-component>
</stripes:layout-render>