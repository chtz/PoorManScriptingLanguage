package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;

public abstract class AbstractASTNode implements ASTNode {
	private final ScannerToken t;
	
	public AbstractASTNode(ScannerToken t) {
		this.t = t;
	}
	
	public abstract void print(StringBuilder sb);
	public abstract void generate(Context ctx, StringBuilder sb);

	public ScannerToken getToken() {
		return t;
	}
}
