package ch.furthermore.pmsl.ast;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTIf extends AbstractASTNode implements ASTStatement {
	private final ASTBExpression condition;
	private final List<ASTStatement> statements = new LinkedList<ASTStatement>();

	public ASTIf(ScannerToken token, ASTBExpression condition) {
		super(token);
		this.condition = condition;
	}

	public void add(ASTStatement statement) { 
		statements.add(statement);
	}
	
	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		sb.append("if ");
		transformer.transform(condition).printTransformInt(sb, transformer);
		sb.append(" then\n");
		for (ASTStatement s : statements) {
			transformer.transform(s).printTransformInt(sb, transformer);
			sb.append('\n');
		}
		sb.append("end");
	}
	
	public void generate(Context ctx, StringBuilder sb) {
		sb.append("if (");
		condition.generate(ctx, sb);
		sb.append(") {\n");
		for (ASTStatement s : statements) {
			s.generate(ctx, sb);
			sb.append(";\n");
		}
		sb.append("}");
	}
}
