/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.profile;

import android.view.View;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.broadcast.actions.ShowTournamentDetailsAction;
import de.cisha.android.board.broadcast.model.TournamentInfo;

class TournamentsWidgetController
implements View.OnClickListener {
    final /* synthetic */ TournamentInfo val$info;

    TournamentsWidgetController(TournamentInfo tournamentInfo) {
        this.val$info = tournamentInfo;
    }

    public void onClick(View view) {
        new ShowTournamentDetailsAction(TournamentsWidgetController.this.getContentPresenter(), this.val$info).perform();
    }
}
