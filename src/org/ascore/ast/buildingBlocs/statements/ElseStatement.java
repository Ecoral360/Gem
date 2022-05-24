package org.ascore.ast.buildingBlocs.statements;

import org.ascore.as.erreurs.ASError;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.executor.Coordinate;
import org.ascore.executor.Executor;
import org.ascore.tokens.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElseStatement extends Statement {
    private final String OCode;

    public ElseStatement(@NotNull Executor executorInstance, String OCode) {
        super(executorInstance);
        this.OCode = OCode;
    }

    @Override
    public String execute() {
        assert executorInstance != null;
        var OCode = executorState().getOCodeManager().pushOCodeOrNewMinor(this.OCode);
        var currentBlock = executorInstance.getRuntimeCoord().getCurrentBlock();
        if (!currentBlock.equals("if") && !currentBlock.equals("elif")) {
            throw new ASError.ErreurSyntaxe("else statement must follow an if or elif block");
        }
        executorInstance.getRuntimeCoord().replaceCurrentBlock("else");
        return "O" + OCode + " else";
    }

    @Override
    public Coordinate getNextCoordinate(Coordinate coord, List<Token> ligne) {
        var currentBlock = coord.getCurrentBlock();
        if (!currentBlock.equals("if") && !currentBlock.equals("elif")) {
            throw new ASError.ErreurSyntaxe("elif statement must follow an if or elif block");
        }
        return coord.replaceCurrentBlock("else");
    }
}