/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.profile;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import de.cisha.android.board.service.ITrackingService;

public class GoogleAnalyticsSettingController {
    public GoogleAnalyticsSettingController(ViewGroup viewGroup, ITrackingService iTrackingService) {
        this.connectView(viewGroup, iTrackingService);
    }

    private void connectView(ViewGroup viewGroup, final ITrackingService iTrackingService) {
        viewGroup = (CheckBox)viewGroup.findViewById(2131296819);
        viewGroup.setChecked(iTrackingService.isTrackingEnabled() ^ true);
        viewGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    iTrackingService.disableUserTracking();
                    return;
                }
                iTrackingService.enableUserTracking();
            }
        });
    }

}
