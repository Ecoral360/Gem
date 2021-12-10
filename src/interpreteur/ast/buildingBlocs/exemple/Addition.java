package interpreteur.ast.buildingBlocs.exemple;

import interpreteur.as.Objets.Entier;
import interpreteur.ast.buildingBlocs.Expression;

public record Addition(ExprEntier nb1, ExprEntier nb2) implements Expression<Entier> {
    @Override
    public Entier eval() {
        return new Entier(((Entier) nb1.eval()).getValue() + ((Entier) nb2.eval()).getValue());
    }
}
