<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:array var="route" items="${actionBean.state.allRoute}">
	<json:object>
		<json:property name="startPoint" value="${route.startPoint.name}"/>
		<json:property name="endPoint" value="${route.endPoint.name}"/>
		<json:property name="carrier" value="${route.carrier.name}"/>
		<json:property name="type" value="${route.transportMeans}"/>
	</json:object>
</json:array>
