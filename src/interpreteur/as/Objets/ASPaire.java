package interpreteur.as.Objets;

import interpreteur.as.Objets.interfaces.ASObjet;

import java.util.Map;

public record ASPaire(Texte clef, ASObjet<?> valeur) implements ASObjet<Map.Entry<Texte, ASObjet<?>>> {

    @Override
    public Map.Entry<Texte, ASObjet<?>> getValue() {
        return Map.entry(clef, valeur);
    }

    @Override
    public boolean boolValue() {
        return valeur.boolValue();
    }

    @Override
    public String obtenirNomType() {
        return "paire";
    }

    @Override
    public String toString() {
        return clef + ": " + (valeur instanceof Texte ? "'" + valeur + "'" : valeur);
    }
}
