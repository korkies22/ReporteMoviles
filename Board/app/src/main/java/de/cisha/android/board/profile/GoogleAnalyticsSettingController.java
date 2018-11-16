// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import de.cisha.android.board.service.ITrackingService;
import android.view.ViewGroup;

public class GoogleAnalyticsSettingController
{
    public GoogleAnalyticsSettingController(final ViewGroup viewGroup, final ITrackingService trackingService) {
        this.connectView(viewGroup, trackingService);
    }
    
    private void connectView(final ViewGroup viewGroup, final ITrackingService trackingService) {
        final CheckBox checkBox = (CheckBox)viewGroup.findViewById(2131296819);
        checkBox.setChecked(trackingService.isTrackingEnabled() ^ true);
        checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (b) {
                    trackingService.disableUserTracking();
                    return;
                }
                trackingService.enableUserTracking();
            }
        });
    }
}
