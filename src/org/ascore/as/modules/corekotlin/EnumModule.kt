package org.ascore.`as`.modules.corekotlin

import org.ascore.`as`.modules.EnumModule
import org.ascore.`as`.modules.core.ASModuleFactory
import org.ascore.`as`.modules.core.ASModuleManager

enum class EnumModule(moduleFactory: ASModuleFactory?) {
    ;

    init {
        ASModuleManager.enregistrerModule(EnumModule.valueOf(this.toString()), moduleFactory)
    }
}