<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

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
		
		<stripes:label for="carrier">Carrier</stripes:label>
		<stripes:text class="carrierEntry" name="carrier" value="${actionBean.carrier}" disabled="${actionBean.disabledFormFields['carrier']}"/>
		
		<stripes:label for="weightCost">Cost per gram ($)</stripes:label>
		<stripes:text name="weightCost" value="${actionBean.weightCost}" disabled="${actionBean.disabledFormFields['weightCost']}"/>
		
		<stripes:label for="volumeCost">Cost per cm&sup3; ($)</stripes:label>
		<stripes:text name="volumeCost" value="${actionBean.volumeCost}" disabled="${actionBean.disabledFormFields['volumeCost']}"/>
		
		<stripes:label for="frequency">Time between departures (hours)</stripes:label>
		<stripes:text name="frequency" value="${actionBean.frequency}" disabled="${actionBean.disabledFormFields['frequency']}"/>
		
		<stripes:label for="duration">Transit duration (hours)</stripes:label>
		<stripes:text name="duration" value="${actionBean.duration}" disabled="${actionBean.disabledFormFields['duration']}"/>
		
		<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
	</stripes:form>
</div>