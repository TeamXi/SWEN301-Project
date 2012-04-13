package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.HashMap;
import java.util.Map;

public class FormActionBean extends AbstractActionBean {
	private String divId;
	private String formId;
	private String submitCallback;
	
	private Map<String, Boolean> disabledFields = new HashMap<String, Boolean>();
	
	public Map<String, Boolean> getDisabledFormFields() {
		return disabledFields;
	}
	
	protected void disableFormField(String[] fields) {
		for(String name : fields) {
			disabledFields.put(name, true);
		}
	}
	
	/**
	 * @return the divId
	 */
	public String getDivId() {
		return divId;
	}
	/**
	 * @param divId the divId to set
	 */
	public void setDivId(String divId) {
		this.divId = divId;
	}
	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}
	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	/**
	 * @return the submitCallback
	 */
	public String getSubmitCallback() {
		return submitCallback;
	}
	/**
	 * @param submitCallback the submitCallback to set
	 */
	public void setSubmitCallback(String submitCallback) {
		this.submitCallback = submitCallback;
	}
}
