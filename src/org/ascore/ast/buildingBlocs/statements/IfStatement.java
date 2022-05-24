package org.ascore.ast.buildingBlocs.statements;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.executor.Coordinate;
import org.ascore.executor.Executor;
import org.ascore.tokens.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IfStatement extends Statement {
    private final Expression<?> condition;
    private final String OCode;

    public IfStatement(@NotNull Executor executorInstance, Expression<?> condition, String OCode) {
        super(executorInstance);
        this.condition = condition;
        this.OCode = OCode;
    }

    @Override
    public String execute() {
        assert executorInstance != null;
        var OCode = executorState().getOCodeManager().pushOCodeOrNewMinor(this.OCode);
        executorInstance.getRuntimeCoord().makeBlock("if");
        return "O" + OCode + " if " + condition.toString();
    }

    @Override
    public Coordinate getNextCoordinate(Coordinate coord, List<Token> ligne) {
        return coord.makeBlock("if");
    }
}
