package ch.furthermore.pmsl.ast;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.furthermore.pmsl.Context;

public class ASTFactorStringLiteralTest {
	@Test
	public void test() {
		assertEquals("\"foo\"", gen("foo"));
		assertEquals("\"a\\nb\"", gen("a\nb"));
		assertEquals("\"a\\rb\"", gen("a\rb"));
		assertEquals("\"a\\\"b\"", gen("a\"b"));
	}

	private String gen(String pmsl) {
		StringBuilder sb = new StringBuilder();
		new ASTFactorStringLiteral(null, pmsl).generate(new Context(), sb);
		return sb.toString();
	}
}
