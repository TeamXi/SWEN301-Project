<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:array var="event" items="${actionBean.log.allEvents}">
	<json:object>
		<json:property name="id" value="${event.id}"></json:property>
		<json:property name="timestamp" value="${event.timestamp.time}"></json:property>
	</json:object>
</json:array>