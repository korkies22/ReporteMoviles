/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

public class CishaUUID {
    private String _uuid;
    private boolean _verified;

    public CishaUUID(String string, boolean bl) {
        this._uuid = string;
        this._verified = bl;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof CishaUUID) {
            object = (CishaUUID)object;
            if ((this._uuid != null && this._uuid.equals(object.getUuid()) || this._uuid == null && object.getUuid() == null) && object.isVerified() == this.isVerified()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public String getUuid() {
        return this._uuid;
    }

    public int hashCode() {
        if (this._uuid == null) {
            return 0;
        }
        return this._uuid.hashCode();
    }

    public boolean isVerified() {
        return this._verified;
    }

    public void setUuid(String string) {
        this._uuid = string;
    }

    public void setVerified(boolean bl) {
        this._verified = bl;
    }

    public String toString() {
        return this.getUuid();
    }
}
