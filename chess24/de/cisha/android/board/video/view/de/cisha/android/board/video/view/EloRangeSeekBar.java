/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.android.ui.view.RangeSeekBar;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EloRangeSeekBar
extends LinearLayout {
    private int _maxValue;
    private int _minValue;
    private List<Integer> _possibleValues;
    private RangeSeekBar<Integer> _rangeSeekBar;
    private TextView _textLeft;
    private TextView _textRight;

    public EloRangeSeekBar(Context context) {
        super(context);
        this.initView();
    }

    public EloRangeSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private int getNearestPossibleValue(int n) {
        int n2 = this._possibleValues.get(0);
        Iterator<Integer> iterator = this._possibleValues.iterator();
        int n3 = 10000;
        while (iterator.hasNext()) {
            Integer n4 = iterator.next();
            int n5 = Math.abs(n4 - n);
            if (n5 >= n3) continue;
            n2 = n4;
            n3 = n5;
        }
        return n2;
    }

    private void initView() {
        this.setOrientation(0);
        this.setGravity(16);
        this._rangeSeekBar = new RangeSeekBar<Integer>(1200, 2200, this.getContext());
        TextView textView = this._textLeft = new CustomTextView(this.getContext(), CustomTextViewTextStyle.TEXT_BOLD, null);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._minValue);
        stringBuilder.append("");
        textView.setText((CharSequence)stringBuilder.toString());
        this.addView((View)this._textLeft);
        textView = new LinearLayout.LayoutParams(0, -2);
        textView.weight = 1.0f;
        this.addView(this._rangeSeekBar, (ViewGroup.LayoutParams)textView);
        textView = this._textRight = new CustomTextView(this.getContext(), CustomTextViewTextStyle.TEXT_BOLD, null);
        stringBuilder = new StringBuilder();
        stringBuilder.append(this._maxValue);
        stringBuilder.append("");
        textView.setText((CharSequence)stringBuilder.toString());
        this.addView((View)this._textRight);
        this._rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>(){

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer n, Integer n2) {
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

    public void setMinMaxEloNumber(Integer n, Integer serializable) {
        this._minValue = this.getNearestPossibleValue(n);
        n = this._textLeft;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._minValue);
        stringBuilder.append("");
        n.setText((CharSequence)stringBuilder.toString());
        this._rangeSeekBar.setSelectedMinValue(this._minValue);
        this._maxValue = this.getNearestPossibleValue(serializable.intValue());
        n = this._textRight;
        serializable = new StringBuilder();
        serializable.append(this._maxValue);
        serializable.append("");
        n.setText((CharSequence)serializable.toString());
        this._rangeSeekBar.setSelectedMaxValue(this._maxValue);
    }

    public void setPossibleValueList(List<Integer> list) {
        this._possibleValues = list;
        if (list.size() > 0) {
            this._minValue = list.get(0);
            this._maxValue = list.get(list.size() - 1);
            this.setMinMaxEloNumber(this._minValue, this._maxValue);
        }
    }

}
