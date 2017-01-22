package ch.furthermore.pmsl.ast;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.GeneratorException;
import ch.furthermore.pmsl.ScannerToken;

public class ASTDefinition extends AbstractASTNode implements ASTStatement {
	private final String name;
	private final List<String> parameters = new LinkedList<String>();
	private final List<ASTStatement> statements = new LinkedList<ASTStatement>();
	private ASTExpression returnExpression;

	public ASTDefinition(ScannerToken token, String name) {
		super(token);
		this.name = name;
	}
	
	public ASTExpression getReturnExpression() {
		return returnExpression;
	}

	public void setReturnExpression(ASTExpression returnExpression) {
		this.returnExpression = returnExpression;
	}

	public String getName() {
		return name;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void add(String parameter) {
		parameters.add(parameter);
	}
	
	public void add(ASTStatement statement) { 
		statements.add(statement);
	}
	
	public void print(StringBuilder sb) {
		sb.append("def ");
		sb.append(name);
		sb.append('(');
		boolean first = true;
		for (String p : parameters) {
			if (first) {
				first = false;
			}
			else {
				sb.append(',');
			}
			sb.append(p);
		}
		sb.append(")\n");
		for (ASTStatement s : statements) {
			s.print(sb);
			sb.append('\n');
		}
		if (returnExpression != null) {
			sb.append("ret ");
			returnExpression.print(sb);
			sb.append('\n');
		}
		sb.append("end");
	}

	public void generate(Context ctx, StringBuilder sb) {
		if (ctx.getKeys().getNonDeep(name) != null) {
			throw new GeneratorException(getToken(), "Variable already declared: " + name);
		}
		String nameKey =  ctx.getKeys().key(name);
		
		ctx.getKeys().push();
				
		sb.append("function ");
		sb.append(nameKey);
		sb.append('(');
		boolean first = true;
		for (String p : parameters) {
			if (first) {
				first = false;
			}
			else {
				sb.append(',');
			}
			sb.append(ctx.getKeys().key(p));
		}
		sb.append(") {\n");
		for (ASTStatement s : statements) {
			s.generate(ctx, sb);
			sb.append(";\n");
		}
		if (returnExpression != null) {
			sb.append("return ");
			returnExpression.generate(ctx, sb);
			sb.append(";\n");
		}
		sb.append("}");
		
		ctx.getKeys().pop();
	}
}
