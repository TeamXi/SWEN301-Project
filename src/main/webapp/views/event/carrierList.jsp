<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<td>Name</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="carrier" items="${actionBean.stateManipulator.allCarriers}">
			<tr>
				<td>
					<c:out value="${carrier.name}"/>
					<span class="row-hover-controls"><a onclick="KPS.event.carrier.updateCarrier('${carrier.id}')">update</a> <a onclick="KPS.event.carrier.deleteCarrier('${carrier.name}', ${carrier.id})">delete</a></span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>