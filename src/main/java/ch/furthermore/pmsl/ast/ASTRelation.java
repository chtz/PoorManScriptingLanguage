package ch.furthermore.pmsl.ast;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.ScannerToken;
import ch.furthermore.pmsl.ScannerTokenType;

public class ASTRelation extends AbstractASTNode {
	private final ASTExpression leftExpression;
	private ScannerTokenType relOp;
	private ASTExpression rightExpression;

	public ASTRelation(ScannerToken token, ASTExpression expression) {
		super(token);
		leftExpression = expression;
	}
	
	public void setRelOp(ScannerTokenType relOp) {
		this.relOp = relOp;
	}

	public ASTExpression getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(ASTExpression rightExpression) {
		this.rightExpression = rightExpression;
	}

	public void print(StringBuilder sb) {
		leftExpression.print(sb);
		
		if (relOp != null) {
			switch (relOp) {
			case REL_GT: sb.append('>'); break;
			case REL_LT: sb.append('<'); break;
			case REL_EQ: sb.append("=="); break;
			default: throw new IllegalStateException();
			}
			
			rightExpression.print(sb);
		}
	}

	public void generate(Context ctx, StringBuilder sb) {
		if (relOp != null) {
			sb.append('(');
		}
		leftExpression.generate(ctx, sb);
		if (relOp != null) {
			switch (relOp) {
			case REL_GT: sb.append('>'); break;
			case REL_LT: sb.append('<'); break;
			case REL_EQ: sb.append("=="); break;
			default: throw new IllegalStateException();
			}
			
			rightExpression.generate(ctx, sb);
			
			sb.append(')');
		}
	}
}
