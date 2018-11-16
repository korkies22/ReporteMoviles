// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public class CishaUUID
{
    private String _uuid;
    private boolean _verified;
    
    public CishaUUID(final String uuid, final boolean verified) {
        this._uuid = uuid;
        this._verified = verified;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CishaUUID) {
            final CishaUUID cishaUUID = (CishaUUID)o;
            return ((this._uuid != null && this._uuid.equals(cishaUUID.getUuid())) || (this._uuid == null && cishaUUID.getUuid() == null)) && cishaUUID.isVerified() == this.isVerified();
        }
        return false;
    }
    
    public String getUuid() {
        return this._uuid;
    }
    
    @Override
    public int hashCode() {
        if (this._uuid == null) {
            return 0;
        }
        return this._uuid.hashCode();
    }
    
    public boolean isVerified() {
        return this._verified;
    }
    
    public void setUuid(final String uuid) {
        this._uuid = uuid;
    }
    
    public void setVerified(final boolean verified) {
        this._verified = verified;
    }
    
    @Override
    public String toString() {
        return this.getUuid();
    }
}
