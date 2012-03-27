<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">New Route</stripes:layout-component>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/routes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1>Create a New Route</h1>
		</div>
		<div class="span5">
			
			<stripes:form id="newRouteForm" action="javascript:submitNewRouteForm('newRouteForm')" onsubmit="return validateRouteForm(this)">
			
				<stripes:label for="source">Source</stripes:label>
				<stripes:text class="portEntry" name="source" />
				
				<stripes:label for="destination">Destination</stripes:label>
				<stripes:text class="portEntry" name="destination" />
				
				<stripes:label for="transportType">Transport type</stripes:label>
				<stripes:select name="transportType">
					<option value="placeholder">Select a transport type</option>
					<stripes:options-enumeration enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans"/>
				</stripes:select>
				
								
				<stripes:submit name="submit" value="Add"></stripes:submit>
				
			</stripes:form>
		</div>
		<div class="modal fade" id="routeAdded">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3>Route added!</h3>
			</div>
			<div class="modal-body">
				<p>Route added successfully</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn btn-primary" data-dismiss="modal">Ok</a>
			</div>
		</div>
	
		
	</stripes:layout-component>
</stripes:layout-render>