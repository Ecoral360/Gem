package ascore.as;

import ascore.executeur.Executeur;
import ascore.generateurs.ast.AstGenerator;


/**
 * Les explications vont être rajoutées quand j'aurai la motivation de les écrire XD
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

























