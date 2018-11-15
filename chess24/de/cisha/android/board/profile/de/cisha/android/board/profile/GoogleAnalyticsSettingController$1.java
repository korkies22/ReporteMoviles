/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.profile;

import android.widget.CompoundButton;
import de.cisha.android.board.service.ITrackingService;

class GoogleAnalyticsSettingController
implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ ITrackingService val$service;

    GoogleAnalyticsSettingController(ITrackingService iTrackingService) {
        this.val$service = iTrackingService;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        if (bl) {
            this.val$service.disableUserTracking();
            return;
        }
        this.val$service.enableUserTracking();
    }
}
