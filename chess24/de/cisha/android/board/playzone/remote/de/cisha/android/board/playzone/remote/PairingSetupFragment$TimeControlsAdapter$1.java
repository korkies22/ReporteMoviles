/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.remote;

import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.view.PairingRangeSetupView;

class PairingSetupFragment.TimeControlsAdapter
implements View.OnClickListener {
    final /* synthetic */ TimeControl val$timeControl;

    PairingSetupFragment.TimeControlsAdapter(TimeControl timeControl) {
        this.val$timeControl = timeControl;
    }

    public void onClick(View object) {
        object = new EloRange(TimeControlsAdapter.this.this$0._eloRangeView.getMinValue(), TimeControlsAdapter.this.this$0._eloRangeView.getMaxValue());
        object = PlayzoneRemoteFragment.createFragmentWithTimeControl(this.val$timeControl, (EloRange)object);
        TimeControlsAdapter.this.this$0._contentPresenter.showFragment((AbstractContentFragment)object, true, true);
    }
}
