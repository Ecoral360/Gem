package org.ascore.ast.buildingBlocs.expressions;

import org.ascore.as.lang.datatype.ASObjet;
import org.ascore.as.lang.datatype.ASTexte;
import org.ascore.ast.buildingBlocs.Expression;

public record GcodeExpr(String gcode, Expression<?> value) implements Expression<ASObjet<?>> {

    @Override
    public ASObjet<?> eval() {
        return new ASTexte(toString());
    }

    @Override
    public String toString() {
        return gcode + value;
    }
}
