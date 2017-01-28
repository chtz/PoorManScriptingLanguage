package ch.furthermore.pmsl.ast;

public interface Transformer {
	ASTNode transform(ASTStatement s);
	ASTNode transform(ASTFactor astFactor);
	ASTNode transform(ASTBExpression n);
	ASTNode transform(ASTBTerm n);
	ASTNode transform(ASTCall n);
	ASTNode transform(ASTDefinition n);
	ASTNode transform(ASTExpression n);
	ASTNode transform(ASTFactorBoolLiteral n);
	ASTNode transform(ASTFactorIntegerLiteral n);
	ASTNode transform(ASTFactorStringLiteral n);
	ASTNode transform(ASTFor n);
	ASTNode transform(ASTIf n);
	ASTNode transform(ASTNotBFactor n);
	ASTNode transform(ASTRelation n);
	ASTNode transform(ASTTerm n);
	ASTNode transform(ASTVarAssignment n);
	ASTNode transform(ASTVarDeclaration n);
	ASTNode transform(ASTVariable n);
}
