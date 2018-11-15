/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.AdapterHelper;

static class AdapterHelper.UpdateOp {
    static final int ADD = 1;
    static final int MOVE = 8;
    static final int POOL_SIZE = 30;
    static final int REMOVE = 2;
    static final int UPDATE = 4;
    int cmd;
    int itemCount;
    Object payload;
    int positionStart;

    AdapterHelper.UpdateOp(int n, int n2, int n3, Object object) {
        this.cmd = n;
        this.positionStart = n2;
        this.itemCount = n3;
        this.payload = object;
    }

    String cmdToString() {
        int n = this.cmd;
        if (n != 4) {
            if (n != 8) {
                switch (n) {
                    default: {
                        return "??";
                    }
                    case 2: {
                        return "rm";
                    }
                    case 1: 
                }
                return "add";
            }
            return "mv";
        }
        return "up";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null) {
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (AdapterHelper.UpdateOp)object;
            if (this.cmd != object.cmd) {
                return false;
            }
            if (this.cmd == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == object.positionStart && this.positionStart == object.itemCount) {
                return true;
            }
            if (this.itemCount != object.itemCount) {
                return false;
            }
            if (this.positionStart != object.positionStart) {
                return false;
            }
            if (this.payload != null ? !this.payload.equals(object.payload) : object.payload != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 * (this.cmd * 31 + this.positionStart) + this.itemCount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("[");
        stringBuilder.append(this.cmdToString());
        stringBuilder.append(",s:");
        stringBuilder.append(this.positionStart);
        stringBuilder.append("c:");
        stringBuilder.append(this.itemCount);
        stringBuilder.append(",p:");
        stringBuilder.append(this.payload);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
