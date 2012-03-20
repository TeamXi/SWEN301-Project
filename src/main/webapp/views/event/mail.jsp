<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">
	<stripes:layout-component name="scripts">
		<script type="text/javascript">
			var locationList = [];
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mail.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="title">Enter Mail Delivery Event</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1>Create a mail delivery event</h1>
			
			<stripes:form action="javascript:submitMailDeliveryForm(this)">
			
				<stripes:label for="source">Source</stripes:label>
				<stripes:text class="portEntry" name="source" />
				
				<stripes:label for="destination">Destination</stripes:label>
				<stripes:text class="porEntry" name="destination" />
				
				<stripes:label for="priority">Priority</stripes:label>
				<stripes:select name="priority">
					<stripes:options-enumeration enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority"/>
				</stripes:select>
				
				<stripes:label for="weight">Weight</stripes:label>
				<stripes:text name="weight" />
				
				<stripes:label for="volume">Volume</stripes:label>
				<stripes:text name="volume"/>
				
				<stripes:submit name="submit" value="Add"></stripes:submit>
				
			</stripes:form>
		</div>
		
		
	</stripes:layout-component>
</stripes:layout-render>