package ch.furthermore.pmsl;

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptFunction {
	private final String name;
	private final String[] parameters;
	private final Invocable compiled;

	public ScriptFunction(String script, Object... builtIns) throws IOException, ScriptException {
		BuiltInFunctions builtInFunctions = builtIns == null
				? null
				: generateBuiltIns(builtIns);
		Context ctx = new Context(new Keys(), builtInFunctions);
		
		Parser p = new Parser(new Scanner(new StringReader(script)));
		
		StringBuilder sb = new StringBuilder();
		p.definition().generate(ctx, sb);
		
		parameters = ctx.getKeys().getLastPop().keySet().toArray(new String[0]);
		name = ctx.getKeys().pop().values().toArray(new String[1])[0];
		
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        
        if (builtInFunctions != null) {
        	engine.put(BuiltInFunctions.EXECUTION_CONTEXT_NAME, builtInFunctions);
        }
        
        engine.eval(sb.toString());
        
        compiled = (Invocable) engine;
	}
	
	public Object call(Map<String,Object> params) throws NoSuchMethodException, ScriptException {
		Object[] p = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			p[i] = params.get(parameters[i]);
		}
		
		return invoke(p);
	}
	
	public Object invoke(Object... params) throws NoSuchMethodException, ScriptException {
        return compiled.invokeFunction(name, params);
	}

	public String[] getParameters() {
		return parameters;
	}
	
	private BuiltInFunctions generateBuiltIns(Object... os) {
		BuiltInFunctions bf = new BuiltInFunctions();
		for (Object o : os) {
			for (Method m : o.getClass().getDeclaredMethods()) {
				for (Annotation a : m.getAnnotations()) {
					if (BuiltIn.class.equals(a.annotationType())) {
						bf.put(m.getName(), o);
					}
				}
			}
		}
		return bf;
	}
}
