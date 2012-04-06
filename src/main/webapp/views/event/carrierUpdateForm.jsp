<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
	<stripes:form id="updateCarrierForm" action="javascript:KPS.event.carrier.submitUpdateForm('updateCarrierForm', ${actionBean.carrierId})">
		<stripes:label for="name">Name</stripes:label>
		<stripes:text name="name" value="${actionBean.name}"/>
		
		<stripes:submit name="submitbutton" style="visibility:hidden"></stripes:submit>
	</stripes:form>
</div>