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
	
	public String getName() {
		return name;
	}

	public ASTExpression getExpression() {
		return expression;
	}
	
	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		sb.append(name);
		
		sb.append('=');
		
		transformer.transform(expression).printTransformInt(sb, transformer);
	}

	public void generate(Context ctx, StringBuilder sb) {
		String nameKey = ctx.getKeys().get(name);
		
		sb.append(nameKey);
		
		sb.append('=');
		
		expression.generate(ctx, sb);
	}
}
