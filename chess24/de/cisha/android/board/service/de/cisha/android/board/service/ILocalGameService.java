/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.chess.model.Game;

public interface ILocalGameService {
    public void clearGameStorage();

    public StoredGameInformation loadGame();

    public void storeGameLocally(Game var1, boolean var2, long var3);
}
