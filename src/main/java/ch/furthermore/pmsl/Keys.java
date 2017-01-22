package ch.furthermore.pmsl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Keys {
	private int i = 0;
	private final LinkedList<LinkedHashMap<String,String>> nameKeys = new LinkedList<LinkedHashMap<String, String>>();
	private LinkedHashMap<String, String> lastPop;
	
	public Keys() {
		push();
	}
	
	public void push() {
		nameKeys.addFirst(new LinkedHashMap<String, String>());
	}
	
	public LinkedHashMap<String, String> pop() {
		return lastPop = nameKeys.removeFirst();
	}
	
	public LinkedHashMap<String, String> getLastPop() {
		return lastPop;
	}

	public String key(String name) {
		return put(name, "i" + (i++));
	}

	public String getNonDeep(String name) { 
		return nameKeys.getFirst().get(name);
	}
	
	public String get(String name) {
		for (Map<String, String> m : nameKeys) {
			if (m.containsKey(name)) {
				return m.get(name);
			}
		}
		return null;
	}
	
	public String put(String name, String value) {
		nameKeys.getFirst().put(name, value);
		return value;
	}
}
