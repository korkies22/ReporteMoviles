/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.engine.EngineOnlineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;

class EngineOnlineOpponentChooserViewFragment
implements BoardAction {
    final /* synthetic */ PlayzoneGameSessionInfo val$sessionInfo;

    EngineOnlineOpponentChooserViewFragment(PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.val$sessionInfo = playzoneGameSessionInfo;
    }

    @Override
    public void perform() {
        if (3.this.this$0._listener != null) {
            3.this.this$0._listener.resumeEngineOnlineGame(this.val$sessionInfo);
        }
    }
}
