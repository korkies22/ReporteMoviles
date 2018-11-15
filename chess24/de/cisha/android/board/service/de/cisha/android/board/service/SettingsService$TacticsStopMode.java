/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.SettingsService;

public static enum SettingsService.TacticsStopMode {
    STOP_AFTER_EVERY(2131690361, 2131690362),
    STOP_ON_FAILURE(2131690365, 2131690366),
    STOP_NEVER(2131690363, 2131690364);
    
    private int _descriptionResId;
    private int _titleResId;

    private SettingsService.TacticsStopMode(int n2, int n3) {
        this._titleResId = n2;
        this._descriptionResId = n3;
    }

    public int getDescriptionResId() {
        return this._descriptionResId;
    }

    public int getTitleResId() {
        return this._titleResId;
    }
}
