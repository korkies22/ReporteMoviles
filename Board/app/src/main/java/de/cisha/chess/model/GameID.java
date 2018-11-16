// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;

public class GameID extends CishaUUID implements Serializable
{
    private static final long serialVersionUID = 4283290355304738590L;
    
    public GameID(final int n) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(n);
        super(sb.toString(), true);
    }
    
    public int getDBId() {
        try {
            return Integer.parseInt(this.getUuid());
        }
        catch (NumberFormatException ex) {
            return -1;
        }
    }
}
