/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.settings.SettingsViewController;
import de.cisha.android.board.settings.view.SettingCheckBoxView;

public class AnalyticsSettingsViewController
extends SettingsViewController {
    public AnalyticsSettingsViewController(Context context) {
        super(context);
    }

    @Override
    public View getSettingsView() {
        View view = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427372, null);
        SettingCheckBoxView settingCheckBoxView = (SettingCheckBoxView)view.findViewById(2131296975);
        settingCheckBoxView.setTitle(this.getContext().getString(2131690199));
        settingCheckBoxView.setDescription(this.getContext().getString(2131690198));
        settingCheckBoxView = settingCheckBoxView.getCheckBox();
        settingCheckBoxView.setChecked(ServiceProvider.getInstance().getTrackingService().isTrackingEnabled() ^ true);
        settingCheckBoxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    ServiceProvider.getInstance().getTrackingService().disableUserTracking();
                    return;
                }
                ServiceProvider.getInstance().getTrackingService().enableUserTracking();
            }
        });
        return view;
    }

}
