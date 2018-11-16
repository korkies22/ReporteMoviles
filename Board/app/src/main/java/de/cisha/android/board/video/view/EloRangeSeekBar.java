// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import java.util.Arrays;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import java.util.Iterator;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import de.cisha.android.ui.view.RangeSeekBar;
import java.util.List;
import android.widget.LinearLayout;

public class EloRangeSeekBar extends LinearLayout
{
    private int _maxValue;
    private int _minValue;
    private List<Integer> _possibleValues;
    private RangeSeekBar<Integer> _rangeSeekBar;
    private TextView _textLeft;
    private TextView _textRight;
    
    public EloRangeSeekBar(final Context context) {
        super(context);
        this.initView();
    }
    
    public EloRangeSeekBar(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView();
    }
    
    private int getNearestPossibleValue(final int n) {
        int n2 = this._possibleValues.get(0);
        final Iterator<Integer> iterator = this._possibleValues.iterator();
        int n3 = 10000;
        while (iterator.hasNext()) {
            final Integer n4 = iterator.next();
            final int abs = Math.abs(n4 - n);
            if (abs < n3) {
                n2 = n4;
                n3 = abs;
            }
        }
        return n2;
    }
    
    private void initView() {
        this.setOrientation(0);
        this.setGravity(16);
        this._rangeSeekBar = new RangeSeekBar<Integer>(1200, 2200, this.getContext());
        this._textLeft = new CustomTextView(this.getContext(), CustomTextViewTextStyle.TEXT_BOLD, null);
        final TextView textLeft = this._textLeft;
        final StringBuilder sb = new StringBuilder();
        sb.append(this._minValue);
        sb.append("");
        textLeft.setText((CharSequence)sb.toString());
        this.addView((View)this._textLeft);
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(0, -2);
        linearLayout.LayoutParams.weight = 1.0f;
        this.addView((View)this._rangeSeekBar, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
        this._textRight = new CustomTextView(this.getContext(), CustomTextViewTextStyle.TEXT_BOLD, null);
        final TextView textRight = this._textRight;
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(this._maxValue);
        sb2.append("");
        textRight.setText((CharSequence)sb2.toString());
        this.addView((View)this._textRight);
        this._rangeSeekBar.setOnRangeSeekBarChangeListener((RangeSeekBar.OnRangeSeekBarChangeListener<Integer>)new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            public void onRangeSeekBarValuesChanged(final RangeSeekBar<?> rangeSeekBar, final Integer n, final Integer n2) {
                EloRangeSeekBar.this.setMinMaxEloNumber(n, n2);
            }
        });
        this._rangeSeekBar.setNotifyWhileDragging(false);
        this.setPossibleValueList(Arrays.asList(1, 2, 3, 4));
    }
    
    public int getMaxEloNumber() {
        return this._maxValue;
    }
    
    public int getMinEloNumber() {
        return this._minValue;
    }
    
    public void setMinMaxEloNumber(final Integer n, final Integer n2) {
        this._minValue = this.getNearestPossibleValue(n);
        final TextView textLeft = this._textLeft;
        final StringBuilder sb = new StringBuilder();
        sb.append(this._minValue);
        sb.append("");
        textLeft.setText((CharSequence)sb.toString());
        this._rangeSeekBar.setSelectedMinValue(this._minValue);
        this._maxValue = this.getNearestPossibleValue(n2);
        final TextView textRight = this._textRight;
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(this._maxValue);
        sb2.append("");
        textRight.setText((CharSequence)sb2.toString());
        this._rangeSeekBar.setSelectedMaxValue(this._maxValue);
    }
    
    public void setPossibleValueList(final List<Integer> possibleValues) {
        this._possibleValues = possibleValues;
        if (possibleValues.size() > 0) {
            this._minValue = possibleValues.get(0);
            this._maxValue = possibleValues.get(possibleValues.size() - 1);
            this.setMinMaxEloNumber(this._minValue, this._maxValue);
        }
    }
}
