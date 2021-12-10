package interpreteur.ast.buildingBlocs.expressions;

import interpreteur.as.Objets.ValeurNul;
import interpreteur.as.Objets.interfaces.ASObjet;
import interpreteur.ast.buildingBlocs.Expression;

public class ExprExemple implements Expression<ASObjet<?>> {

    /**
     * Appel\u00E9 durant le Runtime, cette m\u00E9thode retourne un objet de type ASObjet
     *
     * @return le r\u00E9sultat de l'expression
     */
    @Override
    public ASObjet<?> eval() {
        return new ValeurNul();
    }
}
