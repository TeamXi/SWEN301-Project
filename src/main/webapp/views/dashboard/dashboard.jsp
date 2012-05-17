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
							<td><a href="dashboard" onclick="">Now</a></td>
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
							<div class="loading-container">
								<div class="loading-content">
									<h1>Loading</h1>
									<div class="progress progress-striped active">
										<div class="bar" style="width: 100%;"></div>
									</div>
								</div>
							</div>
							
							<script>
								$(document).ready(function() {
									$("#dashboard-tab-critical-routes").load("dashboard?tab-criticalroutes");
								});
							</script>
						</div>
						<div class="tab-pane" id="dashboard-tab-revenue-expenditure">
							<div class="loading-container">
								<div class="loading-content">
									<h1>Loading</h1>
									<div class="progress progress-striped active">
										<div class="bar" style="width: 100%;"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="dashboard-tab-no-of-events">
							<div class="loading-container">
								<div class="loading-content">
									<h1>Loading</h1>
									<div class="progress progress-striped active">
										<div class="bar" style="width: 100%;"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="dashboard-tab-rawdata">
							<div class="loading-container">
								<div class="loading-content">
									<h1>Loading</h1>
									<div class="progress progress-striped active">
										<div class="bar" style="width: 100%;"></div>
									</div>
								</div>
							</div>
							
							<script>
								$(document).ready(function() {
									$("#dashboard-tab-rawdata").load("dashboard?tab-rawdata");
								});
							</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>