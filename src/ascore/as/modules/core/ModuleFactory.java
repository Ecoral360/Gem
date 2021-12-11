package ascore.as.modules.core;

import ascore.executeur.Executeur;

@FunctionalInterface
public interface ModuleFactory {

    Module charger(Executeur executeurInstance);

}
