/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ServiceProvider;
import java.util.List;

class EngineOnlineOpponentChooserViewFragment
implements Runnable {
    EngineOnlineOpponentChooserViewFragment() {
    }

    @Override
    public void run() {
        final List<OnlineEngineOpponent> list = ServiceProvider.getInstance().getPlayzoneService().getOnlineEngineOpponents();
        EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setOnlineOpponents(list);
            }
        });
        EngineOnlineOpponentChooserViewFragment.this.loadOpponents();
    }

}
