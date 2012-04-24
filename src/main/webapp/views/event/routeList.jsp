<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-bordered table-striped responsive-utilities">
	<thead>
		<tr>
			<td>From</td>
			<td>To</td>
			<td>Carrier</td>
			<td>Transport method</td>
			<td>Category</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="route" items="${actionBean.state.allRoute}">
			<tr>
				<td><c:out value="${route.startPoint.name}" /></td>
				<td><c:out value="${route.endPoint.name}" /></td>
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