package interpreteur.ast.buildingBlocs.exemple;

import interpreteur.as.Objets.Entier;
import interpreteur.ast.buildingBlocs.Expression;


public record ExprEntier(Entier val) implements Expression<Entier> {

    @Override
    public Entier eval() {
        return val;
    }

    @Override
    public String toString() {
        return "ValeurConstante{" +
                "val=" + val +
                '}';
    }
}
