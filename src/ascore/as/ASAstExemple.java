package ascore.as;

import ascore.as.lang.datatype.ASEntier;
import ascore.as.lang.datatype.ASTexte;
import ascore.ast.Ast;
import ascore.ast.buildingBlocs.Expression;
import ascore.ast.buildingBlocs.exemple.expressions.Addition;
import ascore.ast.buildingBlocs.exemple.programmes.Afficher;
import ascore.ast.buildingBlocs.exemple.expressions.ExprEntier;
import ascore.ast.buildingBlocs.exemple.expressions.ExprTexte;
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
            public Afficher apply(List<Object> p, Integer idxVariante) {
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
            public ExprEntier apply(List<Object> p, Integer idxVariante) {
                System.out.println(p.get(0));
                return new ExprEntier(new ASEntier((Token) p.get(0)));
            }
        });

        ajouterExpression("TEXTE", new Ast<ExprTexte>() {

            /**
             * @param p p contient l'\u00E9quivalent du pattern de l'expression en objet.<br>
             *          Par exemple, ici, <code>p = [Token(nom="ENTIER")]</code>
             * @return une Expression de type valeurConstante
             */
            @Override
            public ExprTexte apply(List<Object> p, Integer idxVariante) {
                System.out.println(p.get(0));
                return new ExprTexte(new ASTexte((Token) p.get(0)));
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
            public Addition apply(List<Object> p, Integer idxVariante) {
                ExprEntier premierNombre = (ExprEntier) p.get(0);
                ExprEntier deuxiemeNombre = (ExprEntier) p.get(2);
                System.out.println(p.get(1));
                return new Addition(premierNombre, deuxiemeNombre);
            }
        });
    }
}

























