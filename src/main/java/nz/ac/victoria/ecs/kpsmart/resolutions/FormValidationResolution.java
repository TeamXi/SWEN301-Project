package nz.ac.victoria.ecs.kpsmart.resolutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.stripes.ajax.JavaScriptResolution;

public class FormValidationResolution extends JavaScriptResolution {

	public FormValidationResolution(boolean status, String[] fieldNames, String[] fieldMessages) {
		super(generateObjects(status, fieldNames, fieldMessages));
	}
	
	public FormValidationResolution(boolean status, Map<String, String> map) {
		super(generateObjects(status, map.keySet().toArray(new String[]{}), map.values().toArray(new String[]{})));
	}
		
	private static Object generateObjects(boolean status, String[] fieldNames, String[] fieldMessages) {
		Map<String, Object> rootObject = new HashMap<String, Object>();
		rootObject.put("status", status);
		List<Map<String, String>> fields = new ArrayList<Map<String, String>>();
		if(fieldNames != null && fieldMessages != null){
			for(int n=0;n<fieldNames.length;n++) {
				Map<String, String> fieldInfo = new HashMap<String, String>();
				fieldInfo.put("name", fieldNames[n]);
				fieldInfo.put("message", fieldMessages[n]);
				fields.add(fieldInfo);
			}
			rootObject.put("validation", fields);
		}
		
		return rootObject;
	}

}
