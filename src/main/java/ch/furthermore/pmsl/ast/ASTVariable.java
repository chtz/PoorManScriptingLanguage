package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.GeneratorException;
import ch.furthermore.pmsl.ScannerToken;

public class ASTVariable extends AbstractASTNode implements ASTFactor {
	private final String name;

	public ASTVariable(ScannerToken token, String name) {
		super(token);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void printTransform(StringBuilder sb, Transformer transformer) {
		transformer.transform(this).printTransformInt(sb, transformer);
	}
	
	@Override
	public void printTransformInt(StringBuilder sb, Transformer transformer) {
		sb.append(name);
	}

	public void generate(Context ctx, StringBuilder sb) {
		String v = ctx.getKeys().get(name);
		
		if (v == null) {
			throw new GeneratorException(getToken(), "Variable not declared: " + name);
		}
		
		sb.append(v);
	}
}
