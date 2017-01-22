package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTNotBFactor extends AbstractASTNode {
	private final ASTRelation relation;
	private final boolean not;

	public ASTNotBFactor(ScannerToken token, ASTRelation relation, boolean not) {
		super(token);
		this.relation = relation;
		this.not = not;
	}

	public void print(StringBuilder sb) {
		if (not) {
			sb.append("not ");
		}
		
		relation.print(sb);
	}

	public void generate(Context ctx, StringBuilder sb) {
		if (not) {
			sb.append("!(");
		}
		relation.generate(ctx, sb);
		if (not) {
			sb.append(")");
		}
	}
}
