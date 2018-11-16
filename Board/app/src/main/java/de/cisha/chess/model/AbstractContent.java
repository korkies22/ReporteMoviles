// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public abstract class AbstractContent implements Content
{
    private ContentId _id;
    
    public AbstractContent(final ContentId id) {
        this._id = id;
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = o instanceof Content;
        boolean equals = false;
        if (b) {
            if (this._id != null) {
                equals = this._id.equals(((Content)o).getContentId());
            }
            return equals;
        }
        return false;
    }
    
    @Override
    public ContentId getContentId() {
        return this._id;
    }
    
    @Override
    public int hashCode() {
        if (this._id != null) {
            return this._id.hashCode();
        }
        return 0;
    }
}
