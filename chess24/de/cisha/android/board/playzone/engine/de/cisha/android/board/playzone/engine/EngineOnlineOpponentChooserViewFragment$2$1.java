/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.playzone.engine.EngineOnlineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import java.util.List;

class EngineOnlineOpponentChooserViewFragment
implements Runnable {
    final /* synthetic */ List val$result;

    EngineOnlineOpponentChooserViewFragment(List list) {
        this.val$result = list;
    }

    @Override
    public void run() {
        2.this.this$0._engineOnlineOpponentView.setOnlineOpponents(this.val$result);
        2.this.this$0._refreshLayout.setRefreshing(false);
    }
}
