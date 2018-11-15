/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

public class ContentId {
    private String _id;

    public ContentId(String string) {
        this._id = string;
    }

    public boolean equals(Object object) {
        if (object instanceof String) {
            return ((String)object).equals(this._id);
        }
        return false;
    }

    public int hashCode() {
        return this._id.hashCode();
    }

    public boolean isValid() {
        if (this._id != null && !this._id.isEmpty()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this._id;
    }
}
