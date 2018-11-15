/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.CishaUUID;

class PlayzoneRemoteFragment
implements Runnable {
    final /* synthetic */ PlayzoneGameSessionInfo val$sessionInfo;

    PlayzoneRemoteFragment(PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.val$sessionInfo = playzoneGameSessionInfo;
    }

    @Override
    public void run() {
        CishaUUID cishaUUID = ServiceProvider.getInstance().getLoginService().getAuthToken();
        PlayzoneRemoteFragment.this._model = new RemoteGameAdapter.Builder(cishaUUID, this.val$sessionInfo.getGameToken(), this.val$sessionInfo.getColor(), PlayzoneRemoteFragment.this, new NodeServerAddress(this.val$sessionInfo.getHost(), this.val$sessionInfo.getPort()), PlayzoneRemoteFragment.this._pairingServerAddress).build();
        PlayzoneRemoteFragment.this.showDialogWaiting(true, false, "", new RookieDialogLoading.OnCancelListener(){

            @Override
            public void onCancel() {
                if (PlayzoneRemoteFragment.this._model != null) {
                    PlayzoneRemoteFragment.this._model.destroy();
                }
            }
        });
        PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, true);
    }

}
