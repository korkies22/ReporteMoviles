/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.CishaUUID;
import java.io.Serializable;

public class GameID
extends CishaUUID
implements Serializable {
    private static final long serialVersionUID = 4283290355304738590L;

    public GameID(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        super(stringBuilder.toString(), true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getDBId() {
        try {
            return Integer.parseInt(this.getUuid());
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }
}
