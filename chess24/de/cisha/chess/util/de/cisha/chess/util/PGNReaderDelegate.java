/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.model.PGNGame;

public interface PGNReaderDelegate {
    public void addPGNGame(PGNGame var1);

    public void finishedReadingPGN();
}
