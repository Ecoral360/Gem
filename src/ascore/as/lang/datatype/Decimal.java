package ascore.as.lang.datatype;

import ascore.as.erreurs.ASErreur;
import ascore.tokens.Token;

import java.util.Objects;

public class Decimal implements Nombre {
    private final double valeur;

    public Decimal(Token valeur) {
        String val = valeur.getValeur();
        if (val.startsWith(".")) val = "0" + val;
        else if (val.endsWith(".")) val += "0";
        this.valeur = Double.parseDouble(val);
    }

    public Decimal(Number valeur) {
        this.valeur = valeur.doubleValue();
    }

    public Decimal(String valeur) {
        try {
            this.valeur = Double.parseDouble(valeur);
        } catch (NumberFormatException err) {
            throw new ASErreur.ErreurType("La valeur " + valeur + " ne peut pas \u00EAtre convertie en nombre d\u00E9cimal.");
        }
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public Double getValue() {
        return valeur;
    }

    @Override
    public boolean boolValue() {
        return this.valeur != 0;
    }

    @Override
    public String obtenirNomType() {
        return "decimal";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Decimal decimal)) return false;
        return Double.compare(decimal.valeur, valeur) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }
}
