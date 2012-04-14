<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="${actionBean.divId}">
	<stripes:form id="${actionBean.formId}" class="form-horizontal" action="javascript:${actionBean.submitCallback}">
		<fieldset>
			<div class="control-group">
				<stripes:label class="control-label" for="source">Source</stripes:label>
				<div class="controls">
					<stripes:text class="portEntry" name="source" value="${actionBean.source}" disabled="${actionBean.disabledFormFields['source']}"/>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="destination">Destination</stripes:label>
				<div class="controls">
					<stripes:text class="portEntry" name="destination" value="${actionBean.destination}" disabled="${actionBean.disabledFormFields['destination']}"/>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="transportType">Transport type</stripes:label>
				<div class="controls">
					<stripes:select name="transportType" value="${actionBean.transportType}" disabled="${actionBean.disabledFormFields['transportType']}">
						<c:if test="${actionBean.transportType==null}">
							<option value="placeholder">Select a transport type</option>
						</c:if>
						<stripes:options-enumeration enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans" />
					</stripes:select>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="carrier">Carrier</stripes:label>
				<div class="controls">
					<stripes:text class="carrierEntry" name="carrier" value="${actionBean.carrier}" disabled="${actionBean.disabledFormFields['carrier']}"/>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="weightCost">Cost</stripes:label>
				<div class="controls">
					<div class="input-prepend input-append">
						<span class="add-on">$</span><stripes:text name="weightCost" value="${actionBean.weightCost}" disabled="${actionBean.disabledFormFields['weightCost']}"/><span class="add-on">/ gram</span>
					</div>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="volumeCost">Cost</stripes:label>
				<div class="controls">
					<div class="input-prepend input-append">
						<span class="add-on">$</span><stripes:text name="volumeCost" value="${actionBean.volumeCost}" disabled="${actionBean.disabledFormFields['volumeCost']}"/><span class="add-on">/ cm&sup3;</span>
					</div>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="frequency">Departure period</stripes:label>
				<div class="controls">
					<div class="input-append">
						<stripes:text name="frequency" value="${actionBean.frequency}" disabled="${actionBean.disabledFormFields['frequency']}"/><span class="add-on">hours</span>
					</div>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="duration">Transit duration</stripes:label>
				<div class="controls">
					<div class="input-append">
						<stripes:text name="duration" value="${actionBean.duration}" disabled="${actionBean.disabledFormFields['duration']}"/><span class="add-on">hours</span>
					</div>
				</div>
			</div>
			
			<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
		</fieldset>
	</stripes:form>
</div>