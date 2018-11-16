// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings.view;

import java.util.Iterator;
import android.view.View.OnClickListener;
import android.view.View;
import java.util.LinkedList;
import android.util.AttributeSet;
import android.content.Context;
import java.util.List;
import android.widget.LinearLayout;

public class SettingRadioGroup extends LinearLayout
{
    private OnSettingRadioButtonSelectionListener _listener;
    private List<SettingRadioButtonView> _radioButtons;
    
    public SettingRadioGroup(final Context context) {
        super(context);
        this.init();
    }
    
    public SettingRadioGroup(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        this._radioButtons = new LinkedList<SettingRadioButtonView>();
    }
    
    public void addSettingRadioButton(final SettingRadioButtonView settingRadioButtonView) {
        this.addView((View)settingRadioButtonView, -1, -2);
        this._radioButtons.add(settingRadioButtonView);
        settingRadioButtonView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                SettingRadioGroup.this.setSelectedRadioButton(settingRadioButtonView);
            }
        });
    }
    
    public void setOnSettingRadioButtonSelectionListener(final OnSettingRadioButtonSelectionListener listener) {
        this._listener = listener;
    }
    
    public void setSelectedRadioButton(final SettingRadioButtonView settingRadioButtonView) {
        if (!settingRadioButtonView.isChecked()) {
            settingRadioButtonView.toggle();
            for (final SettingRadioButtonView settingRadioButtonView2 : this._radioButtons) {
                if (settingRadioButtonView2.isChecked() && settingRadioButtonView2 != settingRadioButtonView) {
                    settingRadioButtonView2.toggle();
                }
            }
            if (this._listener != null) {
                this._listener.onSelectionChanged(settingRadioButtonView);
            }
        }
    }
    
    public interface OnSettingRadioButtonSelectionListener
    {
        void onSelectionChanged(final SettingRadioButtonView p0);
    }
}
