package org.ascore.ast.buildingBlocs.expressions;

import org.ascore.as.lang.datatype.ASObjet;
import org.ascore.as.lang.datatype.ASTexte;
import org.ascore.ast.buildingBlocs.Expression;

public record BinOpExpr(Expression<?> left, Op op, Expression<?> right) implements Expression<ASObjet<?>> {
    public enum Op {
        ADD("+"),
        POW("**"),
        MUL("*"),
        SUBTRACT("-"),
        DIV("/"),
        MOD("MOD");

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
        return new ASTexte(toString());
    }

    @Override
    public String toString() {
        return left + " " + op.gCode + " " + right;
    }
}
