package ch.furthermore.pmsl.wf;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.Printable;
import ch.furthermore.pmsl.ScannerTokenType;

public class WFNode implements Printable {
	private final ScannerTokenType type;
	private final String name;
	private final List<WFTransition> transitions = new LinkedList<>();
	private final List<WFAction> actions = new LinkedList<>();

	public WFNode(ScannerTokenType type, String name) {
		this.type = type;
		this.name = name;
	}

	public void addAction(WFAction action) {
		actions.add(action);
	}


	public void addTransition(WFTransition transition) {
		transitions.add(transition);
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append(ScannerTokenType.NODE_KEYWORD.equals(type) ? "node" : "state");
		sb.append(' ');
		sb.append(name);
		sb.append('\n');
		for (WFTransition t : transitions) {
			t.print(sb);
			sb.append('\n');
		}
		for (WFAction a : actions) {
			a.print(sb);
			sb.append('\n');
		}
		sb.append("end");
	}
}
