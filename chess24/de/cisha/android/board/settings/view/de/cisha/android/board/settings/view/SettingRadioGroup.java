/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.settings.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import de.cisha.android.board.settings.view.SettingRadioButtonView;
import java.util.LinkedList;
import java.util.List;

public class SettingRadioGroup
extends LinearLayout {
    private OnSettingRadioButtonSelectionListener _listener;
    private List<SettingRadioButtonView> _radioButtons;

    public SettingRadioGroup(Context context) {
        super(context);
        this.init();
    }

    public SettingRadioGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        this._radioButtons = new LinkedList<SettingRadioButtonView>();
    }

    public void addSettingRadioButton(final SettingRadioButtonView settingRadioButtonView) {
        this.addView((View)settingRadioButtonView, -1, -2);
        this._radioButtons.add(settingRadioButtonView);
        settingRadioButtonView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SettingRadioGroup.this.setSelectedRadioButton(settingRadioButtonView);
            }
        });
    }

    public void setOnSettingRadioButtonSelectionListener(OnSettingRadioButtonSelectionListener onSettingRadioButtonSelectionListener) {
        this._listener = onSettingRadioButtonSelectionListener;
    }

    public void setSelectedRadioButton(SettingRadioButtonView settingRadioButtonView) {
        if (!settingRadioButtonView.isChecked()) {
            settingRadioButtonView.toggle();
            for (SettingRadioButtonView settingRadioButtonView2 : this._radioButtons) {
                if (!settingRadioButtonView2.isChecked() || settingRadioButtonView2 == settingRadioButtonView) continue;
                settingRadioButtonView2.toggle();
            }
            if (this._listener != null) {
                this._listener.onSelectionChanged(settingRadioButtonView);
            }
        }
    }

    public static interface OnSettingRadioButtonSelectionListener {
        public void onSelectionChanged(SettingRadioButtonView var1);
    }

}
