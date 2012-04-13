<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="${actionBean.divId}">
	<stripes:form id="${actionBean.formId}" class="form-horizontal" action="javascript:${actionBean.submitCallback}">
		<fieldset>
			<div class="control-group">
				<stripes:label class="control-label" for="name">Name</stripes:label>
				<div class="controls">
					<stripes:text name="name" value="${actionBean.name}"/>
				</div>
			</div>
			
			<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
		</fieldset>
	</stripes:form>
</div>