<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

KPS.graphs.finances = {revenue:{international:[],domestic:[]},expenditure:{international:[],domestic:[]}};
	<c:forEach var="revenueExpenditure" items="${actionBean.reportManager.allRevenueExpenditure}">
		KPS.graphs.finances.revenue.<c:out value="${revenueExpenditure.endPoint.international? 'international': 'domestic'}"/>.push(
				{startPoint:'<c:out value="${revenueExpenditure.startPoint.name}"/>',
				endPoint:'<c:out value="${revenueExpenditure.endPoint.name}"/>',
				priority:'<c:out value="${revenueExpenditure.priority}"/>',
				amount:'<c:out value="${revenueExpenditure.revenue}"/>'}
		);
		KPS.graphs.finances.expenditure.<c:out value="${revenueExpenditure.endPoint.international? 'international': 'domestic'}"/>.push(
				{startPoint:'<c:out value="${revenueExpenditure.startPoint.name}"/>',
				endPoint:'<c:out value="${revenueExpenditure.endPoint.name}"/>',
				priority:'<c:out value="${revenueExpenditure.priority}"/>',
				amount:'<c:out value="${revenueExpenditure.expenditure}"/>'}
		);
	</c:forEach>


KPS.graphs.revenueexpenditure = <json:array name="revenue" var="revexp" items="${actionBean.reportManager.revenueExpenditureOverTime}">
	<json:object>
		<json:property name="revenue" value="${revexp.revenue}"></json:property>
		<json:property name="expenditure" value="${revexp.expenditure}"></json:property>
		<json:property name="timestamp" value="${revexp.date.time}"></json:property>
		<json:property name="eventId" value="${revexp.eventId}"></json:property>
	</json:object>
</json:array>
