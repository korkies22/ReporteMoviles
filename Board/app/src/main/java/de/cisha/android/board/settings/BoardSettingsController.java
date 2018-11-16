// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import android.widget.CompoundButton;
import de.cisha.android.board.service.SettingsService;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.settings.view.SettingCheckBoxView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import android.view.View;

public class BoardSettingsController extends SettingsViewController
{
    private View _mainView;
    
    public BoardSettingsController(final Context context) {
        super(context);
        final ViewGroup mainView = (ViewGroup)((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427378, (ViewGroup)null);
        final SettingCheckBoxView settingCheckBoxView = (SettingCheckBoxView)mainView.findViewById(2131296958);
        final SettingCheckBoxView settingCheckBoxView2 = (SettingCheckBoxView)mainView.findViewById(2131296959);
        final SettingCheckBoxView settingCheckBoxView3 = (SettingCheckBoxView)mainView.findViewById(2131296960);
        settingCheckBoxView.setTitle(context.getString(2131689571));
        settingCheckBoxView.setDescription(context.getString(2131689570));
        settingCheckBoxView2.setTitle(context.getString(2131689573));
        settingCheckBoxView2.setDescription(context.getString(2131689572));
        settingCheckBoxView3.setTitle(context.getString(2131689575));
        settingCheckBoxView3.setDescription(context.getString(2131689574));
        final SettingsService settingsService = ServiceProvider.getInstance().getSettingsService();
        settingCheckBoxView.getCheckBox().setChecked(settingsService.isArrowLastMoveEnabled());
        settingCheckBoxView.getCheckBox().setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean arrowLastMoveEnabled) {
                settingsService.setArrowLastMoveEnabled(arrowLastMoveEnabled);
            }
        });
        settingCheckBoxView2.getCheckBox().setChecked(settingsService.playMoveSounds());
        settingCheckBoxView2.getCheckBox().setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean playMoveSounds) {
                settingsService.setPlayMoveSounds(playMoveSounds);
            }
        });
        settingCheckBoxView3.getCheckBox().setChecked(settingsService.isMarkSquareModeForBoardEnabled());
        settingCheckBoxView3.getCheckBox().setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean markSquareModeForBoardEnabled) {
                settingsService.setMarkSquareModeForBoardEnabled(markSquareModeForBoardEnabled);
            }
        });
        final SpeedChooserController speedChooserController = new SpeedChooserController(context, 2131690280, 2131231665, 2130903041);
        speedChooserController.setChoosenSpeedLevel(settingsService.getMoveTimeInMills() / 200);
        mainView.addView(speedChooserController.getView(), -1, -2);
        speedChooserController.setOnSpeedChooseListener((SpeedChooserController.SpeedChooseListener)new SpeedChooserController.SpeedChooseListener() {
            @Override
            public void onSpeedChoosen(final int n) {
                settingsService.setMoveTime(n * 200);
            }
        });
        final SpeedChooserController speedChooserController2 = new SpeedChooserController(context, 2131690278, 2131231665, 2130903040);
        speedChooserController2.setChoosenSpeedLevel((settingsService.getAutoReplaySpeedMillis() - 500) / 1000);
        mainView.addView(speedChooserController2.getView(), -1, -2);
        speedChooserController2.setOnSpeedChooseListener((SpeedChooserController.SpeedChooseListener)new SpeedChooserController.SpeedChooseListener() {
            @Override
            public void onSpeedChoosen(final int n) {
                settingsService.setAutoReplaySpeedMillis(n * 1000 + 500);
            }
        });
        mainView.addView(new BoardThemeChooserController(context).getView(), -1, -2);
        this._mainView = (View)mainView;
    }
    
    @Override
    public View getSettingsView() {
        return this._mainView;
    }
}
