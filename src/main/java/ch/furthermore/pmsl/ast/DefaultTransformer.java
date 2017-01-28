package ch.furthermore.pmsl.ast;

public class DefaultTransformer implements Transformer {
	@Override
	public ASTNode transform(ASTStatement s) { //FIXME dirty hack
		if (s instanceof ASTCall) {
			return transform((ASTCall) s);
		}
		else if (s instanceof ASTDefinition) {
			return transform((ASTDefinition) s);
		}
		else if (s instanceof ASTFor) {
			return transform((ASTFor) s);
		}
		else if (s instanceof ASTIf) {
			return transform((ASTIf) s);
		}
		else if (s instanceof ASTVarAssignment) {
			return transform((ASTVarAssignment) s);
		}
		else if (s instanceof ASTVarDeclaration) {
			return transform((ASTVarDeclaration) s);
		}
		else throw new IllegalStateException();
	}
	
	@Override
	public ASTNode transform(ASTFactor s) { //FIXME dirty hack
		if (s instanceof ASTBExpression) {
			return transform((ASTBExpression) s);
		}
		else if (s instanceof ASTCall) {
			return transform((ASTCall) s);
		}
		else if (s instanceof ASTFactorBoolLiteral) {
			return transform((ASTFactorBoolLiteral) s);
		}
		else if (s instanceof ASTFactorIntegerLiteral) {
			return transform((ASTFactorIntegerLiteral) s);
		}
		else if (s instanceof ASTFactorStringLiteral) {
			return transform((ASTFactorStringLiteral) s);
		}
		else if (s instanceof ASTVariable) {
			return transform((ASTVariable) s);
		}
		else throw new IllegalStateException();
	}
	
	@Override
	public ASTNode transform(ASTBExpression n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTBTerm n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTCall n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTDefinition n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTExpression n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTFactorBoolLiteral n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTFactorIntegerLiteral n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTFactorStringLiteral n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTFor n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTIf n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTNotBFactor n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTRelation n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTTerm n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTVarAssignment n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTVarDeclaration n) {
		return n;
	}

	@Override
	public ASTNode transform(ASTVariable n) {
		return n;
	}
}
