package ch.furthermore.pmsl.ast;

import java.util.ArrayList;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTBExpression extends AbstractASTNode implements ASTFactor {
	private final ArrayList<ASTBTerm> terms = new ArrayList<ASTBTerm>();
	
	public ASTBExpression(ScannerToken t) {
		super(t);
	}
	
	public void add(ASTBTerm term) {
		terms.add(term);
	}

	public void print(StringBuilder sb) {
		for (int i = 0; i < terms.size(); i++) {
			if (i > 0) {
				sb.append(" or ");
			}
			terms.get(i).print(sb);
		}
	}

	public void generate(Context ctx, StringBuilder sb) {
		if (terms.size() > 1) {
			sb.append('(');
		}
		for (int i = 0; i < terms.size(); i++) {
			if (i > 0) {
				sb.append('|');
			}
			terms.get(i).generate(ctx, sb);
		}
		if (terms.size() > 1) {
			sb.append(')');
		}
	}
}
