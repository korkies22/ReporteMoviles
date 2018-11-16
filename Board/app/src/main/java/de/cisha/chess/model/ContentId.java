// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public class ContentId
{
    private String _id;
    
    public ContentId(final String id) {
        this._id = id;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof String && ((String)o).equals(this._id);
    }
    
    @Override
    public int hashCode() {
        return this._id.hashCode();
    }
    
    public boolean isValid() {
        return this._id != null && !this._id.isEmpty();
    }
    
    @Override
    public String toString() {
        return this._id;
    }
}
