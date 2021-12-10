package interpreteur.as.modules.builtins;

import interpreteur.as.Objets.Fonction;
import interpreteur.as.Objets.Variable;
import interpreteur.as.modules.core.Module;
import interpreteur.executeur.Executeur;

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
