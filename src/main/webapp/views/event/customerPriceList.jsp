<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-bordered table-striped responsive-utilities">
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
		<c:forEach var="price" items="${actionBean.state.allCustomerPrices}">
			<c:set value="${price.startLocation!=null?price.startLocation.name:'New Zealand'}" var="from"></c:set>
			<c:set value="${price.endLocation!=null?price.endLocation.name:'New Zealand'}" var="to"></c:set>
			<tr>
				<td>
					<c:out value="${from}"/>
				</td>
				<td>
					<c:out value="${to}"/>
				</td>
				<td>
					<c:out value="${price.priority}"/>
				</td>
				<td>
					<c:out value="${price.pricePerUnitWeight}"/>
				</td>
				<td>
					<c:out value="${price.pricePerUnitVolume}"/>
					<span class="row-hover-controls"><a onclick="KPS.event.customerprice.updateCustomerPrice(${price.id})">update</a> <a onclick="KPS.event.customerprice.deleteCustomerPrice('from ${from} to ${to}', ${price.id})">delete</a></span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>