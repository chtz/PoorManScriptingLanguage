package ch.furthermore.pmsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class ScannerTest {
	@Test
	public void testStringLiterals() throws IOException {
		assertEquals("halli\nhallo", scanner("\"halli\\nhallo\"").next().getValue());
		assertEquals("halli\nhallo", scanner("\"halli\nhallo\"").next().getValue());
		assertEquals("halli\rhallo", scanner("\"halli\\rhallo\"").next().getValue());
		assertEquals("halli\rhallo", scanner("\"halli\rhallo\"").next().getValue());
		
		try {
			scanner("\"halli\\xhallo\"").next().getValue();
			fail();
		}
		catch (Exception e) {}
		
		assertEquals("halli \"x\" hallo", scanner("\"halli \\\"x\\\" hallo\"").next().getValue());
	}
	
	@Test
	public void testNext() throws IOException {
		Scanner scanner = scanner("def foo(x)\nx\nend\nvar a = 12\nprint(foo(a))");
		
		ScannerToken t = scanner.next();
		assertEquals(ScannerTokenType.DEF_KEYWORD, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("foo", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.LEFT_PARENTHESIS, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("x", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.RIGHT_PARENTHESIS, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("x", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.END_KEYWORD, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.VAR_KEYWORD, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("a", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.EQ, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.INTEGER_LITERAL, t.getType());
		assertEquals("12", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("print", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.LEFT_PARENTHESIS, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("foo", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.LEFT_PARENTHESIS, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.IDENTIFIER, t.getType());
		assertEquals("a", t.getValue());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.RIGHT_PARENTHESIS, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.RIGHT_PARENTHESIS, t.getType());
		
		t = scanner.next();
		assertEquals(ScannerTokenType.EOF, t.getType());
	}

	private Scanner scanner(String s) {
		return new Scanner(new StringReader(s));
	}
}
