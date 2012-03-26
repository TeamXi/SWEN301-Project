<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">
	<stripes:layout-component name="scripts">
		<script type="text/javascript">
			var locationNames = <json:array var="location" items="${actionBean.stateManipulator.allLocations}">
									<json:property value="${location.name}"/>
								</json:array>;
			var locationList =	<json:array var="location" items="${actionBean.stateManipulator.allLocations}">
									<json:object>
										<json:property name="name" value="${location.name}"/>
										<json:property name="international" value="${location.international}"/>
									</json:object>
								</json:array>;
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mail.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="title">New Mail Delivery Event</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1>Create a mail delivery event</h1>
		</div>
		<div class="span5">
			
			<stripes:form id="newMailForm" action="javascript:submitMailDeliveryForm(this)" onsubmit="return validateMailDeliveryForm(this)">
			
				<stripes:label for="source">Source</stripes:label>
				<stripes:text class="portEntry" name="source" />
				
				<stripes:label for="destination">Destination</stripes:label>
				<stripes:text class="portEntry" name="destination" />
				
				<stripes:label for="priority">Priority</stripes:label>
				<stripes:select name="priority">
					<option value="placeholder">Select a Priority</option>
					<stripes:options-enumeration enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority"/>
				</stripes:select>
				
				<stripes:label for="weight">Weight (grams)</stripes:label>
				<stripes:text name="weight" />
				
				<stripes:label for="volume">Volume (cm&sup3;)</stripes:label>
				<stripes:text name="volume" />
				
				<stripes:submit name="submit" value="Add"></stripes:submit>
				
			</stripes:form>
		</div>
		
		
	</stripes:layout-component>
</stripes:layout-render>