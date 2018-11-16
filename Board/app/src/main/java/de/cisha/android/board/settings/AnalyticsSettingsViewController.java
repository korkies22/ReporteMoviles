// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.settings.view.SettingCheckBoxView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;

public class AnalyticsSettingsViewController extends SettingsViewController
{
    public AnalyticsSettingsViewController(final Context context) {
        super(context);
    }
    
    @Override
    public View getSettingsView() {
        final View inflate = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427372, (ViewGroup)null);
        final SettingCheckBoxView settingCheckBoxView = (SettingCheckBoxView)inflate.findViewById(2131296975);
        settingCheckBoxView.setTitle(this.getContext().getString(2131690199));
        settingCheckBoxView.setDescription(this.getContext().getString(2131690198));
        final CheckBox checkBox = settingCheckBoxView.getCheckBox();
        checkBox.setChecked(ServiceProvider.getInstance().getTrackingService().isTrackingEnabled() ^ true);
        checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (b) {
                    ServiceProvider.getInstance().getTrackingService().disableUserTracking();
                    return;
                }
                ServiceProvider.getInstance().getTrackingService().enableUserTracking();
            }
        });
        return inflate;
    }
}
