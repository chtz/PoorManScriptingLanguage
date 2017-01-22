package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.GeneratorException;
import ch.furthermore.pmsl.ScannerToken;

public class ASTVarDeclaration extends AbstractASTNode implements ASTStatement { 
	private final String name;
	private final ASTExpression expression;

	public ASTVarDeclaration(ScannerToken token, String name, ASTExpression expression) {
		super(token);
		this.name = name;
		this.expression = expression;
	}
	
	public void print(StringBuilder sb) {
		print(sb, true);
	}
	
	public void print(StringBuilder sb, boolean printInitialKeywod) {
		if (printInitialKeywod) {
			sb.append("var ");
		}
		
		sb.append(name);
		
		sb.append('=');
		
		expression.print(sb);
	}

	public void generate(Context ctx, StringBuilder sb) {
		generate(ctx, sb, true);
	}
	
	public void generate(Context ctx, StringBuilder sb, boolean genVarKeyword) {
		if (ctx.getKeys().getNonDeep(name) != null) {
			throw new GeneratorException(getToken(), "Variable already declared: " + name);
		}
		String nameKey =  ctx.getKeys().key(name);
		
		if (genVarKeyword) {
			sb.append("var ");
		}
		
		sb.append(nameKey);
		
		sb.append('=');
		
		expression.generate(ctx, sb);
	}
}
