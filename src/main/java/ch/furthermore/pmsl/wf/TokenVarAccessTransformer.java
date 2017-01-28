package ch.furthermore.pmsl.wf;

import ch.furthermore.pmsl.ast.ASTCall;
import ch.furthermore.pmsl.ast.ASTExpression;
import ch.furthermore.pmsl.ast.ASTFactorStringLiteral;
import ch.furthermore.pmsl.ast.ASTNode;
import ch.furthermore.pmsl.ast.ASTTerm;
import ch.furthermore.pmsl.ast.ASTVarAssignment;
import ch.furthermore.pmsl.ast.ASTVariable;
import ch.furthermore.pmsl.ast.DefaultTransformer;

public class TokenVarAccessTransformer extends DefaultTransformer {
	@Override
	public ASTNode transform(ASTVariable n) {
		if ("returnCode".equals(n.getName())) {
			return n;
		}
		
		ASTCall c = new ASTCall(null, "getTokenVar");
		ASTExpression varExpr = new ASTExpression(null);
		ASTTerm varTerm = new ASTTerm(null);
		varTerm.add(new ASTFactorStringLiteral(null, n.getName()));
		varExpr.add(varTerm);
		c.add(varExpr);
		return c;	
	}

	@Override
	public ASTNode transform(ASTVarAssignment n) {
		if ("returnCode".equals(n.getName())) {
			return n;
		}
		
		ASTCall c = new ASTCall(null, "setTokenVar");
		ASTExpression varExpr = new ASTExpression(null);
		ASTTerm varTerm = new ASTTerm(null);
		varTerm.add(new ASTFactorStringLiteral(null, n.getName()));
		varExpr.add(varTerm);
		c.add(varExpr);
		c.add(n.getExpression());
		return c;
	}
}
