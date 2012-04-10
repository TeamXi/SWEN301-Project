<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>

<div id="${actionBean.divId}">
	<stripes:form id="${actionBean.formId}" action="javascript:${actionBean.submitCallback}">

		<stripes:label for="source">Source</stripes:label>
		<stripes:text class="portEntry" name="source" value="${actionBean.source}" disabled="${actionBean.disabledFormFields['source']}"/>

		<stripes:label for="destination">Destination</stripes:label>
		<stripes:text class="portEntry" name="destination" value="${actionBean.destination}" disabled="${actionBean.disabledFormFields['destination']}"/>

		<stripes:label for="transportType">Transport type</stripes:label>
		<stripes:select name="transportType" value="${actionBean.transportType}" disabled="${actionBean.disabledFormFields['transportType']}">
			<option value="placeholder">Select a transport type</option>
			<stripes:options-enumeration enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans" />
		</stripes:select>
		
		<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
	</stripes:form>
</div>