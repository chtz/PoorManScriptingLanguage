package ch.furthermore.pmsl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import ch.furthermore.pmsl.ast.ASTBExpression;
import ch.furthermore.pmsl.ast.ASTBTerm;
import ch.furthermore.pmsl.ast.ASTCall;
import ch.furthermore.pmsl.ast.ASTDefinition;
import ch.furthermore.pmsl.ast.ASTExpression;
import ch.furthermore.pmsl.ast.ASTFactorIntegerLiteral;
import ch.furthermore.pmsl.ast.ASTFactorStringLiteral;
import ch.furthermore.pmsl.ast.ASTFor;
import ch.furthermore.pmsl.ast.ASTIf;
import ch.furthermore.pmsl.ast.ASTNotBFactor;
import ch.furthermore.pmsl.ast.ASTRelation;
import ch.furthermore.pmsl.ast.ASTStatement;
import ch.furthermore.pmsl.ast.ASTTerm;
import ch.furthermore.pmsl.ast.ASTVarAssignment;
import ch.furthermore.pmsl.ast.ASTVarDeclaration;
import ch.furthermore.pmsl.ast.ASTVariable;
import ch.furthermore.pmsl.ast.ASTNode;

public class ParserTest {
	@Test
	public void testBExpression() throws IOException {
		Parser parser = parser("a or b");
		ASTBExpression e = parser.bExpression();
		assertEquals("a or b", toString(e));
	}
	
	@Test
	public void testBTerm() throws IOException {
		Parser parser = parser("a and b");
		ASTBTerm t = parser.bTerm();
		assertEquals("a and b", toString(t));
	}
	
	@Test
	public void testNotBFactor() throws IOException {
		Parser parser = parser("not a");
		ASTNotBFactor p = parser.notBFactor();
		assertEquals("not a", toString(p));
	}
	
	@Test
	public void testRelation() throws IOException {
		Parser parser = parser("a == b");
		ASTRelation p = parser.relation();
		assertEquals("a==b", toString(p));
	}
	
	@Test
	public void testIf() throws IOException {
		Parser parser = parser("if a - 1 then\na()\nb()\nend");
		ASTIf i = parser.ifstatement();
		assertEquals("if a-1 then\na()\nb()\nend", toString(i)); 
	}
	
	@Test
	public void testFor() throws IOException {
		Parser parser = parser("for a = 0; a < 10; a = a + 1 do\nvar b = a\nend");
		ASTFor f = parser.forStatement();
		assertEquals("for a=0;a<10;a=a+1 do\nvar b=a\nend", toString(f)); 
	}
	
	@Test
	public void testStatementDefinition() throws IOException {
		Parser parser = parser("def foo(x)\nret x+x\nend");
		ASTStatement s = parser.statement();
		assertEquals("def foo(x)\nret x+x\nend", toString(s)); 
	}
	
	@Test
	public void testDefinition() throws IOException {
		Parser parser = parser("def foo(x,y)\nvar a=1\nend");
		ASTDefinition d = parser.definition();
		assertEquals("def foo(x,y)\nvar a=1\nend", toString(d));
	}
	
	@Test
	public void testCall() throws IOException {
		Parser parser = parser("a(1, 2)");
		ASTCall a = parser.call();
		assertEquals("a(1,2)", toString(a));
	}
	
	@Test
	public void testAssignment() throws IOException {
		Parser parser = parser("var a = 1");
		ASTVarDeclaration a = parser.varDeclaration();
		assertEquals("var a=1", toString(a));
	}
	
	@Test
	public void testLetAssignment() throws IOException {
		Parser parser = parser("a = 1");
		ASTVarAssignment a = parser.varAssignment();
		assertEquals("a=1", toString(a));
	}
	
	@Test
	public void testExpression() throws IOException {
		Parser parser = parser("1+2-3");
		ASTExpression e = parser.expression();
		assertEquals("1+2-3", toString(e));
	}
	
	@Test
	public void testTerm() throws IOException {
		Parser parser = parser("1*2/3");
		ASTTerm t = parser.term();
		assertEquals("1*2/3", toString(t));
	}
	
	@Test
	public void testFactorIntegerLiteral() throws IOException {
		Parser parser = parser("123");
		ASTFactorIntegerLiteral f = (ASTFactorIntegerLiteral) parser.factor();
		assertEquals("123", toString(f));
	}
	
	@Test
	public void testFactorStringLiteral() throws IOException {
		Parser parser = parser("\"te-\nst\"");
		ASTFactorStringLiteral f = (ASTFactorStringLiteral) parser.factor();
		assertEquals("\"te-\nst\"", toString(f));
	}
	
	@Test
	public void testFactorVariable() throws IOException {
		Parser parser = parser("abc");
		ASTVariable v = (ASTVariable) parser.factor();
		assertEquals("abc", toString(v));
	}
	
	@Test
	public void testFactorExpression() throws IOException {
		Parser parser = parser("(1+2/3)");
		ASTBExpression v = (ASTBExpression) parser.factor();
		assertEquals("1+2/3", toString(v));
	}

	private Parser parser(String s) throws IOException {
		Scanner scanner = scanner(s);
		return new Parser(scanner);
	}

	private Scanner scanner(String s) {
		return new Scanner(new StringReader(s));
	}

	private String toString(ASTNode a) {
		StringBuilder sb = new StringBuilder();
		a.print(sb);
		return sb.toString();
	}
}
