package ascore.as.modules.core;

import ascore.as.lang.Fonction;
import ascore.as.lang.managers.FonctionManager;
import ascore.as.lang.Variable;
import ascore.as.lang.Scope;
import ascore.as.lang.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe repr\u00E9sentant un module.<br>
 * Un module est un ensemble de {@link Fonction fonctions} et de
 * {@link Variable variables}/{@link ascore.as.lang.Constante constantes}
 * qui, lorsqu'{@link #utiliser(String) utiliser}, sont d\u00E9clar\u00E9es dans le scope
 * pour \u00EAtre utilis\u00E9 plus loin dans le code
 *
 * @author Mathis Laroche
 */
public final record Module(Fonction[] fonctions,
                           Variable[] variables) {

    public Module(Fonction[] fonctions) {
        this(fonctions, new Variable[]{});
    }

    public Module(Variable[] variables) {
        this(new Fonction[]{}, variables);
    }

    public void utiliser(String prefix) {
        FonctionManager.ajouterStructure(prefix);
        for (Fonction fonction : fonctions) {
            Scope.getCurrentScope().declarerVariable(new Variable(fonction.getNom(), fonction, new Type(fonction.obtenirNomType())));
        }
        for (Variable variable : variables) {
            Scope.getCurrentScope().declarerVariable(variable.clone());
        }
        FonctionManager.retirerStructure();
    }

    public void utiliser(List<String> nomMethodes) {
        for (Fonction fonction : fonctions) {
            if (nomMethodes.contains(fonction.getNom()))
                FonctionManager.ajouterFonction(fonction);
        }
        for (Variable variable : variables) {
            if (nomMethodes.contains(variable.obtenirNom())) {
                Scope.getCurrentScope().declarerVariable(variable);
            }
        }
    }

    /**
     * @return un array contenant toutes les fonctions du module
     */
    public Fonction[] getFonctions() {
        return fonctions;
    }

    /**
     * @return un array contenant toutes les variables du module
     */
    public Variable[] getVariables() {
        return variables;
    }

    /**
     * @return la liste des noms des fonctions du module
     */
    public List<String> getNomsFonctions() {
        if (fonctions.length == 0) return new ArrayList<>();
        return Stream.of(fonctions).map(Fonction::getNom).collect(Collectors.toList());
    }

    /**
     * @return la liste des noms des constantes du module
     */
    public List<String> getNomsVariables() {
        if (variables.length == 0) return new ArrayList<>();
        return Stream.of(variables).map(Variable::obtenirNom).collect(Collectors.toList());
    }

    /**
     * @return la liste des noms des constantes du module
     */
    public List<String> getNomsConstantesEtFonctions() {
        List<String> noms = getNomsFonctions();
        noms.addAll(getNomsVariables());
        return noms;
    }

    @Override
    public String toString() {
        return "Module{\n" +
                "fonctions=" + Arrays.toString(fonctions) + "\n" +
                ", variables=" + Arrays.toString(variables) + "\n" +
                '}';
    }
}














