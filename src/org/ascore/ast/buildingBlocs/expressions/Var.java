package org.ascore.ast.buildingBlocs.expressions;

import org.ascore.as.erreurs.ASError;
import org.ascore.as.lang.datatype.ASNombre;
import org.ascore.as.lang.datatype.ASObjet;
import org.ascore.as.lang.datatype.ASTexte;
import org.ascore.ast.buildingBlocs.Expression;

/**
 * Exemple d'une expression charg\u00E9e de retourner la valeur d'une variable au Runtime
 * selon le nom de la variable qui lui a \u00E9t\u00E9 pr\u00E9cis\u00E9e au compile time
 *
 * @author Mathis Laroche
 */
public class Var implements Expression<ASObjet<?>> {
    private String nom;

    public Var(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return la valeur de la variable ayant le m\u00EAme nom que {@link #nom Var.nom}
     */
    @Override
    public ASObjet<?> eval() {
        try {
            // return ASObjet.VariableManager.obtenirVariable(this.nom).getValeurApresGetter();
            // ASScope.getCurrentScopeInstance().getVariable(nom);
            if (ASNombre.estNumerique(nom)) {
                return new ASTexte("#" + nom);
            }
            return new ASTexte("#<" + nom + ">");
        } catch (NullPointerException e) {
            throw new ASError.ErreurVariableInconnue("La variable '" + this.nom + "' n'est pas d\u00E9clar\u00E9e dans ce scope.");
        }
    }

    @Override
    public String toString() {
        if (ASNombre.estNumerique(nom)) {
            return "#" + nom;
        }
        return "#<" + nom + ">";
    }
}

