package Enums;

import java.io.Serializable;

public enum CmdMod implements Serializable {
    PRIVATE(0),
    PUBLIC(1),
    PUBLIC_ONLY(2),
    VARIATE(3);

    private int index;

    CmdMod(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}