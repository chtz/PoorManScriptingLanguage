package ch.furthermore.pmsl;

import java.util.HashMap;
import java.util.Map;

public class BuiltInFunctions { 
	public final static String EXECUTION_CONTEXT_NAME = "builtins";
	
	private final Map<String,Object> builtInFunctions = new HashMap<>();
	
	public Object get(String key) {
		return builtInFunctions.get(key);
	}
	
	public void put(String key, Object value) {
		builtInFunctions.put(key, value);
	}
}
