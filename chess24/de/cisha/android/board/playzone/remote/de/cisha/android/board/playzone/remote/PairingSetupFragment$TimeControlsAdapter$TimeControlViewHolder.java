/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;
import de.cisha.android.board.playzone.view.TimeControlView;

public class PairingSetupFragment.TimeControlsAdapter.TimeControlViewHolder
extends RecyclerView.ViewHolder {
    private TimeControlView _timeControl;
    private TextView _timeControlType;

    public PairingSetupFragment.TimeControlsAdapter.TimeControlViewHolder(View view) {
        super(view);
        this._timeControl = (TimeControlView)view.findViewById(2131296658);
        this._timeControlType = (TextView)view.findViewById(2131296657);
    }

    static /* synthetic */ TimeControlView access$600(PairingSetupFragment.TimeControlsAdapter.TimeControlViewHolder timeControlViewHolder) {
        return timeControlViewHolder._timeControl;
    }

    static /* synthetic */ TextView access$700(PairingSetupFragment.TimeControlsAdapter.TimeControlViewHolder timeControlViewHolder) {
        return timeControlViewHolder._timeControlType;
    }
}
