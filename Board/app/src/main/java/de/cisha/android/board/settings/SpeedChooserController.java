// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import android.view.View.OnClickListener;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

public class SpeedChooserController
{
    private Drawable _drawable;
    private TextView _headlineText;
    private SpeedChooseListener _listener;
    private TextView _textView;
    private String[] _texts;
    private View _view;
    
    public SpeedChooserController(final Context context, final int text, int compoundDrawablePadding, final int n) {
        this._view = LayoutInflater.from(context).inflate(2131427534, (ViewGroup)null);
        this._texts = context.getResources().getStringArray(n);
        this._textView = (TextView)this._view.findViewById(2131296957);
        this._drawable = context.getResources().getDrawable(compoundDrawablePadding);
        compoundDrawablePadding = (int)TypedValue.applyDimension(1, 5.0f, context.getResources().getDisplayMetrics());
        this._textView.setCompoundDrawablesWithIntrinsicBounds(this._drawable, (Drawable)null, (Drawable)null, (Drawable)null);
        this._textView.setCompoundDrawablePadding(compoundDrawablePadding);
        (this._headlineText = (TextView)this._view.findViewById(2131296976)).setText(text);
        this._view.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                SpeedChooserController.this.goToNextSpeedLevel();
            }
        });
    }
    
    private void goToNextSpeedLevel() {
        final int level = this._drawable.getLevel();
        int choosenSpeedLevel;
        if (level < this._texts.length - 1) {
            choosenSpeedLevel = level + 1;
        }
        else {
            choosenSpeedLevel = 0;
        }
        if (this._listener != null) {
            this._listener.onSpeedChoosen(choosenSpeedLevel);
        }
        this.setChoosenSpeedLevel(choosenSpeedLevel);
    }
    
    public View getView() {
        return this._view;
    }
    
    public void setChoosenSpeedLevel(final int level) {
        if (level < this._texts.length) {
            this._drawable.setLevel(level);
            this._textView.setText((CharSequence)this._texts[level]);
        }
    }
    
    public void setOnSpeedChooseListener(final SpeedChooseListener listener) {
        this._listener = listener;
    }
    
    public interface SpeedChooseListener
    {
        void onSpeedChoosen(final int p0);
    }
}
