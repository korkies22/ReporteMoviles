/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.BaseObject;
import java.io.Serializable;
import java.util.Date;

public class Tournament
extends BaseObject
implements Serializable {
    private static final long serialVersionUID = 1078784557434549519L;
    private Date _endDate;
    private String _name;
    private Date _startDate;

    public Tournament() {
    }

    public Tournament(int n) {
        super(n);
    }

    public String getName() {
        return this._name;
    }

    public void setName(String string) {
        this._name = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name:");
        stringBuilder.append(this._name);
        stringBuilder.append(" id:");
        stringBuilder.append(this.getId());
        return stringBuilder.toString();
    }
}
