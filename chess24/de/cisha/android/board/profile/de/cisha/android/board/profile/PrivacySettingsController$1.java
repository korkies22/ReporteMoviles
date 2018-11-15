/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.view.PrivacySettingItemView;
import de.cisha.android.board.service.IWebSettingsService;
import java.util.Map;
import java.util.Set;

class PrivacySettingsController
implements Runnable {
    final /* synthetic */ Map val$values;

    PrivacySettingsController(Map map) {
        this.val$values = map;
    }

    @Override
    public void run() {
        PrivacySettingsController.this._mapSettingsToValues = this.val$values;
        for (Map.Entry entry : this.val$values.entrySet()) {
            PrivacySettingItemView privacySettingItemView = (PrivacySettingItemView)((Object)PrivacySettingsController.this._mapSettingView.get(entry.getKey()));
            if (privacySettingItemView != null) {
                privacySettingItemView.setPrivacySetting((IWebSettingsService.PrivacySetting)((Object)entry.getKey()), (IWebSettingsService.PrivacySetting.PrivacyValue)((Object)entry.getValue()));
            }
            int n = privacySettingItemView != null ? 0 : 8;
            privacySettingItemView.setVisibility(n);
        }
    }
}
