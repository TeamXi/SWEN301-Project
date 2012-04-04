<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mail.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>


<div class="alertSpacer" style="margin-top: 0px;">
	<div style="display: none;" id="addMailSuccessMessage"
		class="alert alert-success">Mail added successfully!
	</div>
</div>
<stripes:form id="newMailForm"
	onsubmit="return validateMailDeliveryForm(this)"
	action="javascript:submitMailDeliveryForm('newMailForm')">

	<stripes:label for="source">Source</stripes:label>
	<stripes:text class="portEntry" name="source" />

	<stripes:label for="destination">Destination</stripes:label>
	<stripes:text class="portEntry" name="destination" />

	<stripes:label for="priority">Priority</stripes:label>
	<stripes:select name="priority">
		<option value="placeholder">Select a Priority</option>
		<stripes:options-enumeration
			enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority" />
	</stripes:select>

	<stripes:label for="weight">Weight (grams)</stripes:label>
	<stripes:text name="weight" />

	<stripes:label for="volume">Volume (cm&sup3;)</stripes:label>
	<stripes:text name="volume" />
	
	<stripes:submit name="submit" style="visibility:hidden"></stripes:submit>
</stripes:form>