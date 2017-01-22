package ch.furthermore.pmsl.ast;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.BuiltInFunctions;
import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTCall extends AbstractASTNode implements ASTStatement, ASTFactor {
	private final String name;
	private final List<ASTExpression> parameters = new LinkedList<ASTExpression>();

	public ASTCall(ScannerToken token, String name) {
		super(token);
		this.name = name;
	}

	public void add(ASTExpression parameter) {
		parameters.add(parameter);
	}
	
	public void print(StringBuilder sb) {
		sb.append(name);
		sb.append('(');
		boolean first = true;
		for (ASTExpression p : parameters) {
			if (first) {
				first = false;
			}
			else {
				sb.append(',');
			}
			p.print(sb);
		}
		sb.append(')');
	}

	
	
	public void generate(Context ctx, StringBuilder sb) {
		String jsName;
		
		if (ctx.getBuiltInFunctions() != null && ctx.getBuiltInFunctions().get(name) != null) { 
			jsName = BuiltInFunctions.EXECUTION_CONTEXT_NAME + ".get('" + name + "')." + name;
		}
		else {
			jsName = ctx.getKeys().get(name);
		}
		
		sb.append(jsName);
		sb.append('(');
		boolean first = true;
		for (ASTExpression p : parameters) {
			if (first) {
				first = false;
			}
			else {
				sb.append(',');
			}
			p.generate(ctx, sb);
		}
		sb.append(")");
	}
}
