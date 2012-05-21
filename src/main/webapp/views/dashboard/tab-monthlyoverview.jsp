<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table id="monthly-overview-table" class="table table-bordered table-striped responsive-utilities">
	<thead>
		<tr>
			<td>Month</td>
			<td>Revenue</td>
			<td>Expenditure</td>
			<td>Event count</td>
			<td>Mail weight (grams)</td>
			<td>Mail volume (cm&sup3;)</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="month" items="${actionBean.reportManager.monthlySummary}">
			<tr ${month.revenue < month.expenditure?'class="color-red"':''}>
				<td><c:out value="${month.name}"/></td>
				<td><fmt:formatNumber type="currency" value="${month.revenue}" /></td>
				<td><fmt:formatNumber type="currency" value="${month.expenditure}" /></td>
				<td><c:out value="${month.eventCount}"/></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="1" value="${month.weight}"/></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="1" value="${month.volume}"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>