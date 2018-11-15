/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.service.PlayzoneService;

class PlayzoneRemoteFragment
implements PlayzoneService.BinderCallback {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void onServiceBinded(GameBackgroundHolderService gameBackgroundHolderService) {
        PlayzoneRemoteFragment.this._gameHolderService = gameBackgroundHolderService;
        PlayzoneRemoteFragment.this._model = PlayzoneRemoteFragment.this._gameHolderService.getRunningGameAdapter(PlayzoneRemoteFragment.this.getClass().getName());
        if (PlayzoneRemoteFragment.this._model != null) {
            PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, false);
            PlayzoneRemoteFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
        } else if (!PlayzoneRemoteFragment.this.hasRunningGame()) {
            if (PlayzoneRemoteFragment.this._gameSessionInfo != null && PlayzoneRemoteFragment.this.getGameAdapter() == null) {
                PlayzoneRemoteFragment.this.resumeGame(PlayzoneRemoteFragment.this._gameSessionInfo);
            } else {
                PlayzoneRemoteFragment.this.loadOpenGameSessions();
            }
        }
        PlayzoneRemoteFragment.this.loadAvailableTimes();
        PlayzoneRemoteFragment.this.loadServerAddresses();
        PlayzoneRemoteFragment.this._gameHolderService.setNotifyInTitleBar(PlayzoneRemoteFragment.this.getClass().getName(), false);
    }
}
