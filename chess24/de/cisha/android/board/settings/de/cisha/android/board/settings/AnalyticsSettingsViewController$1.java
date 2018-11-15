/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.settings;

import android.widget.CompoundButton;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;

class AnalyticsSettingsViewController
implements CompoundButton.OnCheckedChangeListener {
    AnalyticsSettingsViewController() {
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        if (bl) {
            ServiceProvider.getInstance().getTrackingService().disableUserTracking();
            return;
        }
        ServiceProvider.getInstance().getTrackingService().enableUserTracking();
    }
}
