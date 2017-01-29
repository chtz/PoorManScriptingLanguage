package ch.furthermore.pmsl.wf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.script.ScriptException;

import ch.furthermore.pmsl.BuiltIn;
import ch.furthermore.pmsl.ScannerTokenType;

public class Token {
	public final static String ID_NAME = "id";
	
	WFWorkflow workflow;
	Object builtIns = new Object();
	
	String currentNodeName;
	
	private final Map<String,Object> vars = new HashMap<>();
	
	private Token parent;
	final List<Token> children = new LinkedList<>();
	
	Token() {
		setTokenVar(ID_NAME, UUID.randomUUID().toString());
	}
	
	public Token(WFWorkflow wf) {
		this();
		this.workflow = wf;
	}
	
	public Token(WFWorkflow wf, Object builtIns) {
		this(wf);
		this.builtIns = builtIns;
	}
	
	public Token findById(String id) {
		if (id.equals(vars.get(ID_NAME))) {
			return this;
		}
		for (Token t : children) {
			if (id.equals(t.vars.get(ID_NAME))) {
				return t;
			}	
		}
		return null;
	}
	
	public void signal() throws NoSuchMethodException, IOException, ScriptException {
		signal(this);
	}
	
	static void signal(Token t) throws NoSuchMethodException, IOException, ScriptException {
		WFNode currentNode = null;
		ArrayList<WFNode> next = new ArrayList<>();
		
		if (t.currentNodeName == null) {
			WFNode start = t.workflow.getStartNode();
			
			start.executeEnterActions(t);
			
			next.add(start);
		}
		else {
			currentNode = t.workflow.getNode(t.currentNodeName);
			
			for (WFTransition out : currentNode.evalOutgoingTransitions(t)) {
				next.add(t.workflow.getNode(out.name));
			}
		}
		
		if (next.size() == 1) {
			WFNode nextNode = next.get(0);
			
			leave(t, currentNode, nextNode);
		}
		else if (next.size() > 1) {
			ArrayList<Token> childs = new ArrayList<>(next.size());
			for (int i = 0; i < next.size(); i++) {
				childs.add(t.createChild());
			}
			
			for (int i = 0; i < next.size(); i++) {
				WFNode nextNode = next.get(i);
				Token childToken = childs.get(i);
				
				leave(childToken, currentNode, nextNode);
			}
		}
	}

	static void leave(Token t, WFNode currentNode, WFNode nextNode)
			throws NoSuchMethodException, IOException, ScriptException 
	{
		if (currentNode != null) {
			currentNode.executeLeaveActions(t);
		}
		
		if (ScannerTokenType.JOIN_KEYWORD.equals(nextNode.type)) {
			if (t.parent.children.size() > 1) {
				t.destroy();
				t = null;
			}
			else {
				Token parent = t.parent;
				t.destroy();
				t = parent;
			}
		}
		
		if (t != null) {
			nextNode.executeEnterActions(t);
			
			t.currentNodeName = nextNode.name;
			
			if (!ScannerTokenType.STATE_KEYWORD.equals(nextNode.type)) {
				signal(t);
			}
		}
	}
	
	public Token createChild() {
		Token child = new Token(workflow, builtIns);
		
		child.parent = this;
		children.add(child);
		
		return child;
	}
	
	public void destroy() {
		if (parent != null) {
			parent.children.remove(this);
			parent = null;
		}
	}
	
	public boolean hasVar(String name) {
		if (vars.keySet().contains(name)) {
			return true;
		}
		if (parent != null) {
			return parent.hasVar(name);
		}
		return false;
	}
	
	@BuiltIn
	public Object getTokenVar(String name) {
		if (vars.keySet().contains(name)) {
			return vars.get(name);
		}
		if (parent != null) {
			return parent.getTokenVar(name);
		}
		return null;
	}
	
	@BuiltIn
	public void setTokenVar(String name, Object o) {
		if (parent != null && parent.hasVar(name)) {
			parent.setTokenVar(name, o);
		}
		else {
			vars.put(name, o);
		}
	}

	@Override
	public String toString() {
		return "Token [currentNodeName=" + currentNodeName + ", vars=" + vars + ", children=" + children + "]";
	}
}
