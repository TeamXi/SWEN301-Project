<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:array var="carrier" items="${actionBean.state.allCarriers}">
	<json:property value="${carrier.name}"/>
</json:array>