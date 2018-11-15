/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import de.cisha.android.board.broadcast.model.BroadcastGameHolder;

class TournamentGameViewFragment
implements View.OnClickListener {
    TournamentGameViewFragment() {
    }

    public void onClick(View view) {
        TournamentGameViewFragment.this.getGameHolder().enableLiveMode();
    }
}
