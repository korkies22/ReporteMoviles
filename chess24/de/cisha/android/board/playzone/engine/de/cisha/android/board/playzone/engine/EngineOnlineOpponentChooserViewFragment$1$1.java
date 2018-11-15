/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.engine.EngineOnlineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import java.util.List;

class EngineOnlineOpponentChooserViewFragment
implements Runnable {
    final /* synthetic */ List val$onlineEngineOpponents;

    EngineOnlineOpponentChooserViewFragment(List list) {
        this.val$onlineEngineOpponents = list;
    }

    @Override
    public void run() {
        1.this.this$0._engineOnlineOpponentView.setOnlineOpponents(this.val$onlineEngineOpponents);
    }
}
