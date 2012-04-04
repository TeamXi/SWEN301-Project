<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">New Carrier</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/carrier.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1 class="pull-left">Carriers</h1>
			<button class="btn btn-success smallMargin" onclick="addCarrier();">Add new</button>
			<div id="carrierListContainer">
				<jsp:include page="carrierList.jsp"></jsp:include>
			</div>
			<div class="modal fade" id="updateCarrierModal">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h3>Update carrier</h3>
				</div>
				<div class="modal-body">
					<div id="updateFormContainer">
					</div>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-primary" data-dismiss="modal">Cancel</a>
					<a href="#" class="btn btn-primary" onclick="submitUpdateModal()">Update</a>
				</div>
			</div>
			<div class="modal fade" id="addCarrierModal">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h3>Add carrier</h3>
				</div>
				<div class="modal-body">
					<div class="alertSpacer">
						<div style="display: none;" id="addCarrierSuccessMessage"
							class="alert alert-success">Carrier added successfully!
						</div>
					</div>
					<stripes:form id="newCarrierForm"
						action="javascript:submitNewCarrierForm('newCarrierForm')"
						onsubmit="return validateCarrierForm(this)">
	
						<stripes:label for="name">Name</stripes:label>
						<stripes:text name="name" />
					</stripes:form>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-primary" data-dismiss="modal">Cancel</a>
					<a href="#" class="btn btn-primary" onclick="submitAddModal()">Add</a>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>