/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.CheckBox
 */
package de.cisha.android.board.settings.view;

import android.view.View;
import android.widget.CheckBox;

class SettingCheckBoxView
implements View.OnClickListener {
    SettingCheckBoxView() {
    }

    public void onClick(View view) {
        SettingCheckBoxView.this._checkBox.toggle();
    }
}
