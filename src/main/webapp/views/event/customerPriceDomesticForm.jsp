<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="${actionBean.divId}">
	<stripes:form id="${actionBean.formId}" class="form-horizontal" action="javascript:${actionBean.submitCallback}">
		<div class="control-group">
			<stripes:label class="control-label" for="priority">Priority</stripes:label>
			<div class="controls">
				<stripes:select name="priority" value="${actionBean.priority}" disabled="${actionBean.disabledFormFields['priority']}">
					<c:if test="${actionBean.priority==null}">
						<option value="placeholder">Select a Priority</option>
					</c:if>
					<stripes:option value="Domestic_Air">Domestic Air</stripes:option>
					<stripes:option value="Domestic_Standard">Domestic Standard</stripes:option>
				</stripes:select>
			</div>
		</div>
		
		<div class="control-group">
			<stripes:label class="control-label" for="weightPrice">Price</stripes:label>
			<div class="controls">
				<div class="input-prepend input-append">
					<span class="add-on">$</span><stripes:text name="weightPrice" value="${actionBean.weightPrice}" disabled="${actionBean.disabledFormFields['weightPrice']}"></stripes:text><span class="add-on">/ gram</span>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<stripes:label class="control-label" for="volumePrice">Price</stripes:label>
			<div class="controls">
				<div class="input-prepend input-append">
					<span class="add-on">$</span><stripes:text name="volumePrice" value="${actionBean.volumePrice}" disabled="${actionBean.disabledFormFields['volumePrice']}"></stripes:text><span class="add-on">/ cm&sup3;</span>
				</div>
			</div>
		</div>
		
		<fieldset>
			<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
		</fieldset>
	</stripes:form>
</div>