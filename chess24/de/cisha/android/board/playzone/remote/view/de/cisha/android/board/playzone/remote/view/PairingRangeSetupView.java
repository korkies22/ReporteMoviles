/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import de.cisha.android.board.playzone.remote.view.EloMarkDrawable;
import de.cisha.android.ui.view.RangeSeekBar;

public class PairingRangeSetupView
extends LinearLayout
implements SeekBar.OnSeekBarChangeListener,
RangeSeekBar.OnRangeSeekBarChangeListener<Integer> {
    private int _avgValue;
    private SeekBar _eloRangeTimeIndicatorBar;
    private int _maxValue;
    private int _minValue;
    private Paint _paint;
    private RangeSeekBar<Integer> _rangeSeekBar;
    private TextView _textViewRangeMax;
    private TextView _textViewRangeMin;

    public PairingRangeSetupView(Context context) {
        super(context);
        this.init();
    }

    public PairingRangeSetupView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private static String getFormattedStringFor(Integer n) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = n >= 0 ? "+" : "";
        stringBuilder.append(string);
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    private void init() {
        this.setOrientation(1);
        PairingRangeSetupView.inflate((Context)this.getContext(), (int)2131427482, (ViewGroup)this);
        this._rangeSeekBar = new RangeSeekBar<Integer>(-500, 500, this.getContext());
        this._rangeSeekBar.setOnRangeSeekBarChangeListener(this);
        this._rangeSeekBar.setNotifyWhileDragging(true);
        ((ViewGroup)this.findViewById(2131296652)).addView(this._rangeSeekBar);
        this._textViewRangeMin = (TextView)this.findViewById(2131296656);
        this._textViewRangeMax = (TextView)this.findViewById(2131296655);
        this._eloRangeTimeIndicatorBar = (SeekBar)this.findViewById(2131296648);
        this._eloRangeTimeIndicatorBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)this);
        this._eloRangeTimeIndicatorBar.setMax(1000);
        this.onRangeSeekBarValuesChanged(this._rangeSeekBar, this._rangeSeekBar.getSelectedMinValue(), this._rangeSeekBar.getSelectedMaxValue());
        this.findViewById(2131296653).setBackgroundDrawable((Drawable)new EloMarkDrawable());
    }

    private void updateRangeTextViews(int n, int n2) {
        this._minValue = n;
        this._maxValue = n2;
        this._textViewRangeMin.setText((CharSequence)PairingRangeSetupView.getFormattedStringFor(n));
        this._textViewRangeMax.setText((CharSequence)PairingRangeSetupView.getFormattedStringFor(n2));
    }

    public int getMaxValue() {
        return this._maxValue;
    }

    public int getMinValue() {
        return this._minValue;
    }

    public int getSelectedMaxRating() {
        return this._rangeSeekBar.getSelectedMaxValue();
    }

    public int getSelectedMinRating() {
        return this._rangeSeekBar.getSelectedMinValue();
    }

    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
        if (bl) {
            int n2 = n / 2;
            int n3 = this._avgValue;
            int n4 = this._rangeSeekBar.getAbsoluteMaxValue();
            n = 0;
            n3 = Math.max(n3 + n2 - n4, 0);
            n4 = this._avgValue - n2 - this._rangeSeekBar.getAbsoluteMinValue();
            if (n4 < 0) {
                n = - n4;
            }
            n4 = this._avgValue;
            int n5 = this._avgValue;
            this._rangeSeekBar.setSelectedMinValue(n4 - n2 - n3);
            this._rangeSeekBar.setSelectedMaxValue(n5 + n2 + n);
            this.updateRangeTextViews(this._rangeSeekBar.getSelectedMinValue(), this._rangeSeekBar.getSelectedMaxValue());
        }
    }

    @Override
    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer n, Integer n2) {
        this._avgValue = (n + n2) / 2;
        int n3 = n2;
        int n4 = n;
        this._eloRangeTimeIndicatorBar.setProgress(n3 - n4);
        this.updateRangeTextViews(n, n2);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void setEloRange(int n, int n2) {
        this._rangeSeekBar.setSelectedMinValue(n);
        this._rangeSeekBar.setSelectedMaxValue(n2);
        this.updateRangeTextViews(n, n2);
        this.onRangeSeekBarValuesChanged(this._rangeSeekBar, this._rangeSeekBar.getSelectedMinValue(), this._rangeSeekBar.getSelectedMaxValue());
    }
}
