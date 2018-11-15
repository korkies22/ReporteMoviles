/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.remote;

import android.content.Intent;
import android.view.View;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

class PlayzoneRemoteFragment.PlayzoneWaitingScreen
implements View.OnClickListener {
    final /* synthetic */ PlayzoneRemoteFragment val$this$0;

    PlayzoneRemoteFragment.PlayzoneWaitingScreen(PlayzoneRemoteFragment playzoneRemoteFragment) {
        this.val$this$0 = playzoneRemoteFragment;
    }

    public void onClick(View view) {
        PlayzoneWaitingScreen.this.this$0.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
    }
}
