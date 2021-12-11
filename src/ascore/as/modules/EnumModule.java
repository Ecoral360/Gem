package ascore.as.modules;

import ascore.as.modules.builtins.ModuleBuiltin;
import ascore.as.modules.core.ModuleManager;
import ascore.as.modules.core.ModuleFactory;

public enum EnumModule {
    builtins(ModuleBuiltin::charger),
    ;

    EnumModule(ModuleFactory moduleFactory) {
        ModuleManager.enregistrerModule(this, moduleFactory);
    }
}
