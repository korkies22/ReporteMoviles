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
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.settings.BoardThemeChooserController;
import de.cisha.android.board.settings.SettingsViewController;
import de.cisha.android.board.settings.SpeedChooserController;
import de.cisha.android.board.settings.view.SettingCheckBoxView;

public class BoardSettingsController
extends SettingsViewController {
    private View _mainView;

    public BoardSettingsController(Context context) {
        super(context);
        ViewGroup viewGroup = (ViewGroup)((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427378, null);
        Object object = (SettingCheckBoxView)viewGroup.findViewById(2131296958);
        SettingCheckBoxView settingCheckBoxView = (SettingCheckBoxView)viewGroup.findViewById(2131296959);
        SettingCheckBoxView settingCheckBoxView2 = (SettingCheckBoxView)viewGroup.findViewById(2131296960);
        object.setTitle(context.getString(2131689571));
        object.setDescription(context.getString(2131689570));
        settingCheckBoxView.setTitle(context.getString(2131689573));
        settingCheckBoxView.setDescription(context.getString(2131689572));
        settingCheckBoxView2.setTitle(context.getString(2131689575));
        settingCheckBoxView2.setDescription(context.getString(2131689574));
        final SettingsService settingsService = ServiceProvider.getInstance().getSettingsService();
        object.getCheckBox().setChecked(settingsService.isArrowLastMoveEnabled());
        object.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                settingsService.setArrowLastMoveEnabled(bl);
            }
        });
        settingCheckBoxView.getCheckBox().setChecked(settingsService.playMoveSounds());
        settingCheckBoxView.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                settingsService.setPlayMoveSounds(bl);
            }
        });
        settingCheckBoxView2.getCheckBox().setChecked(settingsService.isMarkSquareModeForBoardEnabled());
        settingCheckBoxView2.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                settingsService.setMarkSquareModeForBoardEnabled(bl);
            }
        });
        object = new SpeedChooserController(context, 2131690280, 2131231665, 2130903041);
        object.setChoosenSpeedLevel(settingsService.getMoveTimeInMills() / 200);
        viewGroup.addView(object.getView(), -1, -2);
        object.setOnSpeedChooseListener(new SpeedChooserController.SpeedChooseListener(){

            @Override
            public void onSpeedChoosen(int n) {
                settingsService.setMoveTime(n * 200);
            }
        });
        object = new SpeedChooserController(context, 2131690278, 2131231665, 2130903040);
        object.setChoosenSpeedLevel((settingsService.getAutoReplaySpeedMillis() - 500) / 1000);
        viewGroup.addView(object.getView(), -1, -2);
        object.setOnSpeedChooseListener(new SpeedChooserController.SpeedChooseListener(){

            @Override
            public void onSpeedChoosen(int n) {
                settingsService.setAutoReplaySpeedMillis(n * 1000 + 500);
            }
        });
        viewGroup.addView(new BoardThemeChooserController(context).getView(), -1, -2);
        this._mainView = viewGroup;
    }

    @Override
    public View getSettingsView() {
        return this._mainView;
    }

}
