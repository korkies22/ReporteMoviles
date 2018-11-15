/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast.standings.view;

import android.view.View;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;

class TournamentMatchInfoView
implements View.OnClickListener {
    final /* synthetic */ TournamentGameInfo val$gameInfo;

    TournamentMatchInfoView(TournamentGameInfo tournamentGameInfo) {
        this.val$gameInfo = tournamentGameInfo;
    }

    public void onClick(View view) {
        TournamentMatchInfoView.this.gameSelected(this.val$gameInfo);
    }
}
