package ch.furthermore.pmsl.wf;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.script.ScriptException;

import ch.furthermore.pmsl.Printable;
import ch.furthermore.pmsl.ScriptFunction;
import ch.furthermore.pmsl.ast.ASTDefinition;
import ch.furthermore.pmsl.ast.ASTStatement;

public class WFAction implements Printable {
	final boolean enterAction;
	final List<ASTStatement> statements = new LinkedList<>();

	public WFAction(boolean enterAction) {
		this.enterAction = enterAction;
	}

	public void add(ASTStatement s) {
		statements.add(s);
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append(enterAction ? "enter" : "leave");
		sb.append('\n');
		for (ASTStatement s : statements) {
			s.print(sb);
			sb.append('\n');
		}
		sb.append("end");
	}

	public void execute(Token t) throws IOException, ScriptException, NoSuchMethodException {
		ASTDefinition def = new ASTDefinition(null, "action");
		for (ASTStatement s : statements) {
			def.add(s);
		}
		
		StringBuilder script = new StringBuilder();
		def.printTransform(script, new TokenVarAccessTransformer());
		
		ScriptFunction f = new ScriptFunction(script.toString(), t);
		
		f.invoke();
	}
}
