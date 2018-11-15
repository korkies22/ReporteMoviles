/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.view.RookieResumeGameDialogFragment;
import de.cisha.android.board.service.ILocalGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.chess.model.Game;

class PlayzoneEngineFragment
implements RookieResumeGameDialogFragment.ResumeGameDialogListener {
    final /* synthetic */ StoredGameInformation val$gameInfo;
    final /* synthetic */ Game val$storedGame;

    PlayzoneEngineFragment(Game game, StoredGameInformation storedGameInformation) {
        this.val$storedGame = game;
        this.val$gameInfo = storedGameInformation;
    }

    @Override
    public void onDiscardClicked() {
        if (PlayzoneEngineFragment.this.getGameAdapter() != null) {
            if (PlayzoneEngineFragment.this.getGameAdapter().isAbortable()) {
                PlayzoneEngineFragment.this.getGameAdapter().requestAbort();
            } else {
                PlayzoneEngineFragment.this.getGameAdapter().resignGame();
            }
        }
        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
    }

    @Override
    public void onResumeClicked() {
        PlayzoneEngineFragment.this.resumeGame(this.val$storedGame, this.val$gameInfo.isPlayerWhite());
    }
}
