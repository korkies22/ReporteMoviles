// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.openingbook;

import de.cisha.chess.model.SEP;

public abstract class BookMove
{
    public abstract int getBlackWins();
    
    public abstract int getDraws();
    
    public abstract int getNumberOfGames();
    
    public abstract SEP getSEP();
    
    public abstract int getWhiteWins();
}
