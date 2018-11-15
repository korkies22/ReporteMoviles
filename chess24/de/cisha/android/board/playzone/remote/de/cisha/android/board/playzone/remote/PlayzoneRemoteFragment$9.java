/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.playzone.remote;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

class PlayzoneRemoteFragment
implements Runnable {
    final /* synthetic */ PlayzoneRemoteFragment.WaitingScreenState val$state;

    PlayzoneRemoteFragment(PlayzoneRemoteFragment.WaitingScreenState waitingScreenState) {
        this.val$state = waitingScreenState;
    }

    @Override
    public void run() {
        if (PlayzoneRemoteFragment.this._waitingDialog == null) {
            PlayzoneRemoteFragment.this._waitingDialog = new PlayzoneRemoteFragment.PlayzoneWaitingScreen(PlayzoneRemoteFragment.this, (Context)PlayzoneRemoteFragment.this.getActivity());
            PlayzoneRemoteFragment.this._fragmentView.addView((View)PlayzoneRemoteFragment.this._waitingDialog, -1, -1);
        }
        PlayzoneRemoteFragment.this._waitingDialog.show(this.val$state);
    }
}
