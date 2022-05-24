package org.ascore.as;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.ast.buildingBlocs.expressions.BinComp;
import org.ascore.ast.buildingBlocs.expressions.NumberExpr;
import org.ascore.ast.buildingBlocs.expressions.Var;
import org.ascore.ast.buildingBlocs.statements.EndIfStatement;
import org.ascore.ast.buildingBlocs.statements.IfStatement;
import org.ascore.executor.Executor;
import org.ascore.generateurs.ast.AstGenerator;
import org.ascore.tokens.Token;


/**
 * Classe o\u00F9 sont d\u00E9finis les programmes et les expressions support\u00E9s par le
 * langage
 *
 * @author Mathis Laroche
 */
public class ASAst extends AstGenerator {
    private final Executor executorInstance;

    public ASAst(Executor executorInstance) {
        reset();
        addStatements();
        addExpressions();
        this.executorInstance = executorInstance;
    }

    protected void addStatements() {
        // add your statements here
        addStatement("IF expression BRACKET_OPEN", p -> {
            var groups = ((Token) p.get(0)).getValueGroups();
            var OCode = groups.length > 1 ? groups[1] : null;
            var condition = (Expression<?>) p.get(1);

            return new IfStatement(executorInstance, condition, OCode);
        });

        addStatement("BRACKET_CLOSE", p ->
                switch (executorInstance.getRuntimeCoord().getBlocActuel()) {
                    case "if", "elif", "else" -> new EndIfStatement(executorInstance);
                    default -> throw new RuntimeException("BRACKET_CLOSE sans BRACKET_OPEN");
                });

        addStatement("", p -> Statement.evalExpression(new Expression.EmptyExpression(), ""));
        addStatement("#expression", p -> Statement.evalExpressions(p.toArray(Expression[]::new), " "));
    }

    protected void addExpressions() {
        addExpression("GCODE.*", p -> new Expression.SimpleExpression(((Token) p.get(0)).getValue()));

        // add your expressions here
        addExpression("NUMBER", p -> new NumberExpr(((Token) p.get(0))));

        // variables
        addExpression("{variables}", p -> {
            // get the variable name (after the '$', or between '#<' and '>' in case of GCode variables)
            var groups = ((Token) p.get(0)).getValueGroups();
            return new Var(groups[0]);
        });

        // comparaisons
        addExpression("expression {comparison} expression", p -> {
            var left = (Expression<?>) p.get(0);
            var right = (Expression<?>) p.get(2);
            var operator = ((Token) p.get(1)).getName();
            return new BinComp(left, BinComp.Op.valueOf(operator), right);
        });
    }
}

























