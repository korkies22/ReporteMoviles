// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.view;

import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RadioGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.cisha.android.ui.patterns.input.CustomRadioButton;
import de.cisha.chess.model.Square;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;

public class EnpassantView extends RadioGroup
{
    private static LinearLayout.LayoutParams _defaultParams;
    private EnpassantListener _listener;
    
    public EnpassantView(final Context context) {
        super(context);
        this.init();
    }
    
    public EnpassantView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        EnpassantView._defaultParams = new LinearLayout.LayoutParams(-1, -2);
        final int n = (int)(this.getResources().getDisplayMetrics().density * 5.0f);
        EnpassantView._defaultParams.bottomMargin = n;
        EnpassantView._defaultParams.topMargin = n;
        EnpassantView._defaultParams.leftMargin = n;
        EnpassantView._defaultParams.rightMargin = n;
    }
    
    public void addEnpassant(final Square square, final boolean checked) {
        final CustomRadioButton customRadioButton = new CustomRadioButton(this.getContext());
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(square.getName());
        customRadioButton.setText((CharSequence)sb.toString());
        customRadioButton.setChecked(checked);
        customRadioButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (EnpassantView.this._listener != null && b) {
                    EnpassantView.this._listener.enpassantChanged(square);
                }
            }
        });
        this.addView((View)customRadioButton, (ViewGroup.LayoutParams)new RadioGroup.LayoutParams((ViewGroup.MarginLayoutParams)EnpassantView._defaultParams));
        final ImageView imageView = new ImageView(this.getContext());
        imageView.setImageResource(2131231123);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView((View)imageView, -1, -2);
    }
    
    public void addNoEnpassantOption(final boolean checked) {
        final CustomRadioButton customRadioButton = new CustomRadioButton(this.getContext());
        customRadioButton.setText(2131690285);
        customRadioButton.setChecked(checked);
        customRadioButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (EnpassantView.this._listener != null && b) {
                    EnpassantView.this._listener.enpassantChanged(null);
                }
            }
        });
        this.addView((View)customRadioButton, (ViewGroup.LayoutParams)new RadioGroup.LayoutParams((ViewGroup.MarginLayoutParams)EnpassantView._defaultParams));
    }
    
    public void setEnpassantListener(final EnpassantListener listener) {
        this._listener = listener;
    }
    
    public interface EnpassantListener
    {
        void enpassantChanged(final Square p0);
    }
}
