/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile.view;

import de.cisha.android.board.profile.view.PrivacySettingChooserView;
import de.cisha.android.board.service.IWebSettingsService;

public static interface PrivacySettingChooserView.PrivacySettingChangedListener {
    public void onPrivacySettingChanged(IWebSettingsService.PrivacySetting var1, IWebSettingsService.PrivacySetting.PrivacyValue var2);
}
