package org.ascore.data_manager;

import java.util.ArrayList;
import java.util.Stack;

public class OCodeManager {
    private final ArrayList<String> OCodeList = new ArrayList<>();
    private final Stack<String> OCodesStack = new Stack<>();
    private int currentMajorOCodeAsInt = 0;
    private int currentMinorOCodeAsInt = 0;

    public OCodeManager() {
        OCodesStack.push(currentOCode());
    }

    private int currentOCodeAsInt() {
        return 1000 + currentMajorOCodeAsInt * 100 + currentMinorOCodeAsInt * 5;
    }

    private String currentOCode() {
        return "" + currentOCodeAsInt();
    }

    public String pushOCode(String OCode) {
        if (OCodeList.contains(OCode)) {
            throw new IllegalArgumentException("OCode already exists");
        }
        OCodesStack.push(OCode);
        return OCode;
    }

    public String pushOCodeOrNewMinor(String OCode) {
        if (OCode == null) {
            return pushNewMinorOCode();
        }
        if (OCodeList.contains(OCode)) {
            throw new IllegalArgumentException("OCode already exists");
        }
        OCodesStack.push(OCode);
        return OCode;
    }

    public String pushOCodeOrNewMajor(String OCode) {
        if (OCode == null) {
            return pushNewMajorOCode();
        }
        if (OCodeList.contains(OCode)) {
            throw new IllegalArgumentException("OCode already exists");
        }
        OCodesStack.push(OCode);
        return OCode;
    }

    /**
     * Creates a new *major* OCode and adds it to the stack (major OCodes are 100 units apart)
     *
     * @return the new OCode
     */
    public String pushNewMajorOCode() {
        currentMajorOCodeAsInt += 1;
        currentMinorOCodeAsInt = 0;
        String newOCode = currentOCode();
        OCodeList.add(newOCode);
        OCodesStack.push(newOCode);
        return newOCode;
    }

    /**
     * Creates a new *minor* OCode and adds it to the stack (minor OCodes are 5 units apart)
     *
     * @return the new OCode
     */
    public String pushNewMinorOCode() {
        currentMinorOCodeAsInt += 1;
        String newOCode = currentOCode();
        OCodeList.add(newOCode);
        OCodesStack.push(newOCode);
        return newOCode;
    }

    public String popCurrentOCode() {
        return OCodesStack.pop();
    }

    public String getCurrentOCode() {
        return OCodesStack.peek();
    }

    public String getCurrentOCode(int index) {
        return OCodesStack.get(index);
    }
}
