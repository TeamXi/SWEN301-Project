package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.HashMap;
import java.util.Map;

public class FormActionBean extends AbstractActionBean {
	private Map<String, Boolean> disabledFields = new HashMap<String, Boolean>();
	
	public Map<String, Boolean> getDisabledFormFields() {
		return disabledFields;
	}
	
	protected void disableFormField(String[] fields) {
		for(String name : fields) {
			disabledFields.put(name, true);
		}
	}
}
