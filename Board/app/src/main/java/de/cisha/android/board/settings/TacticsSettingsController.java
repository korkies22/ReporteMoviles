// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import de.cisha.android.board.settings.view.SettingRadioButtonView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.service.ServiceProvider;
import java.util.HashMap;
import android.content.Context;
import de.cisha.android.board.service.SettingsService;
import android.view.View;
import java.util.Map;
import de.cisha.android.board.settings.view.SettingRadioGroup;

public class TacticsSettingsController extends SettingsViewController implements OnSettingRadioButtonSelectionListener
{
    private Map<View, SettingsService.TacticsStopMode> _mapViewToOption;
    private SettingsService _settingsService;
    private View _view;
    
    public TacticsSettingsController(final Context context) {
        super(context);
        this._mapViewToOption = new HashMap<View, SettingsService.TacticsStopMode>();
        this._settingsService = ServiceProvider.getInstance().getSettingsService();
        this._view = LayoutInflater.from(context).inflate(2131427560, (ViewGroup)null);
        final SettingRadioGroup settingRadioGroup = (SettingRadioGroup)this._view.findViewById(2131296977);
        final SettingsService.TacticsStopMode[] values = SettingsService.TacticsStopMode.values();
        for (int i = 0; i < values.length; ++i) {
            final SettingsService.TacticsStopMode tacticsStopMode = values[i];
            final SettingRadioButtonView settingRadioButtonView = new SettingRadioButtonView(this.getContext());
            settingRadioButtonView.setTitle(this.getContext().getText(tacticsStopMode.getTitleResId()));
            settingRadioButtonView.setDescription(this.getContext().getText(tacticsStopMode.getDescriptionResId()));
            settingRadioGroup.addSettingRadioButton(settingRadioButtonView);
            this._mapViewToOption.put((View)settingRadioButtonView, tacticsStopMode);
            if (tacticsStopMode == this._settingsService.getTacticsStopMode()) {
                settingRadioButtonView.getRadioButton().setChecked(true);
            }
        }
        settingRadioGroup.setOnSettingRadioButtonSelectionListener((SettingRadioGroup.OnSettingRadioButtonSelectionListener)this);
    }
    
    @Override
    public View getSettingsView() {
        return this._view;
    }
    
    @Override
    public void onSelectionChanged(final SettingRadioButtonView settingRadioButtonView) {
        final SettingsService.TacticsStopMode tacticsStopMode = this._mapViewToOption.get(settingRadioButtonView);
        if (tacticsStopMode != null) {
            this._settingsService.setTacticsStopMode(tacticsStopMode);
        }
    }
}
