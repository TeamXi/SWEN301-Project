<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/layouts/KPSmart.jsp">
	<stripes:layout-component name="title">Home</stripes:layout-component>
	<stripes:layout-component name="content">
		<div class="row-fluid">
			<div id="main" class="span10">
				<div class="hero-unit">
					<h1>Welcome</h1>
					<p>Welcome to KPSmart</p>
				</div>
			</div>
			
			<div class="span2">
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li class="nav-header">Useful links</li>
						<li><a href="#">LINK</a>
					</ul>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-render>