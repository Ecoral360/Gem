package ascore.ast.buildingBlocs.programmes;


import ascore.as.objets.*;
import ascore.as.objets.interfaces.ASObjet;
import ascore.as.erreurs.ASErreur;
import ascore.ast.buildingBlocs.Expression;
import ascore.ast.buildingBlocs.Programme;
import ascore.ast.buildingBlocs.expressions.Var;

/**
 * Exemple d'un {@link Programme} charg\u00E9 de d\u00E9clarer une variable
 * au <i>Compile time</i> et de lui assigner sa valeur par d\u00E9faut au <i>Runtime</i>
 *
 * @author Mathis Laroche
 */
public class Declarer extends Programme {
    private final Expression<?> valeur;
    private final boolean constante;
    private final Type type;
    private final Var var;

    /**
     * Cr\u00E9ation d'un programme Declarer
     * <br>
     * repr\u00E9sentation des param\u00E8tres: <code>constante expr: type = valeur</code>
     *
     * @param expr      l'expression repr\u00E9sentant ce qui est d\u00E9clar\u00E9
     * @param valeur    la valeur par d\u00E9faut (premi\u00E8re valeur de la variable).
     *                  Si elle est <code>null</code>, la valeur par d\u00E9faut est {@link ascore.as.objets.ValeurNul ValeurNul}
     * @param type      le type de la variable. Si <code>null</code>, vaut {@link TypeBuiltin#tout tout}
     * @param constante booleen indiquant si la variable est une constante
     */
    public Declarer(Expression<?> expr, Expression<?> valeur, Type type, boolean constante) {
        // get la variable
        if (expr instanceof Var) {
            var = (Var) expr;
        } else {
            throw new ASErreur.ErreurSyntaxe("Seul les variables peuvent \u00EAtre d\u00E9clar\u00E9e, pas " + expr);
        }

        this.valeur = valeur;
        this.constante = constante;
        this.type = type == null ? TypeBuiltin.tout.asType() : type;
        addVariable();
    }

    /**
     * Ajout de la variable dans le {@link Scope} au <i>Compile time</i>
     */
    private void addVariable() {

        // get l'objet variable s'il existe
        Variable varObj = Scope.getCurrentScope().getVariable(var.getNom());

        // si la variable existe déjà et que c'est une constante, lance une erreur, car on ne peut pas modifier une constante
        if (varObj != null)
            throw new ASErreur.ErreurAssignement("La variable '" + var.getNom() + "' a d\u00E9j\u00E0 \u00E9t\u00E9 d\u00E9clar\u00E9e");

        // si le mot "const" est présent dans l'assignement de la variable, on crée la constante
        // sinon si la variable a été déclarée avec "var", on crée la variable
        varObj = constante
                ? new Constante(var.getNom(), null)
                : new Variable(var.getNom(), null, type);

        Scope.getCurrentScope().declarerVariable(varObj);

        var.setNom(varObj.obtenirNom());
    }

    /**
     * Assignement de la variable au <i>Runtime</i> si celle-ci \u00E9tait pr\u00E9sente dans la
     * d\u00E9claration de la variable
     *
     * @return null
     */
    @Override
    public Object execute() {
        //ASObjet.Variable variable = ASObjet.VariableManager.obtenirVariable(var.getNom());
        Variable variable = Scope.getCurrentScopeInstance().getVariable(var.getNom());
        if (this.valeur != null) {
            ASObjet<?> valeur = this.valeur.eval();
            variable.setValeur(valeur);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Declarer{" +
                "valeur=" + valeur +
                ", constante=" + constante +
                ", type=" + type +
                ", var=" + var +
                '}';
    }
}
