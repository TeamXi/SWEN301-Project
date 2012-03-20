<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">
	<stripes:layout-component name="title">Enter Mail Delivery Event</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span12">
			<h1>Create a mail delivery event</h1>
			
			<stripes:form action="/event/mail">
			
				<stripes:label for="source">Source</stripes:label>
				<stripes:text name="source" />
				
				<stripes:label for="destination">Destination</stripes:label>
				<stripes:text name="destination" />
				
				<stripes:label for="priority">Priority</stripes:label>
				<stripes:text name="priority" />
				
				<stripes:label for="weight">Weight</stripes:label>
				<stripes:text name="weight" />
				
				<stripes:label for="volume">Volume</stripes:label>
				<stripes:text name="volume"/>
				
				<stripes:submit name="submit" value="Add"></stripes:submit>
				
			</stripes:form>
		</div>
		
		
	</stripes:layout-component>
</stripes:layout-render>