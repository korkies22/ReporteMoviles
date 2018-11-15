/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.ui.list.UpdatingList;
import java.util.Collection;
import java.util.List;

class TournamentListFragment
implements Runnable {
    final /* synthetic */ List val$result;

    TournamentListFragment(List list) {
        this.val$result = list;
    }

    @Override
    public void run() {
        2.this.this$0._tournamentList.updateFinished();
        if (this.val$result == null || this.val$result.size() == 0) {
            2.this.this$0._tournamentList.disableFooter();
        }
        2.this.this$0.addAllTournamentInfos(this.val$result, false);
    }
}
