// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public interface GameStateObservable
{
    void addGameStateObserver(final GameStateObserver p0);
    
    GameStatus getGameStatus();
    
    void removeGameStateObserver(final GameStateObserver p0);
}
