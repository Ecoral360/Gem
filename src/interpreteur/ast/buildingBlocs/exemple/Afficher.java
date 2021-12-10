package interpreteur.ast.buildingBlocs.exemple;

import interpreteur.ast.buildingBlocs.Expression;
import interpreteur.ast.buildingBlocs.Programme;
import interpreteur.executeur.Executeur;
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
