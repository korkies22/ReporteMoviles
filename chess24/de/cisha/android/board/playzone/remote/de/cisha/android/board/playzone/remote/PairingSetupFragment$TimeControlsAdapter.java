/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.view.PairingRangeSetupView;
import de.cisha.android.board.playzone.view.TimeControlView;
import de.cisha.chess.model.ClockSetting;
import java.util.List;

private class PairingSetupFragment.TimeControlsAdapter
extends RecyclerView.Adapter<TimeControlViewHolder> {
    private PairingSetupFragment.TimeControlsAdapter() {
    }

    @Override
    public int getItemCount() {
        if (PairingSetupFragment.this._availableClocks != null) {
            return PairingSetupFragment.this._availableClocks.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(TimeControlViewHolder timeControlViewHolder, int n) {
        if (PairingSetupFragment.this._availableClocks != null) {
            final TimeControl timeControl = (TimeControl)PairingSetupFragment.this._availableClocks.get(n);
            timeControlViewHolder._timeControl.setTimeControlValue(timeControl.getMinutes(), timeControl.getIncrement());
            ClockSetting.GameClockType gameClockType = new ClockSetting(timeControl.getMinutes() * 60, timeControl.getMinutes()).getClockType();
            timeControlViewHolder._timeControlType.setText((CharSequence)gameClockType.getString(PairingSetupFragment.this.getResources()));
            timeControlViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View object) {
                    object = new EloRange(PairingSetupFragment.this._eloRangeView.getMinValue(), PairingSetupFragment.this._eloRangeView.getMaxValue());
                    object = PlayzoneRemoteFragment.createFragmentWithTimeControl(timeControl, (EloRange)object);
                    PairingSetupFragment.this._contentPresenter.showFragment((AbstractContentFragment)object, true, true);
                }
            });
        }
    }

    @Override
    public TimeControlViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new TimeControlViewHolder(LayoutInflater.from((Context)PairingSetupFragment.this.getActivity()).inflate(2131427483, viewGroup, false));
    }

    public class TimeControlViewHolder
    extends RecyclerView.ViewHolder {
        private TimeControlView _timeControl;
        private TextView _timeControlType;

        public TimeControlViewHolder(View view) {
            super(view);
            this._timeControl = (TimeControlView)view.findViewById(2131296658);
            this._timeControlType = (TextView)view.findViewById(2131296657);
        }
    }

}
