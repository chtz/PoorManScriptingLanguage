package ch.furthermore.pmsl.ast;

import java.util.ArrayList;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTBTerm extends AbstractASTNode {
	private final ArrayList<ASTNotBFactor> factors = new ArrayList<ASTNotBFactor>();
	
	public ASTBTerm(ScannerToken t) {
		super(t);
	}
	
	public void add(ASTNotBFactor factor) {
		factors.add(factor);
	}

	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		for (int i = 0; i < factors.size(); i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			transformer.transform(factors.get(i)).printTransformInt(sb, transformer);
		}
	}

	public void generate(Context ctx, StringBuilder sb) {
		if (factors.size() > 1) {
			sb.append('(');
		}
		for (int i = 0; i < factors.size(); i++) {
			if (i > 0) {
				sb.append('&');
			}
			factors.get(i).generate(ctx, sb);
		}
		if (factors.size() > 1) {
			sb.append(')');
		}
	}
}
