package ch.furthermore.pmsl.wf;

import java.util.HashMap;
import java.util.Map;

import ch.furthermore.pmsl.BuiltIn;

public class Token {
	private final Map<String,Object> vars = new HashMap<>();
	
	@BuiltIn
	public Object getTokenVar(String name) {
		return vars.get(name);
	}
	
	@BuiltIn
	public void setTokenVar(String name, Object o) {
		vars.put(name, o);
	}
}
