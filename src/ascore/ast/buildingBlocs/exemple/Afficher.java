package ascore.ast.buildingBlocs.exemple;

import ascore.ast.buildingBlocs.Expression;
import ascore.ast.buildingBlocs.Programme;
import ascore.executeur.Executeur;
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
