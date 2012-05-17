<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/includes/main.jsp" %>

<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Dashboard</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/highcharts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/exporting.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/charts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
		<script type="text/javascript">
			var KPS = KPS || {};
			KPS.dashboard = KPS.dashboard || {};
			
			KPS.dashboard.events = <%@include file="eventListJSON.jsp"%>;
			KPS.dashboard.currentEvent = ${actionBean.atevent};
			if(KPS.dashboard.currentEvent <= 0) {
				KPS.dashboard.currentEvent = KPS.dashboard.events.length;
			}
			
			<%@include file="charts.jsp"%>
			KPS.graphs.currentEvent = KPS.dashboard.currentEvent;
			KPS.graphs.events = KPS.dashboard.events;
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="styles">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard.css" />
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="row-fluid">
			<div class="span12">
				<div id="eventbacklogalert" class="alert alert-info" style="display:none">
					<a class="close" href="javascript:KPS.dashboard.hideEventAlert()">&times;</a>
					<b>Heads up!</b> <span id="eventbacklogcount"></span> occurred since you loaded the page, refresh to view the updates.
				</div>
				<header class="jumbotron subhead">
					<h1>Dashboard</h1>
					<p class="lead">
						<c:choose>
							<c:when test="<%= isManager() %>">
								Welcome to the dashboard, use the event bar to view statistics about KPSmart throughout time.
							</c:when>
							<c:otherwise>
								Welcome to the dashboard, here you can view the current financial status of KPSmart.
							</c:otherwise>
						</c:choose>
					</p>
				</header>
			</div>
		</div>
		
		<c:if test="<%= isManager() %>">
			<div class="row-fluid">
				<div class="span12" id="eventscrubber" style="opacity:0.001">
					<table id="eventpager">
						<tr>
							<td id="larrId"><a href="#" onclick="KPS.dashboard.goLeft(this)">&larr; Older events</a></td>
							<td id="eventListContainer">
								<div style="width: 10000px;" id="eventList">
									
								</div>
							</td>
							<td id="rarrId"><a href="#" onclick="KPS.dashboard.goRight(this)">Newer events &rarr;</a></td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
		<div class="row-fluid">
			<div class="span4">
				<div class="well">
					Revenue
					<h1 class="color-green"><fmt:formatNumber type="currency" value="${actionBean.reportManager.totalRevenue}"/></h1>
				</div>
			</div>
			<div class="span4">
				<div class="well">
					Expenditure
					<h1 class="color-red"><fmt:formatNumber type="currency" value="${actionBean.reportManager.totalExpenditure}"/></h1>
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
					<ul id="main-tabs" class="nav nav-tabs">
						<li><a href="#dashboard-tab-critical-routes" data-toggle="tab"><i class="icon-exclamation-sign"></i> Critical Routes</a></li>
						<li class="activate-graph"><a href="#dashboard-tab-revenue-expenditure" data-toggle="tab"><i class="icon-signal"></i> Revenue &amp; Expenditure</a></li>
						<li class="activate-graph"><a href="#dashboard-tab-no-of-events" data-toggle="tab"><i class="icon-time"></i> Number of Events</a></li>
						<li><a href="#dashboard-tab-rawdata" data-toggle="tab"><i class="icon-list-alt"></i> Raw data</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane" id="dashboard-tab-critical-routes">
							<table id="critical-routes-table" class="table table-bordered table-striped responsive-utilities">
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
									<c:choose>
										<c:when test="${actionBean.hasCriticalRoutes()}">
											<c:forEach var="revenueExpenditure" items="${actionBean.reportManager.allRevenueExpenditure}">
												<c:if test="${revenueExpenditure.expenditure>revenueExpenditure.revenue}">
													<tr class="color-red">
														<td><c:out value="${revenueExpenditure.startPoint.name}"/></td>
														<td><c:out value="${revenueExpenditure.endPoint.name}"/></td>
														<td><fmt:message key="Priority.${revenueExpenditure.priority}"/></td>
														<td><fmt:formatNumber type="currency" value="${revenueExpenditure.revenue}" /></td>
														<td><fmt:formatNumber type="currency" value="${revenueExpenditure.expenditure}" /></td>
														<td>
															<c:choose>
																<c:when test="${revenueExpenditure.averageDeliveryTime!='NaN'}">
																	<fmt:formatNumber type="number" maxFractionDigits="1" value="${revenueExpenditure.averageDeliveryTime}"/> hours
																</c:when>
																<c:otherwise>
																	No data
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="6">There are no critical routes</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="dashboard-tab-revenue-expenditure">
						</div>
						<div class="tab-pane" id="dashboard-tab-no-of-events">
						</div>
						<div class="tab-pane" id="dashboard-tab-rawdata">
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
											<td><fmt:message key="Priority.${revenueExpenditure.priority}"/></td>
											<td><fmt:formatNumber type="currency" value="${revenue}" /></td>
											<td><fmt:formatNumber type="currency" value="${expenditure}" /></td>
											<td>
												<c:choose>
													<c:when test="${averageDeliveryTime!='NaN'}">
														<fmt:formatNumber type="number" maxFractionDigits="1" value="${averageDeliveryTime}"/> hours
													</c:when>
													<c:otherwise>
														No data
													</c:otherwise>
												</c:choose>
											</td>
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
										<td>Total weight (grams)</td>
										<td>Total volume (cm&sup3;)</td>
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