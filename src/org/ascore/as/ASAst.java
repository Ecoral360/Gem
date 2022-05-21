package org.ascore.as;

import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Programme;
import org.ascore.executeur.Executeur;
import org.ascore.generateurs.ast.AstGenerator;
import org.ascore.tokens.Token;
import org.ascore.as.lang.datatype.ASEntier;
import org.ascore.ast.buildingBlocs.exemple.expressions.Addition;
import org.ascore.ast.buildingBlocs.exemple.expressions.ExprEntier;
import org.ascore.ast.buildingBlocs.exemple.programmes.Afficher;
import org.ascore.generateurs.annotations.SyntaxeExpression;
import org.ascore.generateurs.annotations.SyntaxeProgramme;

import java.util.List;


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
        extractFromInstance(this);
        this.executeurInstance = executeurInstance;
    }

    protected void ajouterProgrammes() {
        // ajouter vos programmes ici

    }

    protected void ajouterExpressions() {
        // ajouter vos expressions ici

    }

    //----------------- Programmes -----------------//
    // ajouter vos programmes ici

    @SyntaxeProgramme("SHOW expression")
    public Programme afficher(List<Object> p, Integer idxVariante) {
        return new Afficher((Expression<?>) p.get(1), executeurInstance);
    }

    //----------------- Expressions -----------------//
    // ajouter vos expressions ici

    @SyntaxeExpression("ENTIER")
    public ExprEntier entier(List<Object> p, Integer idxVariante) {
        return new ExprEntier(new ASEntier((Token) p.get(0)));
    }


    @SyntaxeExpression("expression PLUS expression")
    public Addition addition(List<Object> p, Integer idxVariante) {
        return new Addition((ExprEntier) p.get(0), (ExprEntier) p.get(2));
    }
}

























