<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>


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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>

<div id="newRouteCarouselWindow">
	<div id="newRouteCarousel">
		<div id="newRouteFormContainer">
			<stripes:form id="newRouteForm"
				action="javascript:submitNewRouteForm('newRouteForm')"
				onsubmit="return validateRouteForm(this)">

				<stripes:label for="source">Source</stripes:label>
				<stripes:text class="portEntry" name="source" />

				<stripes:label for="destination">Destination</stripes:label>
				<stripes:text class="portEntry" name="destination" />

				<stripes:label for="transportType">Transport type</stripes:label>
				<stripes:select name="transportType">
					<option value="placeholder">Select a transport type</option>
					<stripes:options-enumeration
						enum="nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans" />
				</stripes:select>
				
				<stripes:submit name="submit" style="visibility:hidden"></stripes:submit>
			</stripes:form>
		</div>
	

		<div id="newLocationMapWrapper">
			<input type="text" id="newLocationMapLocationName"></input> <button onclick="searchNewLocation()">search</button>
			<br />
			<select id="newLocationMapLocationResults">
			</select>
			Is international: <input type="checkbox" checked=true" id="newLocationMapLocationIsInternational" />
			<div id="newLocationMap"></div>
		</div>
	
	</div>
</div>