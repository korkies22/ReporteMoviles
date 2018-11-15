/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.profile.TournamentsWidgetController;
import java.util.List;

class TournamentsWidgetController
implements Runnable {
    final /* synthetic */ List val$result;

    TournamentsWidgetController(List list) {
        this.val$result = list;
    }

    @Override
    public void run() {
        2.this.this$0.updateTournaments(this.val$result);
    }
}
