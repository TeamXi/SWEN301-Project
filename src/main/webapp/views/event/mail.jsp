<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<div>
	<stripes:form id="newMailForm" class="form-horizontal" action="javascript:KPS.event.maildelivery.submitForm('newMailForm')">
		<fieldset>
			<div class="control-group">
				<stripes:label class="control-label" for="source">Source</stripes:label>
				<div class="controls">
					<stripes:text class="portEntry" name="source" />
				</div>
			</div>
		
			<div class="control-group">
				<stripes:label class="control-label" for="destination">Destination</stripes:label>
				<div class="controls">
					<stripes:text class="portEntry" name="destination" />
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="priority">Priority</stripes:label>
				<div class="controls">
					<stripes:select name="priority">
						<option value="placeholder">Select a Priority</option>
						<stripes:options-enumeration
							enum="nz.ac.victoria.ecs.kpsmart.entities.state.Priority" />
					</stripes:select> <img id="select-spinner" style="display:none" src="${pageContext.request.contextPath}/resources/img/spinner.gif"></img>
				</div>
			</div>
		
			<div class="control-group">
				<stripes:label class="control-label" for="weight">Weight</stripes:label>
				<div class="controls">
					<div class="input-append">
						<stripes:text name="weight" /><span class="add-on">grams</span>
					</div>
				</div>
			</div>
			
			<div class="control-group">
				<stripes:label class="control-label" for="volume">Volume</stripes:label>
				<div class="controls">
					<div class="input-append">
						<stripes:text name="volume" /><span class="add-on">cm&sup3;</span>
					</div>
				</div>
			</div>
			
			<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
		</fieldset>
	</stripes:form>
</div>

<div>
	<dl class="dl-horizontal">
		<dt>Revenue</dt><dd id="mail-success-info-revenue">$0.00</dd>
		<dt>Expenditure</dt><dd id="mail-success-info-expenditure">$0.00</dd>
		<dt>Delivery time</dt><dd id="mail-success-info-delivery-duration">0.0 hours</dd>
	</dl>
	<div id="mail-success-info-route-map" class="modal-map"></div>
</div>