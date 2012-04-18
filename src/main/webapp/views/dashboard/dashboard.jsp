<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">

	<stripes:layout-component name="title">Dashboard</stripes:layout-component>
	<stripes:layout-component name="scripts">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="row-fluid">
			<div class="span12">
				<h1>Dashboard</h1>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<div class="well">
					Revenue
					<h1 class="color-green">$1382.32</h1>
				</div>
			</div>
			<div class="span4">
				<div class="well">
					Expenditure
					<h1 class="color-red">$993.65</h1>
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
				<table class="table table-bordered table-striped responsive-utilities">
					<thead>
						<tr>
							<td>Source</td>
							<td>Destination</td>
							<td>Priority</td>
							<td>Revenue</td>
							<td>Expenditure</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="revenueExpenditure" items="${actionBean.reportManager.allRevenueExpenditure}">
							<c:set value="${revenueExpenditure.revenue}" var="revenue"></c:set>
							<c:set value="${revenueExpenditure.expenditure}" var="expenditure"></c:set>
							<tr class="${expenditure>revenue?'color-red':''}">
								<td><c:out value="${revenueExpenditure.startPoint.name}"></c:out></td>
								<td><c:out value="${revenueExpenditure.endPoint.name}"></c:out></td>
								<td><c:out value="${revenueExpenditure.priority}"></c:out></td>
								<td><c:out value="${revenue}"></c:out></td>
								<td><c:out value="${expenditure}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>