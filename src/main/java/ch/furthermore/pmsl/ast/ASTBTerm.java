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

	public void print(StringBuilder sb) {
		for (int i = 0; i < factors.size(); i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			factors.get(i).print(sb);
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
