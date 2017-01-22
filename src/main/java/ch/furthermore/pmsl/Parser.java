package ch.furthermore.pmsl;

import java.io.IOException;

import ch.furthermore.pmsl.ast.ASTBExpression;
import ch.furthermore.pmsl.ast.ASTBTerm;
import ch.furthermore.pmsl.ast.ASTCall;
import ch.furthermore.pmsl.ast.ASTDefinition;
import ch.furthermore.pmsl.ast.ASTExpression;
import ch.furthermore.pmsl.ast.ASTFactor;
import ch.furthermore.pmsl.ast.ASTFactorBoolLiteral;
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

public class Parser {
	private final Scanner scanner;
	private ScannerToken current;

	public Parser(Scanner scanner) throws IOException {
		this.scanner = scanner;
		
		read();
	}

	ASTStatement statement() throws IOException {
		if (ScannerTokenType.DEF_KEYWORD.equals(current().getType())) {
			return definition();
		}
		else if (ScannerTokenType.VAR_KEYWORD.equals(current().getType())) {
			return varDeclaration();
		}
		else if (ScannerTokenType.IF_KEYWORD.equals(current().getType())) {
			return ifstatement();
		}
		else if (ScannerTokenType.FOR_KEYWORD.equals(current().getType())) {
			return forStatement();
		}
		else {
			ScannerToken start = current();
			
			assertEquals(ScannerTokenType.IDENTIFIER, current().getType());
			
			String name = current().getValue();
			
			read();
			
			if (ScannerTokenType.LEFT_PARENTHESIS.equals(current().getType())) { 
				return call(start, name);
			}
			else if (ScannerTokenType.EQ.equals(current().getType())) {
				return varAssignment(start, name);
			}
			else throw new ParserException(current(), ScannerTokenType.LEFT_PARENTHESIS + " or " + ScannerTokenType.EQ + " expected");
		}
	}
	
	ASTFor forStatement() throws IOException {
		ScannerToken start = current();
		
		assertEquals(ScannerTokenType.FOR_KEYWORD, current().getType());
		
		read();
		
		ASTVarDeclaration declaration = varDeclaration(false);
		
		assertEquals(ScannerTokenType.SEMICOLON, current().getType());
		
		read();
		
		ASTRelation relation = relation();
		
		assertEquals(ScannerTokenType.SEMICOLON, current().getType());
		
		read();
		
		ASTVarAssignment assignment = varAssignment(start, null);
		
		assertEquals(ScannerTokenType.DO_KEYWORD, current().getType());
		
		read();
		
		ASTFor forStatement = new ASTFor(start, declaration, relation, assignment);
		
		while (!ScannerTokenType.END_KEYWORD.equals(current().getType())) {
			ASTStatement statement = statement();
			
			forStatement.add(statement);
		}
		
		read();
		
		return forStatement;
	}
	
	ASTIf ifstatement() throws IOException {
		ScannerToken start = current();
		
		assertEquals(ScannerTokenType.IF_KEYWORD, current().getType());
		
		read();
		
		ASTBExpression condition = bExpression();
		
		ASTIf ifstatement = new ASTIf(start, condition);
		
		assertEquals(ScannerTokenType.THEN_KEYWORD, current().getType());
		
		read();
		
		while (!ScannerTokenType.END_KEYWORD.equals(current().getType())) {
			ifstatement.add(statement());
		}
		
		read();
		
		return ifstatement;
	}

	public ASTDefinition definition() throws IOException {
		ScannerToken start = current();
		
		assertEquals(ScannerTokenType.DEF_KEYWORD, current().getType());
		
		read();
		
		assertEquals(ScannerTokenType.IDENTIFIER, current().getType());
		
		ASTDefinition definition = new ASTDefinition(start, current().getValue());
		
		read();
		
		assertEquals(ScannerTokenType.LEFT_PARENTHESIS, current().getType());
		
		read();
		
		while (!ScannerTokenType.RIGHT_PARENTHESIS.equals(current().getType())) {
			assertEquals(ScannerTokenType.IDENTIFIER, current().getType());
			
			definition.add(current().getValue());
			
			read();
			
			if (ScannerTokenType.COMMA.equals(current().getType())) {
				read();
			}
		}
		
		read();
		
		while (!ScannerTokenType.END_KEYWORD.equals(current().getType())) {
			if (ScannerTokenType.RETURN_KEYWORD.equals(current().getType())) {
				read();
				
				ASTExpression returnValue = expression();
				
				definition.setReturnExpression(returnValue);
				
				break;
			}
			else {
				definition.add(statement());
			}
		}
		
		read();
		
		return definition;
	}
	
	ASTCall call() throws IOException {
		return call(null, null);
	}
	
	ASTCall call(ScannerToken start, String name) throws IOException {
		ASTCall call;
		if (start == null) {
			assertEquals(ScannerTokenType.IDENTIFIER, current().getType());
			
			call = new ASTCall(start, current().getValue());
			
			read();
		}
		else {
			call = new ASTCall(current(), name);
		}
		
		assertEquals(ScannerTokenType.LEFT_PARENTHESIS, current().getType());
		
		read();
		
		while (!ScannerTokenType.RIGHT_PARENTHESIS.equals(current().getType())) {
			call.add(expression());
			
			if (ScannerTokenType.COMMA.equals(current().getType())) {
				read();
			}
		}
		
		read();
		
		return call;
	}
	
	ASTVarDeclaration varDeclaration() throws IOException { 
		return varDeclaration(true);
	}
	
	ASTVarDeclaration varDeclaration(boolean expectInitialKeyword) throws IOException { 
		ScannerToken start = current();
		
		if (expectInitialKeyword) {
			assertEquals(ScannerTokenType.VAR_KEYWORD, current().getType());
			
			read();
		}
		
		assertEquals(ScannerTokenType.IDENTIFIER, current().getType());
		
		String variable = current().getValue();
		
		read();
		
		assertEquals(ScannerTokenType.EQ, current().getType());

		read();
		
		ASTExpression expression = expression();
		
		return new ASTVarDeclaration(start, variable, expression);
	}
	
	ASTVarAssignment varAssignment() throws IOException { 
		return varAssignment(null, null);
	}
	
	ASTVarAssignment varAssignment(ScannerToken start, String variable) throws IOException { 
		if (variable == null) {
			start = current();
			
			assertEquals(ScannerTokenType.IDENTIFIER, current().getType());
			
			variable = current().getValue();
		
			read();
		}
		
		assertEquals(ScannerTokenType.EQ, current().getType());

		read();
		
		ASTExpression expression = expression();
		
		return new ASTVarAssignment(start, variable, expression);
	}
	
	ASTBExpression bExpression() throws IOException {
		ASTBExpression expression = new ASTBExpression(current());
		
		expression.add(bTerm());
		
		while (ScannerTokenType.OR_KEYWORD.equals(current().getType())) {
			read();
			
			expression.add(bTerm());
		}
		
		return expression;
	}
	
	ASTBTerm bTerm() throws IOException {
		ASTBTerm term = new ASTBTerm(current());
		
		term.add(notBFactor());
		
		while (ScannerTokenType.AND_KEYWORD.equals(current().getType())) {
			read();
			
			term.add(notBFactor());
		}
		
		return term;
	}
	
	ASTNotBFactor notBFactor() throws IOException {
		ScannerToken start = current();
		
		if (ScannerTokenType.NOT_KEYWORD.equals(current().getType())) {
			read();
			
			ASTRelation relation = relation();
			
			return new ASTNotBFactor(start, relation, true);
		}
		else {
			ASTRelation relation = relation();
			
			return new ASTNotBFactor(start, relation, false);
		}
	}
	
	ASTRelation relation() throws IOException {
		ScannerToken start = current();
		
		ASTExpression leftExpression = expression();
		
		ASTRelation relation = new ASTRelation(start, leftExpression);
		
		if (ScannerTokenType.REL_GT.equals(current().getType())
				|| ScannerTokenType.REL_LT.equals(current().getType())
				|| ScannerTokenType.REL_EQ.equals(current().getType())) {
			ScannerTokenType relOp = current().getType();
			
			relation.setRelOp(relOp);
			
			read();
			
			ASTExpression rightExpression = expression();
			
			relation.setRightExpression(rightExpression);
		}
		
		return relation;
	}
	
	ASTExpression expression() throws IOException {
		ASTExpression expression = new ASTExpression(current());
		
		expression.add(term());
		
		while (ScannerTokenType.PLUS.equals(current().getType()) || ScannerTokenType.MINUS.equals(current().getType())) {
			expression.add(current().getType());
			
			read();
			
			expression.add(term());
		}
		
		return expression;
	}

	ASTTerm term() throws IOException {
		ASTTerm term = new ASTTerm(current());
		
		term.add(factor());
		
		while (ScannerTokenType.ASTERIX.equals(current().getType()) || ScannerTokenType.DIVISION.equals(current().getType())) {
			term.add(current().getType());
			
			read();
			
			term.add(factor());
		}
		
		return term;
	}
	
	ASTFactor factor() throws IOException {
		ScannerToken start = current();
		
		if (ScannerTokenType.IDENTIFIER.equals(current().getType())) {
			String name = current().getValue();
			
			read();
			
			if (ScannerTokenType.LEFT_PARENTHESIS.equals(current().getType())) {
				ASTCall call = call(start, name);
				
				return call;
			}
			else {
				ASTVariable variable = new ASTVariable(start, name);
				
				return variable;
			}
		}
		else if (ScannerTokenType.INTEGER_LITERAL.equals(current().getType())) {
			ASTFactorIntegerLiteral literal = new ASTFactorIntegerLiteral(start, current().getValue());
			
			read();
			
			return literal;
		}
		else if (ScannerTokenType.STRING_LITERAL.equals(current().getType())) {
			ASTFactorStringLiteral literal = new ASTFactorStringLiteral(start, current().getValue());
			
			read();
			
			return literal;
		}
		else if (ScannerTokenType.TRUE_KEYWORD.equals(current().getType()) || ScannerTokenType.FALSE_KEYWORD.equals(current().getType())) {
			ASTFactorBoolLiteral literal = new ASTFactorBoolLiteral(start, current().getValue());
			
			read();
			
			return literal;
		}
		else if (ScannerTokenType.LEFT_PARENTHESIS.equals(current().getType())) {
			read();
			
			ASTBExpression expression = bExpression();
			
			assertEquals(ScannerTokenType.RIGHT_PARENTHESIS, current().getType());
			
			read();
			
			return expression;
		}
		else {
			throw new ParserException(current(), "NOT expected");
		}
	}

	private void assertEquals(ScannerTokenType expected, ScannerTokenType actual) {
		if (!expected.equals(actual)) {
			throw new ParserException(current(), "NOT expected (Expected: " + expected + ")");
		}
	}
	
	private ScannerToken current() {
		return current;
	}

	private ScannerToken read() throws IOException {
		return current = scanner.next();
	}
}
