/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.chess.model.Opponent;

class EngineOnlineOpponentChooserViewFragment
implements PlayzoneService.BinderCallback {
    EngineOnlineOpponentChooserViewFragment() {
    }

    @Override
    public void onServiceBinded(GameBackgroundHolderService object) {
        Object object2 = object.getRunningGameAdapter(PlayzoneRemoteFragment.class.getName());
        if (object2 != null && object2.isGameActive()) {
            object = object2.getGameSessionInfo();
            object2.getChessClock().addOnClockListener(EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView);
            object2 = object2.getOpponent().getName();
            EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setResumeGameEnabled((String)object2, new BoardAction((PlayzoneGameSessionInfo)object){
                final /* synthetic */ PlayzoneGameSessionInfo val$sessionInfo;
                {
                    this.val$sessionInfo = playzoneGameSessionInfo;
                }

                @Override
                public void perform() {
                    if (EngineOnlineOpponentChooserViewFragment.this._listener != null) {
                        EngineOnlineOpponentChooserViewFragment.this._listener.resumeEngineOnlineGame(this.val$sessionInfo);
                    }
                }
            });
            return;
        }
        object.stopServiceIfNoGameActive();
    }

}
