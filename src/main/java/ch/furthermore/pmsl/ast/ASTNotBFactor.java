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

	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		if (not) {
			sb.append("not ");
		}
		
		transformer.transform(relation).printTransformInt(sb, transformer);
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
