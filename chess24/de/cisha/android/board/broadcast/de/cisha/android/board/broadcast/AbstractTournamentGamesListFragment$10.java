/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimerTask;

class AbstractTournamentGamesListFragment
extends TimerTask {
    AbstractTournamentGamesListFragment() {
    }

    @Override
    public void run() {
        if (!AbstractTournamentGamesListFragment.this._scrolling) {
            AbstractTournamentGamesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    Iterator iterator = AbstractTournamentGamesListFragment.this._mapIdView.values().iterator();
                    while (iterator.hasNext()) {
                        ((TournamentGameInfoView)((Object)iterator.next())).updateRemainingTimes();
                    }
                }
            });
        }
    }

}
