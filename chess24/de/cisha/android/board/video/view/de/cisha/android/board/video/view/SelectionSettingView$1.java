/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video.view;

import android.view.View;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;

class SelectionSettingView
implements View.OnClickListener {
    final /* synthetic */ SettingItem val$item;

    SelectionSettingView(SettingItem settingItem) {
        this.val$item = settingItem;
    }

    public void onClick(View view) {
        SelectionSettingView.this._setting.setSelected(this.val$item);
        SelectionSettingView.this.updateAllItemViews();
    }
}
