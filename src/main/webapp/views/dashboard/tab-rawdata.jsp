<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a href="#" id="show-hidden-data-link">Show</a>

<table class="table table-bordered table-striped responsive-utilities" id="three-value-table">
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
		<c:forEach var="revenueExpenditure" items="${actionBean.reportManager.allRevenueExpenditure}">
			<c:set value="${revenueExpenditure.revenue}" var="revenue"></c:set>
			<c:set value="${revenueExpenditure.expenditure}" var="expenditure"></c:set>
			<c:set value="${revenueExpenditure.averageDeliveryTime}" var="averageDeliveryTime"></c:set>
			<tr class="${expenditure>revenue?'color-red':''} ${averageDeliveryTime=='NaN'?'none':''}">
				<td><span class="location-name-hover"><c:out value="${revenueExpenditure.startPoint.name}"></c:out></span></td>
				<td><span class="location-name-hover"><c:out value="${revenueExpenditure.endPoint.name}"></c:out></span></td>
				<td><fmt:message key="Priority.${revenueExpenditure.priority}"/></td>
				<td><fmt:formatNumber type="currency" value="${revenue}" /></td>
				<td><fmt:formatNumber type="currency" value="${expenditure}" /></td>
				<td class="data-chooser">
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

<script type="text/javascript">
	$('#show-hidden-data-link').click(function () {
		$('#three-value-table tr').each(function () {			
			if ($('td.data-chooser', $(this)).html()!= null && $('td.data-chooser', $(this)).html().indexOf("No data") != -1)
				$(this).show();
		});
		
		$('#two-value-table tr').each(function () {
			if ($('td.data-chooser', $(this)).html()!= null && $('td.data-chooser', $(this)).html().indexOf("0") != -1)
				$(this).show();
		});
		
		$(this).hide();
	});
</script>

<table class="table table-bordered table-striped responsive-utilities" id="two-value-table">
	<thead>
		<tr>
			<th>Source</th>
			<th>Destination</th>
			<th>Number of items</th>
			<th>Total weight (grams)</th>
			<th>Total volume (cm&sup3;)</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="mailCount" items="${actionBean.reportManager.amountsOfMailForAllRoutes}">
			<tr class="${mailCount.items==0?'none':''}">
				<td><span class="location-name-hover"><c:out value="${mailCount.startPoint.name}"></c:out></span></td>
				<td><span class="location-name-hover"><c:out value="${mailCount.endPoint.name}"></c:out></span></td>
				<td class="data-chooser"><c:out value="${mailCount.items}"></c:out></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="1" value="${mailCount.totalWeight}"></fmt:formatNumber></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="1" value="${mailCount.totalVolume}"></fmt:formatNumber></td>
			</tr>
		</c:forEach>
	</tbody>
</table>