<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<div id="${actionBean.divId}">
	<stripes:form id="${actionBean.formId}" action="javascript:${actionBean.submitCallback}">
		<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
	</stripes:form>
</div>