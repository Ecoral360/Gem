package org.ascore.generateurs.lexer;

import org.ascore.tokens.Token;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Classe responsable de charger les {@link TokenRule} du langage avec lesquelles seront contruits les
 * {@link Token Token}
 *
 * @author Mathis Laroche
 */
public class LexerLoader extends LexerGenerator {
    private final Map<String, ?> dict;

    public LexerLoader(String fileName) {
        if (fileName == null) fileName = "org/ascore/grammar_rules/Grammar.yaml";

        Yaml yaml = new Yaml();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(fileName);
        this.dict = yaml.load(input);

    }

    @SuppressWarnings("unchecked")
    public void load() {
        var regles_a_ajouter = (Map<String, ?>) dict.get("Ajouter");
        if (regles_a_ajouter == null) {
            regles_a_ajouter = (Map<String, ?>) dict.get("Add");
        }

        for (String toAdd : regles_a_ajouter.keySet()) {
            if (regles_a_ajouter.get(toAdd) instanceof Map<?, ?>) {
                Map<String, String> categorie = (Map<String, String>) regles_a_ajouter.get(toAdd);
                if (categorie.containsKey("__prefix__")) {
                    categorie.replaceAll((ruleName, rulePattern) -> categorie.get("__prefix__") + rulePattern);
                    categorie.remove("__prefix__");
                }
                if (categorie.containsKey("__postfix__")) {
                    categorie.replaceAll((ruleName, rulePattern) -> rulePattern + categorie.get("__postfix__"));
                    categorie.remove("__postfix__");
                }
                for (String element : categorie.keySet()) {
                    this.ajouterRegle(element, categorie.get(element), toAdd);
                }
            } else {
                this.ajouterRegle(toAdd, (String) regles_a_ajouter.get(toAdd), "");
            }
        }

        var regles_a_ignorer = (List<String>) dict.get("Ignorer");
        if (regles_a_ignorer == null) {
            regles_a_ignorer = (List<String>) dict.get("Ignore");
        }
        regles_a_ignorer.forEach(this::ignorerRegle);
    }
}
