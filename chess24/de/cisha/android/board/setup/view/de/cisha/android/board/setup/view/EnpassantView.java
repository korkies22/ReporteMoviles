/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$LayoutParams
 */
package de.cisha.android.board.setup.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import de.cisha.android.ui.patterns.input.CustomRadioButton;
import de.cisha.chess.model.Square;

public class EnpassantView
extends RadioGroup {
    private static LinearLayout.LayoutParams _defaultParams;
    private EnpassantListener _listener;

    public EnpassantView(Context context) {
        super(context);
        this.init();
    }

    public EnpassantView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        int n;
        this.setOrientation(1);
        _defaultParams = new LinearLayout.LayoutParams(-1, -2);
        EnpassantView._defaultParams.bottomMargin = n = (int)(this.getResources().getDisplayMetrics().density * 5.0f);
        EnpassantView._defaultParams.topMargin = n;
        EnpassantView._defaultParams.leftMargin = n;
        EnpassantView._defaultParams.rightMargin = n;
    }

    public void addEnpassant(final Square square, boolean bl) {
        CustomRadioButton customRadioButton = new CustomRadioButton(this.getContext());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(square.getName());
        customRadioButton.setText((CharSequence)stringBuilder.toString());
        customRadioButton.setChecked(bl);
        customRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (EnpassantView.this._listener != null && bl) {
                    EnpassantView.this._listener.enpassantChanged(square);
                }
            }
        });
        this.addView((View)customRadioButton, (ViewGroup.LayoutParams)new RadioGroup.LayoutParams((ViewGroup.MarginLayoutParams)_defaultParams));
        square = new ImageView(this.getContext());
        square.setImageResource(2131231123);
        square.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView((View)square, -1, -2);
    }

    public void addNoEnpassantOption(boolean bl) {
        CustomRadioButton customRadioButton = new CustomRadioButton(this.getContext());
        customRadioButton.setText(2131690285);
        customRadioButton.setChecked(bl);
        customRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (EnpassantView.this._listener != null && bl) {
                    EnpassantView.this._listener.enpassantChanged(null);
                }
            }
        });
        this.addView((View)customRadioButton, (ViewGroup.LayoutParams)new RadioGroup.LayoutParams((ViewGroup.MarginLayoutParams)_defaultParams));
    }

    public void setEnpassantListener(EnpassantListener enpassantListener) {
        this._listener = enpassantListener;
    }

    public static interface EnpassantListener {
        public void enpassantChanged(Square var1);
    }

}
