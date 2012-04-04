<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:form id="updateCarrierForm"
	action="javascript:submitUpdateCarrierForm('updateCarrierForm', ${actionBean.carrierId})"
	onsubmit="return validateCarrierForm(this)">

	<stripes:label for="name">Name</stripes:label>
	<stripes:text name="name" value="${actionBean.name}"/>
	
	<stripes:submit name="submit" style="visibility:hidden"></stripes:submit>
</stripes:form>