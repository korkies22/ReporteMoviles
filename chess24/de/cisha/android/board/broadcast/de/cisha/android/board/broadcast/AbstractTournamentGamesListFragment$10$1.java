/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.AbstractTournamentGamesListFragment;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import java.util.Collection;
import java.util.Iterator;

class AbstractTournamentGamesListFragment
implements Runnable {
    AbstractTournamentGamesListFragment() {
    }

    @Override
    public void run() {
        Iterator iterator = 10.this.this$0._mapIdView.values().iterator();
        while (iterator.hasNext()) {
            ((TournamentGameInfoView)((Object)iterator.next())).updateRemainingTimes();
        }
    }
}
