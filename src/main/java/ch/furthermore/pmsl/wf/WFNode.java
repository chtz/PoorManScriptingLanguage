package ch.furthermore.pmsl.wf;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.script.ScriptException;

import ch.furthermore.pmsl.Printable;
import ch.furthermore.pmsl.ScannerTokenType;

public class WFNode implements Printable {
	final ScannerTokenType type;
	final String name;
	final List<WFTransition> transitions = new LinkedList<>();
	final List<WFAction> actions = new LinkedList<>();

	public WFNode(ScannerTokenType type, String name) {
		this.type = type;
		this.name = name;
	}

	public List<WFTransition> evalOutgoingTransitions(Token token) throws NoSuchMethodException, IOException, ScriptException {
		List<WFTransition> out = new LinkedList<>();
		for (WFTransition t : transitions) {
			if (t.evaluateCondition(token)) {
				out.add(t);
			}
		}
		return out;
	}

	public void executeEnterActions(Token token) throws NoSuchMethodException, IOException, ScriptException {
		for (WFAction a : actions) {
			if (a.enterAction) {
				a.execute(token);
			}
		}
	}
	
	public void executeLeaveActions(Token token) throws NoSuchMethodException, IOException, ScriptException {
		for (WFAction a : actions) {
			if (!a.enterAction) {
				a.execute(token);
			}
		}
	}
	
	public void addAction(WFAction action) {
		actions.add(action);
	}

	public void addTransition(WFTransition transition) {
		transitions.add(transition);
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append(ScannerTokenType.NODE_KEYWORD.equals(type) ? "node" : (
				ScannerTokenType.STATE_KEYWORD.equals(type) ? "state" : "join"
				));
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
