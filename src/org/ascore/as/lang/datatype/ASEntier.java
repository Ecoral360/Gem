package org.ascore.as.lang.datatype;

import org.ascore.as.erreurs.ASError;
import org.ascore.tokens.Token;

import java.util.Objects;

public class ASEntier implements ASNombre {
    private final int valeur;

    public ASEntier(Token valeur) {
        try {
            this.valeur = Integer.parseInt(valeur.getValue());
        } catch (NumberFormatException e) {
            throw new ASError.ErreurEntierInvalide("Les nombres entiers doivent avoir une valeur entre "
                                                   + Integer.MIN_VALUE + " et " + Integer.MAX_VALUE);
        }
    }

    public ASEntier(Number valeur) {
        try {
            this.valeur = valeur.intValue();
        } catch (NumberFormatException e) {
            throw new ASError.ErreurEntierInvalide("Les nombres entiers doivent avoir une valeur entre "
                                                   + Integer.MIN_VALUE + " et " + Integer.MAX_VALUE);
        }
    }

    public ASEntier(String valeur) {
        try {
            this.valeur = Integer.parseInt(valeur);
        } catch (NumberFormatException err) {
            throw new ASError.ErreurType("La valeur " + valeur + " ne peut pas \u00EAtre convertie en nombre entier.");
        }
    }


    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public Integer getValue() {
        return valeur;
    }

    @Override
    public boolean boolValue() {
        return this.valeur != 0;
    }

    @Override
    public String obtenirNomType() {
        return "entier";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ASEntier entier)) return false;
        return valeur == entier.valeur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }
}
