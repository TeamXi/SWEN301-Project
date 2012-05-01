<%
setup(request);
%>

<%! // Utilities
public String capitalize(String string) {
	if(string != null && string.length() > 0) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	return string;
}
%>

<%!
private HttpServletRequest request;
private String userRoleValue;

private void setup(HttpServletRequest req) {
	request = req;
	userRoleValue = (String)request.getSession().getAttribute("user-role");
}

public String getRoleText() {
	String s = capitalize(userRoleValue);;
	if(s != null && s.length() > 0) {
		return s;
	}
	return "Log in";
}

public boolean isLoggedIn() {
	return isClerk() || isManager();
}

public boolean isClerk() {
	return userRoleValue != null && userRoleValue.equals("clerk");
}

public boolean isManager() {
	return userRoleValue != null && userRoleValue.equals("manager");
}
%>