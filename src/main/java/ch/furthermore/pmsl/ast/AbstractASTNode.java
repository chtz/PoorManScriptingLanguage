package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.ScannerToken;

public abstract class AbstractASTNode implements ASTNode {
	private final ScannerToken t;
	
	public AbstractASTNode(ScannerToken t) {
		this.t = t;
	}

	@Override
	public void print(StringBuilder sb) {
		printTransform(sb, new DefaultTransformer());
	}

	public ScannerToken getToken() {
		return t;
	}
}
