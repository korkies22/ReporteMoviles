/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.playzone.remote;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;
import java.util.List;

class PairingSetupFragment
implements Runnable {
    final /* synthetic */ List val$result;

    PairingSetupFragment(List list) {
        this.val$result = list;
    }

    @Override
    public void run() {
        2.this.this$0._availableClocks = this.val$result;
        2.this.this$0._recyclerView.setVisibility(0);
        2.this.this$0._timeControlLoadingView.setVisibility(8);
        2.this.this$0._timeControlsAdapter.notifyDataSetChanged();
    }
}
