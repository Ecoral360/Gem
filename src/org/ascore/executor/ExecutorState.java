package org.ascore.executor;

import org.ascore.data_manager.OCodeManager;

public class ExecutorState {
    private final StringBuilder finalCode = new StringBuilder();
    private final OCodeManager OCodeManager = new OCodeManager();


    public StringBuilder getFinalCode() {
        return finalCode;
    }

    public OCodeManager getOCodeManager() {
        return OCodeManager;
    }
}
