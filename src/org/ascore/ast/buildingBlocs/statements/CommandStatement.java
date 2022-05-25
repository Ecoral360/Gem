package org.ascore.ast.buildingBlocs.statements;

import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.executor.Executor;

public class CommandStatement extends Statement {
    private final String command;
    private final String args;

    public CommandStatement(Executor executorInstance, String command, String args) {
        super(executorInstance);
        this.command = command;
        this.args = args.stripLeading();
    }

    @Override
    public String execute() {
        assert executorInstance != null;
        switch (command) {
            case "DEBUG" -> executorInstance.ecrire(args);
        }
        return "(" + command + ", " + args + ")";
    }
}
