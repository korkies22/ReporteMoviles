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
        1.this.this$1.this$0.hideDialogWaiting();
        1.this.this$1.this$0.addAllTournamentInfos(this.val$result, true);
        1.this.this$1.this$0._tournamentList.updateFinished();
        1.this.this$1.this$0._listAdapter.notifyDataSetChanged();
    }
}
