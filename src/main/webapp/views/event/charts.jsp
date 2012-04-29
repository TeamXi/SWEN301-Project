<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes"	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
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
var events = <%@include file="../dashboard/eventListJSON.jsp"%>;
</script>
	