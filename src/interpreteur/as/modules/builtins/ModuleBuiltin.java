package interpreteur.as.modules.builtins;

import interpreteur.as.Objets.Fonction;
import interpreteur.as.Objets.Variable;
import interpreteur.as.modules.core.Module;
import interpreteur.executeur.Executeur;

/**
 * Classe o\u00F9 sont d\u00E9finis les {@link Fonction fonctions} et les
 * {@link Variable variables}/{@link interpreteur.as.Objets.Constante constantes} builtin
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
