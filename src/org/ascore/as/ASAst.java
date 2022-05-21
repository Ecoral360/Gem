package org.ascore.as;

import org.ascore.executor.Executor;
import org.ascore.generateurs.ast.AstGenerator;


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

    }

    protected void addExpressions() {
        // add your expressions here

    }
}

























