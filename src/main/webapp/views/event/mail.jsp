<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">
	<stripes:layout-component name="title">Enter Mail Delivery Event</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="span10">
			<h1>Create a mail delivery event</h1>
			
			<stripes:form action="mail">
				<stripes:label for="day">Day</stripes:label>
				<stripes:text name="day" />
				
				<stripes:submit name="submit" value="Add"></stripes:submit>
			</stripes:form>
		</div>
		
		<div class="span2">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li class="nav-header">Useful Links</li>
					<li><a href="#">LINK</a></li>
				</ul>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>