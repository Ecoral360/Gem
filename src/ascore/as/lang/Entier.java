package ascore.as.lang;

import ascore.as.lang.interfaces.Nombre;
import ascore.as.erreurs.ASErreur;
import ascore.tokens.Token;

import java.util.Objects;

public class Entier implements Nombre {
    private final int valeur;

    public Entier(Token valeur) {
        try {
            this.valeur = Integer.parseInt(valeur.getValeur());
        } catch (NumberFormatException e) {
            throw new ASErreur.ErreurEntierInvalide("Les nombres entiers doivent avoir une valeur entre "
                    + Integer.MIN_VALUE + " et " + Integer.MAX_VALUE);
        }
    }

    public Entier(Number valeur) {
        try {
            this.valeur = valeur.intValue();
        } catch (NumberFormatException e) {
            throw new ASErreur.ErreurEntierInvalide("Les nombres entiers doivent avoir une valeur entre "
                    + Integer.MIN_VALUE + " et " + Integer.MAX_VALUE);
        }
    }

    public Entier(String valeur) {
        try {
            this.valeur = Integer.parseInt(valeur);
        } catch (NumberFormatException err) {
            throw new ASErreur.ErreurType("La valeur " + valeur + " ne peut pas \u00EAtre convertie en nombre entier.");
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
        if (!(o instanceof ascore.as.lang.Entier entier)) return false;
        return valeur == entier.valeur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }
}