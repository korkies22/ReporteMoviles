// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings.view;

import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

public class SettingCheckBoxView extends RelativeLayout
{
    private CheckBox _checkBox;
    
    public SettingCheckBoxView(final Context context) {
        super(context);
        this.init();
    }
    
    public SettingCheckBoxView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427540, (ViewGroup)this);
        this._checkBox = (CheckBox)this.findViewById(2131296970);
        this.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                SettingCheckBoxView.this._checkBox.toggle();
            }
        });
    }
    
    public CheckBox getCheckBox() {
        return this._checkBox;
    }
    
    public void setDescription(final CharSequence text) {
        ((TextView)this.findViewById(2131296971)).setText(text);
    }
    
    public void setTitle(final CharSequence text) {
        ((TextView)this.findViewById(2131296973)).setText(text);
    }
}
