<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="span12">
	<h1>Carriers</h1>
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
						<span class="row-hover-controls"><a onclick="updateCarrier('${carrier.id}')">update</a> <a onclick="alert('delete')">delete</a></span>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>