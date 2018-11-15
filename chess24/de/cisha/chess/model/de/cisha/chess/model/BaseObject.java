/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import java.io.Serializable;

public abstract class BaseObject
implements Serializable {
    private static int nextId = 100000;
    private static final long serialVersionUID = 8705939552646099423L;
    private int _id;

    public BaseObject() {
        this._id = BaseObject.getNextid();
    }

    public BaseObject(int n) {
        this._id = n;
    }

    private static int getNextid() {
        synchronized (BaseObject.class) {
            int n = nextId;
            nextId = n + 1;
            return n;
        }
    }

    protected boolean equals(Object object, Object object2) {
        if (object == null && object2 == null) {
            return true;
        }
        if (object != null && object2 != null) {
            return object.equals(object2);
        }
        return false;
    }

    public int getId() {
        return this._id;
    }

    protected int hashcode(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    public void setId(int n) {
        this._id = n;
    }
}
