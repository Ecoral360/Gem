package ascore.ast.buildingBlocs.exemple;

import ascore.as.Objets.Entier;
import ascore.ast.buildingBlocs.Expression;


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