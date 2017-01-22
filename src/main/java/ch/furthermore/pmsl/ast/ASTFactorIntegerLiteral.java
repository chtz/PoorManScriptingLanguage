package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTFactorIntegerLiteral extends AbstractASTNode implements ASTFactor {
	private final String value;

	public ASTFactorIntegerLiteral(ScannerToken token, String value) {
		super(token);
		this.value = value;
	}

	public void print(StringBuilder sb) {
		sb.append(value);
	}

	public void generate(Context ctx, StringBuilder sb) {
		sb.append(value);
	}
}