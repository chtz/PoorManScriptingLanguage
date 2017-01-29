package ch.furthermore.pmsl.wf;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import javax.script.ScriptException;

import org.junit.Test;

import ch.furthermore.pmsl.Parser;
import ch.furthermore.pmsl.Scanner;

public class TokenTest {
	@Test
	public void testEnterStart() throws IOException, NoSuchMethodException, ScriptException {
		Token t = new Token(workflow("workflow foo node bar enter x=111 end end end"));
		t.signal();
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
	}
	
	@Test
	public void testLeaveStart() throws IOException, NoSuchMethodException, ScriptException {
		Token t = new Token(workflow("workflow foo node bar leave x=111 end transition to bar2 end node bar2 end end"));
		t.signal();
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
	}
	
	@Test
	public void testNoLeaveStartState() throws IOException, NoSuchMethodException, ScriptException {
		Token t = new Token(workflow("workflow foo state bar leave x=111 end transition to bar2 end node bar2 end end"));
		t.signal();
		assertNull(t.getTokenVar("x"));
		
		t.signal();
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
	}
	
	@Test
	public void testNNodes() throws IOException, NoSuchMethodException, ScriptException {
		Token t = new Token(workflow("workflow foo "
				+ "node node1 leave x=111 end transition to node2 end "
				+ "node node2 leave y=222 end transition to node3 end "
				+ "node node3 enter z=333 end end "
				+ " end"));
		t.signal();
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
		assertEquals(222, ((Number)t.getTokenVar("y")).intValue());
		assertEquals(333, ((Number)t.getTokenVar("z")).intValue());
	}
	
	@Test
	public void testNStates() throws IOException, NoSuchMethodException, ScriptException {
		Token t = new Token(workflow("workflow foo "
				+ "state node1 leave x=111 end transition to node2 end "
				+ "state node2 leave y=222 end transition to node3 end "
				+ "node node3 enter z=333 end end "
				+ "end"));
		t.signal();
		
		assertNull(t.getTokenVar("x"));
		assertNull(t.getTokenVar("y"));
		assertNull(t.getTokenVar("z"));
		
		t.signal();
		
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
		assertNull(t.getTokenVar("y"));
		assertNull(t.getTokenVar("z"));
		
		t.signal();
		
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
		assertEquals(222, ((Number)t.getTokenVar("y")).intValue());
		assertEquals(333, ((Number)t.getTokenVar("z")).intValue());
	}
	
	@Test
	public void testCond() throws IOException, NoSuchMethodException, ScriptException {
		WFWorkflow wf = workflow("workflow foo "
				+ "node node1 transition to node2 if x == 5 transition to node3 if not(x == 5) end "
				+ "node node2 enter z=222 end end "
				+ "node node3 enter z=333 end end "
				+ "end");
		
		Token t = new Token(wf);
		t.setTokenVar("x", 5);
		t.signal();
		
		assertEquals(222, ((Number)t.getTokenVar("z")).intValue());
		
		t = new Token(wf);
		t.setTokenVar("x", 9);
		t.signal();
		
		assertEquals(333, ((Number)t.getTokenVar("z")).intValue());
	}
	
	@Test
	public void testFork() throws IOException, NoSuchMethodException, ScriptException {
		WFWorkflow wf = workflow("workflow foo "
				+ "node node1 enter x=0 y=0 end transition to node2 transition to node3 end "
				+ "state node2 leave x=111 end transition to node4 end "
				+ "state node3 leave y=222 end transition to node4 end "
				+ "join node4 enter z=333 end end "
				+ "end");
		
		Token t = new Token(wf);
		t.signal();
		
		assertEquals(0, ((Number)t.getTokenVar("x")).intValue());
		assertEquals(0, ((Number)t.getTokenVar("y")).intValue());
		assertNull(t.getTokenVar("z"));
		
		t.children.get(0).signal();
		
		assertNull(t.getTokenVar("z"));
		
		t.children.get(0).signal();
		
		assertEquals(111, ((Number)t.getTokenVar("x")).intValue());
		assertEquals(222, ((Number)t.getTokenVar("y")).intValue());
		assertEquals(333, ((Number)t.getTokenVar("z")).intValue());
	}
	
	public WFWorkflow workflow(String s) throws IOException {
		Parser p = new Parser(new Scanner(new StringReader(s)));
		return p.wfWorkflow();
	}
}
