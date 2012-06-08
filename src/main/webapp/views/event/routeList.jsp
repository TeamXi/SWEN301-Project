<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-bordered table-striped responsive-utilities">
	<thead>
		<tr>
			<th>From</th>
			<th>To</th>
			<th>Carrier</th>
			<th>Transport method</th>
			<th>Category</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="route" items="${actionBean.state.allRoute}">
			<tr>
				<td><span class="location-name-hover"><c:out value="${route.startPoint.name}" /></span></td>
				<td><span class="location-name-hover"><c:out value="${route.endPoint.name}" /></span></td>
				<td><c:out value="${route.carrier.name}" /></td>
				<td><c:out value="${route.transportMeans}" /></td>
				<td>
					<c:out value="${route.international?'International':'Domestic'}" />
					<span class="row-hover-controls">
						<a onclick="KPS.event.route.updateRoute('${route.id}')">update</a>
						<a onclick="KPS.event.route.deleteRoute('from ${route.startPoint.name} to ${route.endPoint.name}', ${route.id})">delete</a> <!-- TODO: escaping? -->
					</span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>