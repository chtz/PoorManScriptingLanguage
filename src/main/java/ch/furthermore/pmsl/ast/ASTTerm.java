package ch.furthermore.pmsl.ast;

import java.util.ArrayList;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;
import ch.furthermore.pmsl.ScannerTokenType;

public class ASTTerm extends AbstractASTNode {
	private final ArrayList<ASTFactor> factors = new ArrayList<ASTFactor>();
	private final ArrayList<ScannerTokenType> combiners = new ArrayList<ScannerTokenType>();
	
	public ASTTerm(ScannerToken t) {
		super(t);
	}
	
	public void add(ASTFactor factor) {
		factors.add(factor);
	}

	public void add(ScannerTokenType asterixDivision) {
		combiners.add(asterixDivision);
	}
	
	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		for (int i = 0; i < factors.size(); i++) {
			if (i > 0) {
				switch (combiners.get(i - 1)) {
				case ASTERIX:
					sb.append('*');
					break;
				case DIVISION:
					sb.append('/');
					break;
				default: throw new IllegalStateException();
				}
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
				switch (combiners.get(i - 1)) {
				case ASTERIX:
					sb.append('*');
					break;
				case DIVISION:
					sb.append('/');
					break;
				default: throw new IllegalStateException();
				}
			}
			factors.get(i).generate(ctx, sb);
		}
		if (factors.size() > 1) {
			sb.append(')');
		}
	}
}
