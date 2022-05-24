package org.ascore.ast.buildingBlocs.expressions;

import org.ascore.as.lang.datatype.ASObjet;
import org.ascore.ast.buildingBlocs.Expression;

public record BinComp(Expression<?> left, Op op, Expression<?> right) implements Expression<ASObjet<?>> {
    public enum Op {
        EQUAL("EQ"),
        NOT_EQUAL("NE"),
        GREATER("GT"),
        LESSER("LT"),
        GREATER_EQUAL("GE"),
        LESSER_EQUAL("LE");

        private final String gCode;

        Op(String gCode) {
            this.gCode = gCode;
        }

        public String getGCode() {
            return gCode;
        }
    }


    @Override
    public ASObjet<?> eval() {
        return null;
    }

    @Override
    public String toString() {
        return "[" + left + " " + op.gCode + " " + right + "]";
    }
}
