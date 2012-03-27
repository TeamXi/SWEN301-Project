<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">New Carrier</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/carrier.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1>Create a New Carrier</h1>
		</div>
		<div class="span5">
			
			<stripes:form id="newCarrierForm" action="javascript:submitNewCarrierForm('newCarrierForm')" onsubmit="return validateCarrierForm(this)">
			
				<stripes:label for="name">Name</stripes:label>
				<stripes:text  name="name" />
								
				<stripes:submit name="submit" value="Add"></stripes:submit>
				
			</stripes:form>
		</div>

		<div class="modal fade" id="carrierAdded">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3>Carrier added!</h3>
			</div>
			<div class="modal-body">
				<p>Carrier added successfully</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn btn-primary" data-dismiss="modal">Ok</a>
			</div>
		</div>
		
	</stripes:layout-component>
</stripes:layout-render>