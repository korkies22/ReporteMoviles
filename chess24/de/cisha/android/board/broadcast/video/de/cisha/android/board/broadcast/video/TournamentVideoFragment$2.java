/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast.video;

import android.view.View;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;

class TournamentVideoFragment
implements View.OnClickListener {
    final /* synthetic */ TournamentVideoInformation val$video;

    TournamentVideoFragment(TournamentVideoInformation tournamentVideoInformation) {
        this.val$video = tournamentVideoInformation;
    }

    public void onClick(View view) {
        TournamentVideoFragment.this.selectVideo(this.val$video);
    }
}
