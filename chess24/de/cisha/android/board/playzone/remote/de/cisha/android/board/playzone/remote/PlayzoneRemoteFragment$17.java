/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.playzone.remote;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.remote.view.DisconnectedOpponentView;
import de.cisha.android.board.playzone.remote.view.FlyInOutView;

class PlayzoneRemoteFragment
implements Runnable {
    final /* synthetic */ boolean val$abort;
    final /* synthetic */ boolean val$connected;
    final /* synthetic */ int val$timeOut;

    PlayzoneRemoteFragment(boolean bl, boolean bl2, int n) {
        this.val$connected = bl;
        this.val$abort = bl2;
        this.val$timeOut = n;
    }

    @Override
    public void run() {
        LinearLayout linearLayout = (FlyInOutView)PlayzoneRemoteFragment.this._fragmentView.findViewById(2131296718);
        linearLayout.bringToFront();
        if (PlayzoneRemoteFragment.this._model.isGameActive()) {
            if (this.val$connected) {
                linearLayout.flyOutToBottom();
                return;
            }
            linearLayout.flyInFromBottom();
            linearLayout = (DisconnectedOpponentView)linearLayout.findViewById(2131296717);
            linearLayout.setGameWillAborted(this.val$abort);
            linearLayout.setTimeout(this.val$timeOut / 1000);
            return;
        }
        linearLayout.setVisibility(8);
    }
}
