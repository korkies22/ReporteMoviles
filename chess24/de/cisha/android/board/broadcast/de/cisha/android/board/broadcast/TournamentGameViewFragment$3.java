/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.res.Resources
 */
package de.cisha.android.board.broadcast;

import android.content.Intent;
import android.content.res.Resources;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;

class TournamentGameViewFragment
implements Runnable {
    TournamentGameViewFragment() {
    }

    @Override
    public void run() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", TournamentGameViewFragment.this._shareLink);
        intent.putExtra("android.intent.extra.SUBJECT", TournamentGameViewFragment.this.getResources().getString(2131690296));
        intent.setType("text/plain");
        TournamentGameViewFragment.this.startActivity(Intent.createChooser((Intent)intent, (CharSequence)TournamentGameViewFragment.this.getResources().getString(2131690072)));
        ServiceProvider.getInstance().getTrackingService().trackScreenOpen("TournamentGame: ShareDialog");
    }
}
