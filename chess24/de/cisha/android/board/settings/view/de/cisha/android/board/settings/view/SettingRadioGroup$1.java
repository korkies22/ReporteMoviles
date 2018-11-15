/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.settings.view;

import android.view.View;
import de.cisha.android.board.settings.view.SettingRadioButtonView;

class SettingRadioGroup
implements View.OnClickListener {
    final /* synthetic */ SettingRadioButtonView val$radioButton;

    SettingRadioGroup(SettingRadioButtonView settingRadioButtonView) {
        this.val$radioButton = settingRadioButtonView;
    }

    public void onClick(View view) {
        SettingRadioGroup.this.setSelectedRadioButton(this.val$radioButton);
    }
}
