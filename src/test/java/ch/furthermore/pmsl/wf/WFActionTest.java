package ch.furthermore.pmsl.wf;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import javax.script.ScriptException;

import org.junit.Test;

import ch.furthermore.pmsl.Parser;
import ch.furthermore.pmsl.Scanner;

public class WFActionTest {
	@Test
	public void test() throws IOException, NoSuchMethodException, ScriptException {
		Parser p = new Parser(new Scanner(new StringReader("enter\nx = y\nend")));
		WFAction a = p.wfAction();
		Token t = new Token();
		t.setTokenVar("y", "foo");
		a.execute(t);
		assertEquals("foo", t.getTokenVar("x"));
	}
}
