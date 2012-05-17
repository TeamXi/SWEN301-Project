<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
				<td><fmt:formatNumber type="number" maxFractionDigits="1" value="${mailCount.totalWeight}"></fmt:formatNumber></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="1" value="${mailCount.totalVolume}"></fmt:formatNumber></td>
			</tr>
		</c:forEach>
	</tbody>
</table>