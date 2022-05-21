package org.ascore.`as`.modules.corekotlin

import org.ascore.`as`.erreurs.ASError
import org.ascore.`as`.lang.ASParametre
import org.ascore.`as`.lang.ASScope
import org.ascore.`as`.lang.ASTypeBuiltin
import org.ascore.`as`.lang.ASVariable
import org.ascore.`as`.lang.datatype.ASNul
import org.ascore.`as`.lang.datatype.ASTexte
import org.ascore.data_manager.Data
import org.ascore.executor.Executor

object ModuleBuiltin : ASModuleFactory {

    override fun charger(executorInstance: Executor): ASModule {
        val fonctions = arrayOf(
            fonction(
                "afficher", ASTypeBuiltin.nulType, arrayOf(
                    ASParametre(ASTypeBuiltin.texte, "txt", ASTexte(""))
                )
            ) {
                val txt = it.getValeurParam("txt")
                executorInstance.addData(Data(Data.Id.AVERTISSEMENT).addParam(txt))
                ASNul()
            },
            fonction(
                "getVar", ASTypeBuiltin.tout, arrayOf(
                    ASParametre(ASTypeBuiltin.texte, "nomVariable", null)
                )
            ) {
                val nomVar = it.getValeurParam("nomVariable").value as String
                val variable = ASScope.getCurrentScopeInstance().getVariable(nomVar)
                    ?: throw ASError.ErreurVariableInconnue("La variable '$nomVar' n'est pas d\u00E9clar\u00E9e dans ce scope.")
                variable.valeurApresGetter
            }
        )
        val variables = arrayOf<ASVariable>()

        return ASModule(fonctions)
    }
}










