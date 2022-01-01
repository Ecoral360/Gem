package ascore.as;

import ascore.executeur.Executeur;
import ascore.generateurs.ast.AstGenerator;


/**
 * Classe o\u00F9 sont d\u00E9finis les programmes et les expressions support\u00E9s par le
 * langage
 *
 * @author Mathis Laroche
 */
public class ASAst extends AstGenerator {
    private final Executeur executeurInstance;

    public ASAst(Executeur executeurInstance) {
        reset();
        ajouterProgrammes();
        ajouterExpressions();
        this.executeurInstance = executeurInstance;
    }

    protected void ajouterProgrammes() {
        // ajouter vos programmes ici

    }

    protected void ajouterExpressions() {
        // ajouter vos expressions ici

    }
}

























