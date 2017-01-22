package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;

public interface ASTNode {
	public void print(StringBuilder sb);
	public void generate(Context ctx, StringBuilder sb);
}
