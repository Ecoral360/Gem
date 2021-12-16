package ascore.as;

import ascore.as.lang.datatype.ASEntier;
import ascore.ast.Ast;
import ascore.ast.buildingBlocs.Expression;
import ascore.ast.buildingBlocs.exemple.Addition;
import ascore.ast.buildingBlocs.exemple.Afficher;
import ascore.ast.buildingBlocs.exemple.ExprEntier;
import ascore.executeur.Executeur;
import ascore.tokens.Token;

import java.util.List;


/**
 * Les explications vont être rajoutées quand j'aurai la motivation de les écrire XD
 *
 * @author Mathis Laroche
 */
public class ASAstExemple extends ASAst {
    private final Executeur executeurInstance;

    public ASAstExemple(Executeur executeurInstance) {
        super(executeurInstance);
        this.executeurInstance = executeurInstance;
    }

    protected void ajouterProgrammes() {
        // ajouter vos programmes ici
        ajouterProgramme("SHOW expression", new Ast<Afficher>() {
            /**
             *
             * @param p p contient l'\u00E9quivalent du pattern de l'expression en objet.<br>
             *          Par exemple, ici, <code>p = [Token(nom="PLUS"), Expression]</code>
             * @return une Expression de type Addition
             */
            @Override
            public Afficher apply(List<Object> p) {
                System.out.println(p.get(0));
                return new Afficher((Expression<?>) p.get(1), executeurInstance);
            }
        });
    }

    protected void ajouterExpressions() {
        // ajouter vos expressions ici
        ajouterExpression("ENTIER", new Ast<ExprEntier>() {

            /**
             * @param p p contient l'\u00E9quivalent du pattern de l'expression en objet.<br>
             *          Par exemple, ici, <code>p = [Token(nom="ENTIER")]</code>
             * @return une Expression de type valeurConstante
             */
            @Override
            public ExprEntier apply(List<Object> p) {
                System.out.println(p.get(0));
                return new ExprEntier(new ASEntier((Token) p.get(0)));
            }
        });

        ajouterExpression("expression PLUS expression", new Ast<Addition>() {

            /**
             *
             * @param p p contient l'\u00E9quivalent du pattern de l'expression en objet.<br>
             *          Par exemple, ici, <code>p = [Expression, Token(nom="PLUS"), Expression]</code>
             * @return une Expression de type Addition
             */
            @Override
            public Addition apply(List<Object> p) {
                ExprEntier premierNombre = (ExprEntier) p.get(0);
                ExprEntier deuxiemeNombre = (ExprEntier) p.get(2);
                System.out.println(p.get(1));
                return new Addition(premierNombre, deuxiemeNombre);
            }
        });
    }
}

























