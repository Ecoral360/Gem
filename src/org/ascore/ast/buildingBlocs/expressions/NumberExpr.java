package org.ascore.ast.buildingBlocs.expressions;

import org.ascore.as.lang.datatype.ASDecimal;
import org.ascore.as.lang.datatype.ASObjet;
import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.tokens.Token;

public record NumberExpr(double value) implements Expression<ASObjet<?>> {

    public NumberExpr(Token token) {
        this(Double.parseDouble(token.getValue()));
    }

    @Override
    public ASObjet<?> eval() {
        return new ASDecimal(value);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
