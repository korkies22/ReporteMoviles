// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.openingbook;

import java.util.List;
import de.cisha.chess.model.FEN;

public abstract class OpeningBook
{
    public abstract List<BookMove> getBookEntriesForFEN(final FEN p0);
}
