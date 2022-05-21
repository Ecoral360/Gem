package org.ascore.as.modules.core;

import org.ascore.executeur.Executeur;

@FunctionalInterface
public interface ASModuleFactory {

    ASModule charger(Executeur executeurInstance);

}
