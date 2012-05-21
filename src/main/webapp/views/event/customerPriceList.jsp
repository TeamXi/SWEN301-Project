<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table table-bordered table-striped responsive-utilities">
	<thead>
		<tr>
			<th>From</th>
			<th>To</th>
			<th>Priority</th>
			<th>Price per gram</th>
			<th>Price per cm&sup3;</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<c:set value="${actionBean.domesticStandardCustomerPrice}" var="price"/>
			<td>New Zealand</td>
			<td>New Zealand</td>
			<td>Domestic Standard</td>
			<td>
				<fmt:formatNumber type="currency" value="${price.pricePerUnitWeight}"/>
			</td>
			<td>
				<fmt:formatNumber type="currency" value="${price.pricePerUnitVolume}"/>
				<span class="row-hover-controls"><a onclick="KPS.event.customerprice.updateDomesticCustomerPrice('${price.priority}')">update</a></span>
			</td>
		</tr>
		<tr>
			<c:set value="${actionBean.domesticAirCustomerPrice}" var="price"/>
			<td>New Zealand</td>
			<td>New Zealand</td>
			<td>Domestic Air</td>
			<td>
				<fmt:formatNumber type="currency" value="${price.pricePerUnitWeight}"/>
			</td>
			<td>
				<fmt:formatNumber type="currency" value="${price.pricePerUnitVolume}"/>
				<span class="row-hover-controls"><a onclick="KPS.event.customerprice.updateDomesticCustomerPrice('${price.priority}')">update</a></span>
			</td>
		</tr>
		<c:forEach var="price" items="${actionBean.state.allCustomerPrices}">
			<c:set value="${price.startLocation!=null?price.startLocation.name:'New Zealand'}" var="from"></c:set>
			<c:set value="${price.endLocation!=null?price.endLocation.name:'New Zealand'}" var="to"></c:set>
			<tr>
				<td>
					<c:choose>
						<c:when test="${price.startLocation!=null}">
							<span class="location-name-hover"><c:out value="${from}"/></span>
						</c:when>
						<c:otherwise>
							<c:out value="${from}"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${price.endLocation!=null}">
							<span class="location-name-hover"><c:out value="${to}"/></span>
						</c:when>
						<c:otherwise>
							<c:out value="${to}"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:message key="Priority.${price.priority}"/>
				</td>
				<td>
					<fmt:formatNumber type="currency" value="${price.pricePerUnitWeight}"/>
				</td>
				<td>
					<fmt:formatNumber type="currency" value="${price.pricePerUnitVolume}"/>
					<span class="row-hover-controls"><a onclick="KPS.event.customerprice.updateCustomerPrice(${price.id})">update</a> <a onclick="KPS.event.customerprice.deleteCustomerPrice('from ${from} to ${to}', ${price.id})">delete</a></span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>