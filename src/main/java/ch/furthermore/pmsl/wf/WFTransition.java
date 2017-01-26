package ch.furthermore.pmsl.wf;

import ch.furthermore.pmsl.Printable;
import ch.furthermore.pmsl.ast.ASTBExpression;

public class WFTransition implements Printable {
	private final String name;
	private ASTBExpression condition;

	public WFTransition(String name) {
		this.name = name;
	}

	public void setCondition(ASTBExpression condition) {
		this.condition = condition;
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append("transition to ");
		sb.append(name);
		if (condition != null) {
			sb.append(" if ");
			condition.print(sb);
		}
	}
}
