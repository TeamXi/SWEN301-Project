<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<td>From</td>
			<td>To</td>
			<td>Priority</td>
			<td>Price per gram</td>
			<td>Price per cm&sup3;</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="price" items="${actionBean.stateManipulator.allCustomerPrices}">
			<tr>
				<td>
					<c:out value="${price.startLocation?price.startLocation.name:'New Zealand'}"/>
				</td>
				<td>
					<c:out value="${price.endLocation?price.endLocation.name:'New Zealand'}"/>
				</td>
				<td>
					<c:out value="${price.priority}"/>
				</td>
				<td>
					<c:out value="${price.pricePerUnitWeight}"/>
				</td>
				<td>
					<c:out value="${price.pricePerUnitVolume}"/>
					<span class="row-hover-controls"><a onclick="KPS.event.carrier.updateCarrier('${carrier.id}')">update</a> <a onclick="KPS.event.carrier.deleteCarrier('${carrier.name}', ${carrier.id})">delete</a></span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>