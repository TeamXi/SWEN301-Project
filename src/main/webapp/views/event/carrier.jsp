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
		<div class="switch-container">
			<div data-window-hash="new">
				<div class="span12">
					<h1>Create a New Carrier</h1>
				</div>
				<div class="span5">
	
					<stripes:form id="newCarrierForm"
						action="javascript:submitNewCarrierForm('newCarrierForm')"
						onsubmit="return validateCarrierForm(this)">
	
						<stripes:label for="name">Name</stripes:label>
						<stripes:text name="name" />
	
						<stripes:submit name="submit" value="Add"></stripes:submit>
	
					</stripes:form>
				</div>
			</div>
			<div id="carrierListContainer" data-window-hash="list">
				<jsp:include page="carrierList.jsp"></jsp:include>
			</div>
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
				<a href="#list" class="btn btn-primary" data-dismiss="modal">Cancel</a>
				<a href="#list" class="btn btn-primary" onclick="submitUpdateModal()">Update</a>
			</div>
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