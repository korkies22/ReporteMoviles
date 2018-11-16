// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;

public abstract class BaseObject implements Serializable
{
    private static int nextId = 100000;
    private static final long serialVersionUID = 8705939552646099423L;
    private int _id;
    
    public BaseObject() {
        this._id = getNextid();
    }
    
    public BaseObject(final int id) {
        this._id = id;
    }
    
    private static int getNextid() {
        synchronized (BaseObject.class) {
            final int nextId = BaseObject.nextId;
            BaseObject.nextId = nextId + 1;
            return nextId;
        }
    }
    
    protected boolean equals(final Object o, final Object o2) {
        return (o == null && o2 == null) || (o != null && o2 != null && o.equals(o2));
    }
    
    public int getId() {
        return this._id;
    }
    
    protected int hashcode(final Object o) {
        if (o == null) {
            return 0;
        }
        return o.hashCode();
    }
    
    public void setId(final int id) {
        this._id = id;
    }
}
