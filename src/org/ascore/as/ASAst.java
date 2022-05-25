package org.ascore.as;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.ast.buildingBlocs.expressions.*;
import org.ascore.ast.buildingBlocs.statements.*;
import org.ascore.executor.Executor;
import org.ascore.generateurs.ast.AstGenerator;
import org.ascore.tokens.Token;

import java.util.ArrayList;


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

        addStatement("COMMAND", p -> {
            var groups = ((Token) p.get(0)).getValueGroups();
            var command = groups[0];
            var args = groups[1];
            return new CommandStatement(executorInstance, command, args);
        });

        addStatement("expression {assignments} expression", p -> {
            var variable = (Expression<?>) p.get(0);
            var opName = ((Token) p.get(1)).getName();
            BinOpExpr.Op op = opName.equals("ASSIGNMENT") ? null : BinOpExpr.Op.valueOf(opName.split("_")[0]);
            var value = (Expression<?>) p.get(2);
            return new AssignStatement(variable, op, value);
        });

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

        addExpression("CROCHET_OUV #expression CROCHET_FERM",
                p -> new Expression.SimpleExpression("[" +
                        evalOneExpr(new ArrayList<>(p.subList(1, p.size() - 1)), null)
                                .eval() + "]"
                ));

        addExpression("expression {arithmetic} expression", p -> {
            var left = (Expression<?>) p.get(0);
            var right = (Expression<?>) p.get(2);
            var operator = ((Token) p.get(1)).getName();
            return new BinOpExpr(left, BinOpExpr.Op.valueOf(operator), right);
        });

        addExpression("GCODE expression",
                p -> new GcodeExpr(((Token) p.get(0)).getValue(), (Expression<?>) p.get(1)));
    }
}

























