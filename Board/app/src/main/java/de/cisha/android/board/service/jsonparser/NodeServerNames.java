// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

public enum NodeServerNames
{
    NOTIFICATION_SERVER("notification"), 
    PAIRING_SERVER("pairing");
    
    private String _name;
    
    private NodeServerNames(final String name) {
        this._name = name;
    }
    
    public String getName() {
        return this._name;
    }
    
    @Override
    public String toString() {
        return this._name;
    }
}
