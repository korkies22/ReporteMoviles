/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.ClockSetting;

class PlayzoneRemoteFragment
implements Runnable {
    final /* synthetic */ TimeControl val$timeControl;

    PlayzoneRemoteFragment(TimeControl timeControl) {
        this.val$timeControl = timeControl;
    }

    @Override
    public void run() {
        Object object = ServiceProvider.getInstance().getLoginService().getAuthToken();
        object = new RemoteGameAdapter.Builder(new ClockSetting(this.val$timeControl.getMinutes() * 60, this.val$timeControl.getIncrement()), (CishaUUID)object, (IGameModelDelegate)PlayzoneRemoteFragment.this, PlayzoneRemoteFragment.this._pairingServerAddress);
        if (PlayzoneRemoteFragment.this._eloRange != null) {
            object.setEloRange(PlayzoneRemoteFragment.this._eloRange);
        }
        PlayzoneRemoteFragment.this._model = object.build();
        PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, true);
        PlayzoneRemoteFragment.this.showWaitingScreenView(PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_SEARCHING);
        PlayzoneRemoteFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
    }
}
