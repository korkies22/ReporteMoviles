/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SpeedChooserController {
    private Drawable _drawable;
    private TextView _headlineText;
    private SpeedChooseListener _listener;
    private TextView _textView;
    private String[] _texts;
    private View _view;

    public SpeedChooserController(Context context, int n, int n2, int n3) {
        this._view = LayoutInflater.from((Context)context).inflate(2131427534, null);
        this._texts = context.getResources().getStringArray(n3);
        this._textView = (TextView)this._view.findViewById(2131296957);
        this._drawable = context.getResources().getDrawable(n2);
        n2 = (int)TypedValue.applyDimension((int)1, (float)5.0f, (DisplayMetrics)context.getResources().getDisplayMetrics());
        this._textView.setCompoundDrawablesWithIntrinsicBounds(this._drawable, null, null, null);
        this._textView.setCompoundDrawablePadding(n2);
        this._headlineText = (TextView)this._view.findViewById(2131296976);
        this._headlineText.setText(n);
        this._view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SpeedChooserController.this.goToNextSpeedLevel();
            }
        });
    }

    private void goToNextSpeedLevel() {
        int n = this._drawable.getLevel();
        n = n < this._texts.length - 1 ? ++n : 0;
        if (this._listener != null) {
            this._listener.onSpeedChoosen(n);
        }
        this.setChoosenSpeedLevel(n);
    }

    public View getView() {
        return this._view;
    }

    public void setChoosenSpeedLevel(int n) {
        if (n < this._texts.length) {
            this._drawable.setLevel(n);
            this._textView.setText((CharSequence)this._texts[n]);
        }
    }

    public void setOnSpeedChooseListener(SpeedChooseListener speedChooseListener) {
        this._listener = speedChooseListener;
    }

    public static interface SpeedChooseListener {
        public void onSpeedChoosen(int var1);
    }

}
