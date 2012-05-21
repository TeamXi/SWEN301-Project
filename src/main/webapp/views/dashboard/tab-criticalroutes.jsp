<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table id="critical-routes-table" class="table table-bordered table-striped responsive-utilities">
	<thead>
		<tr>
			<th>Source</th>
			<th>Destination</th>
			<th>Priority</th>
			<th>Revenue</th>
			<th>Expenditure</th>
			<th>Average delivery time</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${actionBean.hasCriticalRoutes()}">
				<c:forEach var="revenueExpenditure" items="${actionBean.reportManager.allRevenueExpenditure}">
					<c:if test="${revenueExpenditure.expenditure>revenueExpenditure.revenue}">
						<tr class="color-red critical-route-hover" data-from="${revenueExpenditure.startPoint.name}" data-to="${revenueExpenditure.endPoint.name}">
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