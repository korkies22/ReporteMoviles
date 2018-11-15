/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.settings;

import android.widget.CompoundButton;
import de.cisha.android.board.service.SettingsService;

class BoardSettingsController
implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ SettingsService val$service;

    BoardSettingsController(SettingsService settingsService) {
        this.val$service = settingsService;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        this.val$service.setMarkSquareModeForBoardEnabled(bl);
    }
}
