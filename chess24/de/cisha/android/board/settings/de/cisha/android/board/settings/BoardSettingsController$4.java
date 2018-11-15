/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.settings;

import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.settings.SpeedChooserController;

class BoardSettingsController
implements SpeedChooserController.SpeedChooseListener {
    final /* synthetic */ SettingsService val$service;

    BoardSettingsController(SettingsService settingsService) {
        this.val$service = settingsService;
    }

    @Override
    public void onSpeedChoosen(int n) {
        this.val$service.setMoveTime(n * 200);
    }
}
