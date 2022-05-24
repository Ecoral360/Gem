package org.ascore.ast.buildingBlocs;

import org.ascore.as.lang.datatype.ASObjet;
import org.ascore.as.lang.datatype.ASTexte;

import java.io.Serializable;

/**
 * Une expression est un objet cr\u00E9e au <i>Compile time</i> et \u00E9valu\u00E9 au <i>Runtime</i> par un
 * {@link Statement}
 *
 * @param <T> le type de l'objet retourn\u00E9 par l'expression lorsqu'\u00E9valuer
 * @author Mathis Laroche
 */
public interface Expression<T extends ASObjet<?>> extends Serializable {

    /**
     * Appel\u00E9 durant le <i>Runtime</i>, cette m\u00E9thode retourne un objet de type {@link ASObjet}
     *
     * @return le r\u00E9sultat de l'expression
     */
    T eval();


    class SimpleExpression implements Expression<ASObjet<?>> {
        private final String repr;

        public SimpleExpression(String repr) {
            this.repr = repr;
        }

        @Override
        public ASObjet<?> eval() {
            return new ASTexte(repr);
        }

        @Override
        public String toString() {
            return repr;
        }
    }

    class EmptyExpression implements Expression<ASObjet<?>> {

        @Override
        public ASObjet<?> eval() {
            return new ASTexte("\n");
        }

        @Override
        public String toString() {
            return "\n";
        }
    }
}
