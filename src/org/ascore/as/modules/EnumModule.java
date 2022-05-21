package org.ascore.as.modules;

import org.ascore.as.modules.builtins.ModuleBuiltin;
import org.ascore.as.modules.core.ASModuleFactory;
import org.ascore.as.modules.core.ASModuleManager;

public enum EnumModule {
    builtins(ModuleBuiltin::charger),
    ;

    EnumModule(ASModuleFactory moduleFactory) {
        ASModuleManager.enregistrerModule(this, moduleFactory);
    }
}
