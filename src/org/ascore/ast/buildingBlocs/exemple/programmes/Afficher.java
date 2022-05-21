package org.ascore.ast.buildingBlocs.exemple.programmes;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Programme;
import org.ascore.executeur.Executeur;
import org.jetbrains.annotations.NotNull;

public class Afficher extends Programme {
    private final Expression<?> exprAfficher;

    public Afficher(Expression<?> expr, @NotNull Executeur executeur) {
        super(executeur);
        this.exprAfficher = expr;
    }

    @Override
    public Object execute() {
        if (executeurInstance != null)
            executeurInstance.ecrire(exprAfficher.eval().toString());
        return null;
    }
}
