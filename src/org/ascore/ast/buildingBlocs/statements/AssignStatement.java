package org.ascore.ast.buildingBlocs.statements;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.ast.buildingBlocs.expressions.BinOpExpr;
import org.ascore.ast.buildingBlocs.expressions.NumberExpr;

public class AssignStatement extends Statement {
    private final Expression<?> assignedExpression;
    private final Expression<?> value;
    private final String op;

    public AssignStatement(Expression<?> assignedExpression, BinOpExpr.Op op, Expression<?> value) {
        this.assignedExpression = assignedExpression;
        this.value = value;
        this.op = op == null ? null : op.getGCode();
    }

    @Override
    public String execute() {
        var varName = assignedExpression.eval();
        if (this.op != null) {
            return varName + " = " + "[" + varName + " " + op + " " + value.eval() + "]";
        }
        return varName + " = " + (value instanceof NumberExpr ? value.eval() : "[" + value.eval() + "]");
    }
}
