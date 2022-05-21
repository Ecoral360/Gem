package org.ascore.as.erreurs;

import org.ascore.data_manager.Data;

public interface ASWarning {
    enum AvertissementType {

    }

    class Avertissement {
        private final AvertissementType type;
        private final String message;

        Avertissement(AvertissementType type, String message) {
            this.type = type;
            this.message = message;
        }

        public Data getAsData() {
            return new Data(Data.Id.AVERTISSEMENT).addParam(type).addParam(message);
        }
    }

}
