/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.GameStateObserver;
import de.cisha.chess.model.GameStatus;

public interface GameStateObservable {
    public void addGameStateObserver(GameStateObserver var1);

    public GameStatus getGameStatus();

    public void removeGameStateObserver(GameStateObserver var1);
}
