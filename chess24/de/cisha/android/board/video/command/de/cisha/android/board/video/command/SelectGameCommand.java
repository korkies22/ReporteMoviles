/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;

public class SelectGameCommand
extends TimedCommand {
    private int _gameIndex;
    private int _moveId;

    public SelectGameCommand(int n, int n2, long l) {
        super(l);
        this._gameIndex = n;
        this._moveId = n2;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        boolean bl;
        boolean bl2 = bl = videoCommandDelegate.selectGame(this._gameIndex);
        if (this._moveId != 0) {
            bl2 = bl && videoCommandDelegate.selectMove(this._moveId);
        }
        return bl2;
    }
}
