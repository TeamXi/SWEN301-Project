package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;

@UrlBinding("/login")
public class LoginBean extends AbstractActionBean{
	
	private String role;
	
	
	@DefaultHandler
	public Resolution defaultEvent() {
		getContext().getRequest().getSession().setAttribute("user-role", role);
		return new JavaScriptResolution(role);
	}


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
