<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formvalidation.js"></script>

<div id="newRouteCarouselWindow">
	<div id="newRouteCarousel">
		<div id="newRouteFormContainer">
			<stripes:form id="newRouteForm" action="javascript:KPS.event.route.submitNewForm('newRouteForm')">

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
			<input type="text" id="newLocationMapLocationName"></input> <button class="btn" onclick="KPS.event.location.search()">search</button>
			<br />
			<select id="newLocationMapLocationResults">
			</select>
			Is international: <input type="checkbox" checked="checked" id="newLocationMapLocationIsInternational" />
			<div id="newLocationMap"></div>
		</div>
	</div>
</div>