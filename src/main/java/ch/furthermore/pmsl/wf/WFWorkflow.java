package ch.furthermore.pmsl.wf;

import java.util.LinkedList;
import java.util.List;

import ch.furthermore.pmsl.Printable;

public class WFWorkflow implements Printable {
	private final String name;
	private final List<WFNode> nodes = new LinkedList<>();

	public WFWorkflow(String name) {
		this.name = name;
	}
	
	public WFNode getStartNode() {
		return nodes.get(0);
	}

	public WFNode getNode(String name) {
		for (WFNode n : nodes) {
			if (name.equals(n.name)) {
				return n;
			}
		}
		return null;
	}
	
	public void add(WFNode node) {
		nodes.add(node);
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append("workflow ");
		sb.append(name);
		sb.append('\n');
		for (WFNode n : nodes) {
			n.print(sb);
			sb.append('\n');
		}
		sb.append("end");
	}
}
