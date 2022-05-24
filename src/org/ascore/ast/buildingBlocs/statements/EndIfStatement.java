package org.ascore.ast.buildingBlocs.statements;

import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.executor.Coordinate;
import org.ascore.executor.Executor;
import org.ascore.tokens.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EndIfStatement extends Statement {

    public EndIfStatement(@NotNull Executor executorInstance) {
        super(executorInstance);
    }

    @Override
    public String execute() {
        assert executorInstance != null;
        var OCode = executorState().getOCodeManager().popCurrentOCode();
        executorInstance.getRuntimeCoord().endBlock();
        return "O" + OCode + " endif";
    }

    @Override
    public Coordinate getNextCoordinate(Coordinate coord, List<Token> ligne) {
        return coord.endBlock();
    }
}
