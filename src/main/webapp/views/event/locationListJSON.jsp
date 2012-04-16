<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:array var="location" items="${actionBean.stateManipulator.allLocations}">
	<json:object>
		<json:property name="name" value="${location.name}"/>
		<json:property name="international" value="${location.international}"/>
		<json:property name="longitude" value="${location.longitude}"/>
		<json:property name="latitude" value="${location.latitude}"/>
	</json:object>
</json:array>