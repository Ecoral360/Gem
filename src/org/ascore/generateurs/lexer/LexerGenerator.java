package org.ascore.generateurs.lexer;

import org.ascore.tokens.Token;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Classe charg\u00E9e de transformer un {@link String} en {@link List}<{@link Token}>
 *
 * @author Mathis Laroche
 */
public class LexerGenerator {
    static private final ArrayList<TokenRule> REGLES_IGNOREES = new ArrayList<>();
    static private ArrayList<TokenRule> reglesAjoutees = new ArrayList<>();

    public LexerGenerator() {
        reglesAjoutees.clear();
        REGLES_IGNOREES.clear();
        TokenRule.reset();
    }

    protected void ajouterRegle(String nom, String pattern, String categorie) {
        reglesAjoutees.add(new TokenRule(nom, pattern, categorie));
    }

    protected void sortRegle() {
        ArrayList<TokenRule> nomVars = reglesAjoutees.stream().filter(r -> r.getNom().equals("NOM_VARIABLE")).collect(Collectors.toCollection(ArrayList::new));
        reglesAjoutees = reglesAjoutees.stream().filter(r -> !r.getNom().equals("NOM_VARIABLE")).collect(Collectors.toCollection(ArrayList::new));

        Comparator<TokenRule> longueurRegle = (o1, o2) -> o2.getPattern().length() - o1.getPattern().length();

        reglesAjoutees.sort(longueurRegle);
        nomVars.sort(longueurRegle);

        reglesAjoutees.addAll(nomVars);
        // this.reglesAjoutees.forEach(r -> System.out.println(r.getNom() + "  " + r.getPattern()));
    }

    protected void ignorerRegle(String pattern) {
        REGLES_IGNOREES.add(new TokenRule(pattern));
    }

    public ArrayList<TokenRule> getReglesAjoutees() {
        return reglesAjoutees;
    }

    public ArrayList<TokenRule> getReglesIgnorees() {
        return REGLES_IGNOREES;
    }


    public List<Token> lex(String s) {

        List<Token> tokenList = new ArrayList<>();

        int idx = 0;
        int debut;

        while (idx < s.length()) {

            idx = this.prochainIndexValide(idx, s);

            if (idx == s.length()) break;

            boolean trouve = false;
            for (TokenRule tokenRule : this.getReglesAjoutees()) {
                Matcher match = Pattern.compile(tokenRule.getPattern()).matcher(s);
                if (match.find(idx) && match.start() == idx) {
                    debut = match.start();
                    idx = match.end();
                    String[] valueGroups = new String[match.groupCount()];
                    for (int i = 0; i < match.groupCount(); i++) {
                        valueGroups[i] = match.group(i + 1);
                    }
                    tokenList.add(tokenRule.makeToken(s.substring(match.start(), match.end()), debut, valueGroups));
                    trouve = true;
                    break;
                }
            }
            if (!trouve) {
                idx = ajouterErreur(idx, s, tokenList);
            }
        }
        return tokenList;
    }


    /**
     * @return le prochain index valide (ignore les patterns dans ignorerRegles)
     */
    private int prochainIndexValide(int idx, String s) {
        while (true) {
            boolean trouve = false;
            for (TokenRule tokenRule : this.getReglesIgnorees()) {
                Matcher match = Pattern.compile(tokenRule.getPattern()).matcher(s);
                if (match.find(idx) && match.start() == idx) {
                    trouve = true;
                    idx = match.end();
                }
            }
            if (!trouve) {
                break;
            }
        }
        return idx;
    }


    private int ajouterErreur(int idx, String s, List<Token> tokenList) {
        /**
         * @add le token ERREUR ??? la liste de token
         * @return le prochain index valide
         */
        idx = this.prochainIndexValide(idx, s);
        Matcher match = Pattern.compile("\\S+").matcher(s);
        //System.out.println("idxOrKey : " + idxOrKey);

        if (idx < s.length()) {
            match.find(idx);
            tokenList.add(new Token("(ERREUR)",
                    s.substring(match.start(), match.end()),
                    "",
                    match.start()));
            idx = match.end();
        }
        throw new LexerError("Error while lexing.\n"
                + "Unexpected token : " + match.group(0)
                + "\n" + s + "\n"
                + " ".repeat(match.start()) + "^".repeat(match.group(0).length()));

        // return idx;
    }
}




















