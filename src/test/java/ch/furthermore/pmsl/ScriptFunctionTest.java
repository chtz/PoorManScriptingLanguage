package ch.furthermore.pmsl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import org.junit.Test;

public class ScriptFunctionTest {
	@Test
	public void test() throws IOException, ScriptException, NoSuchMethodException {
		ScriptFunction f = new ScriptFunction("def foo(c,a,b) ret (a + b) * c end");
		
		assertEquals(3, f.getParameters().length);
		assertEquals("c", f.getParameters()[0]);
		assertEquals("a", f.getParameters()[1]);
		assertEquals("b", f.getParameters()[2]);
		
		assertEquals((3 + 4) * 2, ((Number) f.invoke(2, 3, 4)).intValue());
		
		Map<String,Object> params = new HashMap<>();
		params.put("a", 6);
		params.put("b", 4);
		params.put("c", 2);
		
		assertEquals((6 + 4) * 2, ((Number) f.call(params)).intValue());
	}
	
	@Test
	public void test2() throws IOException, ScriptException, NoSuchMethodException {
		assertEquals("halli hallo kartoffel", 
				new ScriptFunction("def foo(a,b) ret a + b + \" kartoffel\" end").invoke("halli ", "hallo"));
	}
	
	@Test
	public void test3() throws IOException, ScriptException, NoSuchMethodException {
		assertEquals("HALLO WELT", 
				new ScriptFunction("def foo(a,b) ret upper(a + b) end", new Helper())
				.invoke("hallo", " welt"));
	}
	
	public final static class Helper {
		@BuiltIn
		public String upper(String s) {
			return s.toUpperCase();
		}
	}
}
