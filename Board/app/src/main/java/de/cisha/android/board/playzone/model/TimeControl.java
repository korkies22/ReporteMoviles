// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

public class TimeControl
{
    public static final TimeControl NO_TIME;
    private int _increment;
    private boolean _isDefault;
    private int _minutes;
    
    static {
        NO_TIME = new TimeControl(0, 0);
    }
    
    public TimeControl(final int minutes, final int increment) {
        this.setMinutes(minutes);
        this.setIncrement(increment);
        this._isDefault = false;
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = o instanceof TimeControl;
        boolean b3;
        final boolean b2 = b3 = false;
        if (b) {
            final TimeControl timeControl = (TimeControl)o;
            b3 = b2;
            if (timeControl.getMinutes() == this.getMinutes()) {
                b3 = b2;
                if (timeControl.getIncrement() == this.getIncrement()) {
                    b3 = true;
                }
            }
        }
        return b3;
    }
    
    public int getIncrement() {
        return this._increment;
    }
    
    public int getMinutes() {
        return this._minutes;
    }
    
    public boolean hasTimeControl() {
        return this._increment > 0 || this._minutes > 0;
    }
    
    @Override
    public int hashCode() {
        return this.getMinutes() * this.getIncrement() * (this.getMinutes() + this.getIncrement());
    }
    
    public boolean isDefault() {
        return this._isDefault;
    }
    
    public void setDefault(final boolean isDefault) {
        this._isDefault = isDefault;
    }
    
    public void setIncrement(final int increment) {
        this._increment = increment;
    }
    
    public void setMinutes(final int minutes) {
        this._minutes = minutes;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._minutes);
        sb.append("min + ");
        sb.append(this._increment);
        return sb.toString();
    }
}
