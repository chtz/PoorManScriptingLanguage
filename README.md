# PoorManScriptingLanguage

[![Build Status](https://travis-ci.org/chtz/PoorManScriptingLanguage.svg?branch=master)](https://travis-ci.org/chtz/PoorManScriptingLanguage)

```
@Test
public void test3() throws IOException, ScriptException, NoSuchMethodException {
	assertEquals("HALLO WELT", 
			new ch.furthermore.pmsl.ScriptFunction("def foo(a,b) ret upper(a + b) end", new Helper())
			.invoke("hallo", " welt"));
}
	
public final static class Helper {
	@ch.furthermore.pmsl.BuiltIn
	public String upper(String s) {
		return s.toUpperCase();
	}
}
```
