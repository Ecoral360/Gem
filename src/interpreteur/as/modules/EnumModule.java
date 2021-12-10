package interpreteur.as.modules;

import interpreteur.as.modules.builtins.ModuleBuiltin;
import interpreteur.as.modules.core.ModuleManager;
import interpreteur.as.modules.core.ModuleFactory;

public enum EnumModule {
    builtins(ModuleBuiltin::charger),
    ;

    EnumModule(ModuleFactory moduleFactory) {
        ModuleManager.enregistrerModule(this, moduleFactory);
    }
}
