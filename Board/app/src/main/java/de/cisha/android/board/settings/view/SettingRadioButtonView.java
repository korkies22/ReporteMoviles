// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings.view;

import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class SettingRadioButtonView extends RelativeLayout
{
    private RadioButton _radioButton;
    
    public SettingRadioButtonView(final Context context) {
        super(context);
        this.init();
    }
    
    public SettingRadioButtonView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        LayoutInflater.from(this.getContext()).inflate(2131427538, (ViewGroup)this, true);
        (this._radioButton = (RadioButton)this.findViewById(2131296972)).setClickable(false);
        this._radioButton.setBackgroundResource(0);
    }
    
    public RadioButton getRadioButton() {
        return this._radioButton;
    }
    
    public boolean isChecked() {
        return this._radioButton.isChecked();
    }
    
    public void setDescription(final CharSequence text) {
        ((TextView)this.findViewById(2131296971)).setText(text);
    }
    
    public void setTitle(final CharSequence text) {
        ((TextView)this.findViewById(2131296973)).setText(text);
    }
    
    public void toggle() {
        this._radioButton.setChecked(this._radioButton.isChecked() ^ true);
    }
}
