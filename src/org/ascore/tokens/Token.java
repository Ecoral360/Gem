package org.ascore.tokens;


import org.ascore.generateurs.lexer.Regle;

import java.util.Objects;

/**
 * Les explications vont être rajouté quand j'aurai la motivation de les écrire XD
 *
 * @author Mathis Laroche
 */

public class Token {

    private final String nom, valeur, categorie;
    private final int debut;
    private final Regle regleParent;

    public Token(String nom, String valeur, String categorie, int debut, Regle regleParent) {
        this.nom = nom;
        this.valeur = valeur;
        this.categorie = categorie;
        this.debut = debut;
        this.regleParent = regleParent;
    }

    public Token(String nom, String valeur, String categorie, int debut) {
        this.nom = nom;
        this.valeur = valeur;
        this.categorie = categorie;
        this.debut = debut;
        this.regleParent = null;
    }

    public static Token withName(String name) {
        return new Token(name, null, null, -1);
    }

    public static Token withCategory(String category) {
        return new Token(null, null, category, -1);
    }

    public static Token withValue(String value) {
        return new Token(null, value, null, -1);
    }

    public String getCategorie() {
        return this.categorie;
    }

    public String getNom() {
        return this.nom;
    }

    public String getValue() {
        return this.valeur;
    }

    public int getStart() {
        return this.debut;
    }

    public Regle getRegleParent() {
        return regleParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        if (token.getNom() != null && nom != null)
            return Objects.equals(nom, token.nom);
        if (token.getCategorie() != null && categorie != null)
            return Objects.equals(categorie, token.categorie);
        if (token.getValue() != null && valeur != null)
            return Objects.equals(valeur, token.valeur);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return "Token{" +
               "nom='" + nom + '\'' +
               ", valeur='" + valeur + '\'' +
               (!categorie.isBlank() ? ", categorie='" + categorie + '\'' : "") +
               ", debut=" + debut +
               (regleParent != null ? ", pattern=" + regleParent.getPattern() : "") +
               '}';
    }
}
















