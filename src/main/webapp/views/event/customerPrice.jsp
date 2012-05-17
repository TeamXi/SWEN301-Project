<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">
	<stripes:layout-component name="title">Customer prices</stripes:layout-component>
	<stripes:layout-component name="styles">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customerprice.css" />
	</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customerprice.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="row-fluid">
			<div class="span12">
				<header class="jumbotron subhead">
					<h1>Customer prices</h1>
					<button class="btn btn-success add-button" onclick="KPS.event.customerprice.addCustomerPrice();"><i class="icon-plus icon-white"></i> Add new</button>
					<p class="lead">
						Charge the customers exorbitant amounts using our friendly UI.
					</p>
				</header>
				
				<div id="customerPriceListContainer">
					<jsp:include page="customerPriceList.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>