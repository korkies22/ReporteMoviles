/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

public class TimeControl {
    public static final TimeControl NO_TIME = new TimeControl(0, 0);
    private int _increment;
    private boolean _isDefault;
    private int _minutes;

    public TimeControl(int n, int n2) {
        this.setMinutes(n);
        this.setIncrement(n2);
        this._isDefault = false;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof TimeControl;
        boolean bl3 = bl = false;
        if (bl2) {
            object = (TimeControl)object;
            bl3 = bl;
            if (object.getMinutes() == this.getMinutes()) {
                bl3 = bl;
                if (object.getIncrement() == this.getIncrement()) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    public int getIncrement() {
        return this._increment;
    }

    public int getMinutes() {
        return this._minutes;
    }

    public boolean hasTimeControl() {
        if (this._increment <= 0 && this._minutes <= 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.getMinutes() * this.getIncrement() * (this.getMinutes() + this.getIncrement());
    }

    public boolean isDefault() {
        return this._isDefault;
    }

    public void setDefault(boolean bl) {
        this._isDefault = bl;
    }

    public void setIncrement(int n) {
        this._increment = n;
    }

    public void setMinutes(int n) {
        this._minutes = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._minutes);
        stringBuilder.append("min + ");
        stringBuilder.append(this._increment);
        return stringBuilder.toString();
    }
}
