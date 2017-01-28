package ch.furthermore.pmsl.wf;

import java.io.IOException;
import java.io.StringReader;

import javax.script.ScriptException;

import ch.furthermore.pmsl.Parser;
import ch.furthermore.pmsl.Printable;
import ch.furthermore.pmsl.Scanner;
import ch.furthermore.pmsl.ScriptFunction;
import ch.furthermore.pmsl.ast.ASTBExpression;
import ch.furthermore.pmsl.ast.ASTDefinition;
import ch.furthermore.pmsl.ast.ASTIf;

public class WFTransition implements Printable {
	private final String name;
	private ASTBExpression condition;

	public WFTransition(String name) {
		this.name = name;
	}

	public void setCondition(ASTBExpression condition) {
		this.condition = condition;
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append("transition to ");
		sb.append(name);
		if (condition != null) {
			sb.append(" if ");
			condition.print(sb);
		}
	}

	public boolean evaluateCondition(Token t) throws IOException, ScriptException, NoSuchMethodException { //FIXME dirty hack
		ASTDefinition def = new ASTDefinition(null, "action");
		def.add(parser("var returnCode = 0").statement());
		
		ASTIf ifSt = new ASTIf(null, condition);
		ifSt.add(parser("returnCode = 1").statement());
		def.add(ifSt);
		
		def.setReturnExpression(parser("returnCode").expression());
		
		StringBuilder script = new StringBuilder();
		def.printTransform(script, new TokenVarAccessTransformer());
		
		ScriptFunction f = new ScriptFunction(script.toString(), t);
		Object o = f.invoke();
		return ((Number) o).intValue() == 1;
	}
	
	private Parser parser(String s) throws IOException {
		return new Parser(new Scanner(new StringReader(s)));
	}
}
