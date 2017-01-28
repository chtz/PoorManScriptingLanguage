package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public class ASTFactorBoolLiteral extends AbstractASTNode implements ASTFactor {
	private final String value;

	public ASTFactorBoolLiteral(ScannerToken token, String value) {
		super(token);
		this.value = value;
	}

	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		sb.append(value);
	}

	public void generate(Context ctx, StringBuilder sb) {
		sb.append(value);
	}
}
