package ch.furthermore.pmsl.ast;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTFor extends AbstractASTNode implements ASTStatement {
	private final ASTVarDeclaration declaration;
	private final ASTRelation relation;
	private final ASTVarAssignment assignment;
	private final List<ASTStatement> statements = new LinkedList<>();

	public ASTFor(ScannerToken token, ASTVarDeclaration declaration, ASTRelation relation, ASTVarAssignment assignment) {
		super(token);
		this.declaration = declaration;
		this.relation = relation;
		this.assignment = assignment;
	}

	public void add(ASTStatement statement) {
		statements.add(statement);
	}
	
	public void print(StringBuilder sb) {
		sb.append("for ");
		declaration.print(sb, false);
		sb.append(';');
		relation.print(sb);
		sb.append(';');
		assignment.print(sb);
		sb.append(" do\n");
		for (ASTStatement s : statements) {
			s.print(sb);
			sb.append('\n');
		}
		sb.append("end");
	}
	
	public void generate(Context ctx, StringBuilder sb) {
		sb.append("for (");
		declaration.generate(ctx, sb, false);
		sb.append(';');
		relation.generate(ctx, sb);
		sb.append(';');
		assignment.generate(ctx, sb);
		sb.append("){\n");
		for (ASTStatement s : statements) {
			s.generate(ctx, sb);
			sb.append(";\n");
		}
		sb.append("}");
	}
}
