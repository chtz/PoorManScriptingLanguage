package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.Printable;

public interface ASTNode extends Printable {
	public void generate(Context ctx, StringBuilder sb);
}
