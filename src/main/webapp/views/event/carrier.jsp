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
		<div class="row-fluid">
			<div class="span12">
				<header class="jumbotron subhead">
					<h1>Carriers</h1>
					<button class="btn btn-success add-button" onclick="KPS.event.carrier.addCarrier();"><i class="icon-plus icon-white"></i> Add new</button>
					<p class="lead">
						View a list of KPSmart carriers&mdash;add, update or delete them to your hearts content!
					</p>
				</header>
				
				<div id="carrierListContainer">
					<jsp:include page="carrierList.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>