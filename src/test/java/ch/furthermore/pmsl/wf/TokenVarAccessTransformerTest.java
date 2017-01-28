package ch.furthermore.pmsl.wf;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import ch.furthermore.pmsl.Parser;
import ch.furthermore.pmsl.Scanner;
import ch.furthermore.pmsl.ast.Transformer;
import ch.furthermore.pmsl.wf.TokenVarAccessTransformer;

public class TokenVarAccessTransformerTest {
	@Test
	public void test() throws IOException {
		Parser p = new Parser(new Scanner(new StringReader("def foo()\nx = y\nend")));
		StringBuilder sb = new StringBuilder();
		Transformer transformer = new TokenVarAccessTransformer();
		p.definition().printTransform(sb, transformer);
		assertEquals("def foo()\nsetTokenVar(\"x\",getTokenVar(\"y\"))\nend", sb.toString());
	}
}
