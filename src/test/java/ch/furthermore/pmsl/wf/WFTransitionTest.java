package ch.furthermore.pmsl.wf;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import javax.script.ScriptException;

import org.junit.Test;

import ch.furthermore.pmsl.Parser;
import ch.furthermore.pmsl.Scanner;

public class WFTransitionTest {
	@Test
	public void test() throws IOException, NoSuchMethodException, ScriptException {
		Parser p = new Parser(new Scanner(new StringReader("transition to foo if x == y")));
		WFTransition tr = p.wfTransition();
		
		Token t = new Token();
		t.setTokenVar("x", "foo");
		t.setTokenVar("y", "foo");
		assertTrue(tr.evaluateCondition(t));
		
		t.setTokenVar("x", "foo");
		t.setTokenVar("y", "bar");
		assertFalse(tr.evaluateCondition(t));
	}
}
