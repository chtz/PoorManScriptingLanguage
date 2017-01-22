package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTVarAssignment extends AbstractASTNode implements ASTStatement { 
	private final String name;
	private final ASTExpression expression;

	public ASTVarAssignment(ScannerToken token, String name, ASTExpression expression) {
		super(token);
		this.name = name;
		this.expression = expression;
	}
	
	public void print(StringBuilder sb) {
		sb.append(name);
		
		sb.append('=');
		
		expression.print(sb);
	}

	public void generate(Context ctx, StringBuilder sb) {
		String nameKey = ctx.getKeys().get(name);
		
		sb.append(nameKey);
		
		sb.append('=');
		
		expression.generate(ctx, sb);
	}
}
