/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.jsonparser;

public enum NodeServerNames {
    PAIRING_SERVER("pairing"),
    NOTIFICATION_SERVER("notification");
    
    private String _name;

    private NodeServerNames(String string2) {
        this._name = string2;
    }

    public String getName() {
        return this._name;
    }

    public String toString() {
        return this._name;
    }
}
