/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.openingbook;

import de.cisha.chess.model.FEN;
import de.cisha.chess.openingbook.BookMove;
import java.util.List;

public abstract class OpeningBook {
    public abstract List<BookMove> getBookEntriesForFEN(FEN var1);
}
