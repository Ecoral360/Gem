package org.ascore.ast.buildingBlocs.statements;

import org.ascore.as.erreurs.ASError;
import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.executor.Coordinate;
import org.ascore.executor.Executor;
import org.ascore.tokens.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElifStatement extends Statement {
    private final Expression<?> condition;
    private final String OCode;

    public ElifStatement(@NotNull Executor executorInstance, Expression<?> condition, String OCode) {
        super(executorInstance);
        this.condition = condition;
        this.OCode = OCode;
    }

    @Override
    public String execute() {
        assert executorInstance != null;
        var currentOCode = executorState().getOCodeManager().getCurrentOCode();
        if (this.OCode != null && !this.OCode.equals(currentOCode)) {
            throw new ASError.ErreurSyntaxe("The OCode of the elif statement must be the same as the OCode of the if statement");
        }
        var currentBlock = executorInstance.getRuntimeCoord().getCurrentBlock();
        if (!currentBlock.equals("if") && !currentBlock.equals("elif")) {
            throw new ASError.ErreurSyntaxe("elif statement must follow an if or elif block");
        }
        executorInstance.getRuntimeCoord().replaceCurrentBlock("elif");
        return "O" + currentOCode + " elseif " + condition;
    }

    @Override
    public Coordinate getNextCoordinate(Coordinate coord, List<Token> ligne) {
        var currentBlock = coord.getCurrentBlock();
        if (!currentBlock.equals("if") && !currentBlock.equals("elif")) {
            throw new ASError.ErreurSyntaxe("elif statement must follow an if or elif block");
        }
        return coord.replaceCurrentBlock("elif");
    }
}
