// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.Date;
import java.io.Serializable;

public class Tournament extends BaseObject implements Serializable
{
    private static final long serialVersionUID = 1078784557434549519L;
    private Date _endDate;
    private String _name;
    private Date _startDate;
    
    public Tournament() {
    }
    
    public Tournament(final int n) {
        super(n);
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(final String name) {
        this._name = name;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Name:");
        sb.append(this._name);
        sb.append(" id:");
        sb.append(this.getId());
        return sb.toString();
    }
}
