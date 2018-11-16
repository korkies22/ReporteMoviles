// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import de.cisha.chess.model.PGNGame;

public interface PGNReaderDelegate
{
    void addPGNGame(final PGNGame p0);
    
    void finishedReadingPGN();
}
