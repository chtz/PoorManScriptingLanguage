package ch.furthermore.pmsl;

public class Context {
	private final Keys keys;
	private final BuiltInFunctions builtInFunctions;
	
	public Context() {
		keys = new Keys();
		builtInFunctions = null;
	}
	
	public Context(Keys keys, BuiltInFunctions builtInFunctions) {
		this.keys = keys;
		this.builtInFunctions = builtInFunctions;
	}

	public Keys getKeys() {
		return keys;
	}

	public BuiltInFunctions getBuiltInFunctions() {
		return builtInFunctions;
	}
}
