package ch.furthermore.pmsl.wf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SerializedToken {
	private String node;
	private Map<String,Object> vars = new HashMap<>();
	private List<SerializedToken> children = new LinkedList<>();
	
	public SerializedToken() {}
	
	public SerializedToken(Token t) {
		node = t.currentNodeName;
		vars.putAll(t.vars);
		for (Token child : t.children) {
			children.add(new SerializedToken(child));
		}
	}
	
	public Token token(WFWorkflow wf, Object builtIns) {
		Token t = new Token(wf, builtIns);
		t.currentNodeName = node;
		t.vars.putAll(vars);
		for (SerializedToken st : children) {
			Token c = st.token(wf, builtIns);
			
			c.parent = t;
			t.children.add(c);
		}
		return t;
	}

	public Map<String, Object> getVars() {
		return vars;
	}

	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}

	public List<SerializedToken> getChildren() {
		return children;
	}

	public void setChildren(List<SerializedToken> children) {
		this.children = children;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
}
