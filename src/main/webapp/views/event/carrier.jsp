<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Carriers</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/carrier.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1 class="pull-left">Carriers</h1>
			<button class="btn btn-success smallMargin" onclick="KPS.event.carrier.addCarrier();">Add new</button>
			<div id="carrierListContainer">
				<jsp:include page="carrierList.jsp"></jsp:include>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>