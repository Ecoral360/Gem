package org.ascore.as;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.ast.buildingBlocs.expressions.BinComp;
import org.ascore.ast.buildingBlocs.expressions.NumberExpr;
import org.ascore.ast.buildingBlocs.expressions.Var;
import org.ascore.ast.buildingBlocs.statements.ElifStatement;
import org.ascore.ast.buildingBlocs.statements.ElseStatement;
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

        addStatement("ELIF expression BRACKET_OPEN~" +
                        "BRACKET_CLOSE ELIF expression BRACKET_OPEN",
                (p, idxVariant) -> {
                    int idxElif = idxVariant;
                    int idxExpr = idxElif + 1;
                    var groups = ((Token) p.get(idxElif)).getValueGroups();
                    var OCode = groups.length > 1 ? groups[1] : null;
                    var condition = (Expression<?>) p.get(idxExpr);

                    return new ElifStatement(executorInstance, condition, OCode);
                });

        addStatement("ELSE BRACKET_OPEN~" +
                        "BRACKET_CLOSE ELSE BRACKET_OPEN",
                (p, idxVariant) -> {
                    int idxElse = idxVariant;
                    var groups = ((Token) p.get(idxElse)).getValueGroups();
                    var OCode = groups.length > 1 ? groups[1] : null;

                    return new ElseStatement(executorInstance, OCode);
                });

        addStatement("BRACKET_CLOSE", p ->
                switch (executorInstance.getRuntimeCoord().getCurrentBlock()) {
                    case "if", "elif", "else" -> new EndIfStatement(executorInstance);
                    default -> throw new RuntimeException("BRACKET_CLOSE sans BRACKET_OPEN");
                });

        // TODO: 5/24/22 Declaration + Assignments

        addStatement("", p -> Statement.evalExpression(new Expression.EmptyExpression(), ""));
        addStatement("#expression", p -> Statement.evalExpressions(p.toArray(Expression[]::new), " "));
    }

    protected void addExpressions() {
        addExpression("GCODE", p -> new Expression.SimpleExpression(((Token) p.get(0)).getValue()));

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

























