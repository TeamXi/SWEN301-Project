<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<json:array var="route" items="${actionBean.reportManager.allRevenueExpenditure}">
	<c:if test="${route.expenditure>route.revenue}">
		<json:object>
		<json:property name="startPoint" value="${route.startPoint.name}"/>
		<json:property name="endPoint" value="${route.endPoint.name}"/>
		<json:property name="revenue" value="${route.revenue}"/>
		<json:property name="expenditure" value="${route.expenditure}"/>
		<json:property name="priority" value="${route.priority}"/>
		<json:property name="deliveryTime" value="${revenueExpenditure.averageDeliveryTime=='NaN'?'No data':revenueExpenditure.averageDeliveryTime}${revenueExpenditure.averageDeliveryTime=='NaN'?'':' hours'}"/>
		</json:object>
	</c:if>
</json:array>