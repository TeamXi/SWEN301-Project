<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

KPS.charts = KPS.charts || {};
	
KPS.graphs.donut = KPS.graphs.donut || {};
KPS.graphs.donut.revenue = KPS.graphs.donut.revenue || {};
KPS.graphs.donut.expenditure = KPS.graphs.donut.expenditure || {};

KPS.graphs.donut.revenue.inner = <json:array var="value" items="${actionBean.reportManager.revenueByDomesticInternational}">
	<json:object>
		<json:property name="y" value="${value.y}"/>
		<json:property name="value" value="${value.value}"/>
		<json:property name="name" value="${value.name}"/>
	</json:object>
</json:array>;
KPS.graphs.donut.revenue.outer = <json:array var="value" items="${actionBean.reportManager.revenueByRoute}">
	<json:object>
		<json:property name="y" value="${value.y}"/>
		<json:property name="value" value="${value.value}"/>
		<json:property name="name" value="${value.name}"/>
	</json:object>
</json:array>;

KPS.graphs.donut.expenditure.inner = <json:array var="value" items="${actionBean.reportManager.expenditureByDomesticInternational}">
	<json:object>
		<json:property name="y" value="${value.y}"/>
		<json:property name="value" value="${value.value}"/>
		<json:property name="name" value="${value.name}"/>
	</json:object>
</json:array>;
KPS.graphs.donut.expenditure.outer = <json:array var="value" items="${actionBean.reportManager.expenditureByRoute}">
	<json:object>
		<json:property name="y" value="${value.y}"/>
		<json:property name="value" value="${value.value}"/>
		<json:property name="name" value="${value.name}"/>
	</json:object>
</json:array>;


KPS.graphs.revenueexpenditure = <json:array name="revenue" var="revexp" items="${actionBean.reportManager.revenueExpenditureOverTime}">
	<json:object>
		<json:property name="revenue" value="${revexp.revenue}"/>
		<json:property name="expenditure" value="${revexp.expenditure}"/>
		<json:property name="timestamp" value="${revexp.date.time}" />
		<json:property name="eventId" value="${revexp.eventId}" />
	</json:object>
</json:array>;
