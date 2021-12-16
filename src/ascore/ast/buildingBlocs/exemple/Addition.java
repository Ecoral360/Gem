package ascore.ast.buildingBlocs.exemple;

import ascore.as.lang.datatype.ASEntier;
import ascore.ast.buildingBlocs.Expression;

public record Addition(ExprEntier nb1, ExprEntier nb2) implements Expression<ASEntier> {
    @Override
    public ASEntier eval() {
        return new ASEntier(((ASEntier) nb1.eval()).getValue() + ((ASEntier) nb2.eval()).getValue());
    }
}
