package org.ascore.`as`.modules.corekotlin

import org.ascore.`as`.lang.ASFonctionModule
import org.ascore.`as`.lang.ASParametre
import org.ascore.`as`.lang.ASTypeBuiltin
import org.ascore.`as`.lang.datatype.ASObjet
import org.ascore.executeur.Executeur

interface ASModuleFactory {
    fun charger(executeurInstance: Executeur): ASModule

    fun fonction(
        nom: String,
        typeRetour: ASTypeBuiltin,
        parametres: Array<ASParametre>,
        effet: (ASFonctionModule) -> ASObjet<*>
    ): ASFonctionModule {
        return object : ASFonctionModule(nom, typeRetour, parametres) {
            override fun executer(): ASObjet<*> {
                return effet(this)
            }
        }
    }

    fun fonction(
        nom: String,
        typeRetour: ASTypeBuiltin,
        effet: (ASFonctionModule) -> ASObjet<*>
    ): ASFonctionModule {
        return object : ASFonctionModule(nom, typeRetour, arrayOf()) {
            override fun executer(): ASObjet<*> {
                return effet(this)
            }
        }
    }
}