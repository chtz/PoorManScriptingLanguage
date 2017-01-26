package ch.furthermore.pmsl.wf;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.Printable;
import ch.furthermore.pmsl.ast.ASTStatement;

public class WFAction implements Printable {
	private final boolean enterAction;
	private final List<ASTStatement> statements = new LinkedList<>();

	public WFAction(boolean enterAction) {
		this.enterAction = enterAction;
	}

	public void add(ASTStatement s) {
		statements.add(s);
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append(enterAction ? "enter" : "leave");
		sb.append('\n');
		for (ASTStatement s : statements) {
			s.print(sb);
			sb.append('\n');
		}
		sb.append("end");
	}
}
