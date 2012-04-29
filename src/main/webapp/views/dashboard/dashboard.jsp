<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Dashboard</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dateformat.js"></script>
		<script type="text/javascript">
			var events = <%@include file="eventListJSON.jsp"%>;
			$(document).ready(function() {
				var eventList = document.getElementById("eventlist");
				
				for(var n=0;n<events.length;n++) {
					var eventEl = document.createElement('a');
					eventEl.innerHTML = events[n].id;
					(function(event) {
						$(eventEl).tooltip({title: new Date(event.timestamp).format("h:Mtt ddd dS mmm 'yy")}).click(function() {
							window.location = KPS.siteRoot + "/dashboard?atevent="+event.id;
						});
					}(events[n]));
					var td = document.createElement('td');
					td.appendChild(eventEl);
					eventList.appendChild(td);
				}
			});
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="styles">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard.css" />
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="row-fluid">
			<div class="span12">
				<h1>Dashboard</h1>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span12" id="eventscrubber">
					<table id="eventpager">
						<tr>
							<td><a href="#">&larr;</a></td>
							<td>
								<div>
									<table class="nested">
										<tr id="eventlist"></tr>
									</table>
								</div>
							</td>
							<td><a href="#">&rarr;</a></td>
						</tr>
					</table>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<div class="well">
					Revenue
					<h1 class="color-green">$${actionBean.reportManager.totalRevenue}</h1>
				</div>
			</div>
			<div class="span4">
				<div class="well">
					Expenditure
					<h1 class="color-red">$${actionBean.reportManager.totalExpenditure}</h1>
				</div>
			</div>
			<div class="span4">
				<div class="well">
					Events
					<h1><c:out value="${actionBean.reportManager.numberOfEvents}"/></h1>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="tabbable">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#dashboard-tab-rawdata" data-toggle="tab">Raw data</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="dashboard-tab-rawdata">
							<table class="table table-bordered table-striped responsive-utilities">
								<thead>
									<tr>
										<td>Source</td>
										<td>Destination</td>
										<td>Priority</td>
										<td>Revenue</td>
										<td>Expenditure</td>
										<td>Average delivery time</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="revenueExpenditure" items="${actionBean.reportManager.allRevenueExpenditure}">
										<c:set value="${revenueExpenditure.revenue}" var="revenue"></c:set>
										<c:set value="${revenueExpenditure.expenditure}" var="expenditure"></c:set>
										<c:set value="${revenueExpenditure.averageDeliveryTime}" var="averageDeliveryTime"></c:set>
										<tr class="${expenditure>revenue?'color-red':''}">
											<td><c:out value="${revenueExpenditure.startPoint.name}"></c:out></td>
											<td><c:out value="${revenueExpenditure.endPoint.name}"></c:out></td>
											<td><c:out value="${revenueExpenditure.priority}"></c:out></td>
											<td><c:out value="$${revenue}"></c:out></td>
											<td><c:out value="$${expenditure}"></c:out></td>
											<td><c:out value="${averageDeliveryTime=='NaN'?'No data':averageDeliveryTime}${averageDeliveryTime=='NaN'?'':' hours'}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="table table-bordered table-striped responsive-utilities">
								<thead>
									<tr>
										<td>Source</td>
										<td>Destination</td>
										<td>Number of items</td>
										<td>Total weight</td>
										<td>Total volume</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="mailCount" items="${actionBean.reportManager.amountsOfMailForAllRoutes}">
										<tr>
											<td><c:out value="${mailCount.startPoint.name}"></c:out></td>
											<td><c:out value="${mailCount.endPoint.name}"></c:out></td>
											<td><c:out value="${mailCount.items}"></c:out></td>
											<td><c:out value="${mailCount.totalWeight}"></c:out></td>
											<td><c:out value="${mailCount.totalVolume}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>