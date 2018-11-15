/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RadioButton
 */
package de.cisha.android.board.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.settings.SettingsViewController;
import de.cisha.android.board.settings.view.SettingRadioButtonView;
import de.cisha.android.board.settings.view.SettingRadioGroup;
import java.util.HashMap;
import java.util.Map;

public class TacticsSettingsController
extends SettingsViewController
implements SettingRadioGroup.OnSettingRadioButtonSelectionListener {
    private Map<View, SettingsService.TacticsStopMode> _mapViewToOption = new HashMap<View, SettingsService.TacticsStopMode>();
    private SettingsService _settingsService = ServiceProvider.getInstance().getSettingsService();
    private View _view;

    public TacticsSettingsController(Context object) {
        super((Context)object);
        this._view = LayoutInflater.from((Context)object).inflate(2131427560, null);
        object = (SettingRadioGroup)this._view.findViewById(2131296977);
        for (SettingsService.TacticsStopMode tacticsStopMode : SettingsService.TacticsStopMode.values()) {
            SettingRadioButtonView settingRadioButtonView = new SettingRadioButtonView(this.getContext());
            settingRadioButtonView.setTitle(this.getContext().getText(tacticsStopMode.getTitleResId()));
            settingRadioButtonView.setDescription(this.getContext().getText(tacticsStopMode.getDescriptionResId()));
            object.addSettingRadioButton(settingRadioButtonView);
            this._mapViewToOption.put((View)settingRadioButtonView, tacticsStopMode);
            if (tacticsStopMode != this._settingsService.getTacticsStopMode()) continue;
            settingRadioButtonView.getRadioButton().setChecked(true);
        }
        object.setOnSettingRadioButtonSelectionListener(this);
    }

    @Override
    public View getSettingsView() {
        return this._view;
    }

    @Override
    public void onSelectionChanged(SettingRadioButtonView object) {
        if ((object = this._mapViewToOption.get(object)) != null) {
            this._settingsService.setTacticsStopMode((SettingsService.TacticsStopMode)((Object)object));
        }
    }
}
