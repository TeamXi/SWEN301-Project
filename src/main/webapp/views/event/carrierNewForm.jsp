<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="alertSpacer" style="margin-top: 0px;">
	<div style="display: none;" id="carrierSuccessMessage" class="alert alert-success">Carrier added successfully!</div>
</div>
<stripes:form id="newCarrierForm" action="javascript:KPS.event.carrier.submitNewForm('newCarrierForm')">
	<stripes:label for="name">Name</stripes:label>
	<stripes:text name="name"/>
	
	<stripes:submit name="submit" style="visibility:hidden"></stripes:submit>
</stripes:form>