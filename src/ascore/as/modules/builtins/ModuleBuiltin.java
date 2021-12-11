package ascore.as.modules.builtins;

import ascore.as.objets.Fonction;
import ascore.as.objets.Variable;
import ascore.as.modules.core.Module;
import ascore.executeur.Executeur;

/**
 * Classe o\u00F9 sont d\u00E9finis les {@link Fonction fonctions} et les
 * {@link Variable variables}/{@link ascore.as.objets.Constante constantes} builtin
 *
 * @author Mathis Laroche
 */
public class ModuleBuiltin {

    public static Module charger(Executeur executeurInstance) {
        var fonctionsBuiltin = new Fonction[]{
                // ajouter vos fonctions builtin ici
        };
        var variablesBuiltin = new Variable[]{
                // ajouter vos variables et vos constantes builtin ici
        };

        return new Module(fonctionsBuiltin, variablesBuiltin);
    }
}
