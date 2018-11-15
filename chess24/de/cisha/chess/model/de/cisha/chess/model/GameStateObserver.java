/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.GameStatus;

public interface GameStateObserver {
    public void gameStateChanged(GameStatus var1);
}
