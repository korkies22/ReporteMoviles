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
import de.cisha.android.board.playzone.remote.view.DisconnectedDeviceView;
import de.cisha.android.board.playzone.remote.view.FlyInOutView;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.position.Position;

class PlayzoneRemoteFragment
implements Runnable {
    final /* synthetic */ boolean val$connected;

    PlayzoneRemoteFragment(boolean bl) {
        this.val$connected = bl;
    }

    @Override
    public void run() {
        LinearLayout linearLayout = (FlyInOutView)PlayzoneRemoteFragment.this._fragmentView.findViewById(2131296716);
        linearLayout.bringToFront();
        if (PlayzoneRemoteFragment.this._model != null && PlayzoneRemoteFragment.this._model.isGameActive()) {
            if (this.val$connected) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game disconnected", null);
                linearLayout.flyOutToBottom();
                return;
            }
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game reconnected", null);
            linearLayout.flyInFromBottom();
            linearLayout = (DisconnectedDeviceView)linearLayout.findViewById(2131296715);
            boolean bl = PlayzoneRemoteFragment.this._model.getPosition().getHalfMoveNumber() < 2;
            linearLayout.setGameWillAborted(bl);
            linearLayout.setTimeout(PlayzoneRemoteFragment.this._model.getConnectionTimeout(true) / 1000);
            return;
        }
        linearLayout.setVisibility(8);
    }
}
