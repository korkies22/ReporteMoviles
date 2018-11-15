/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.settings;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.settings.SettingsViewController;
import de.cisha.android.board.settings.view.SettingCheckBoxView;

public class PlayzoneSettingsController
extends SettingsViewController {
    private LinearLayout _mainView;

    public PlayzoneSettingsController(Context context) {
        super(context);
        final SettingsService settingsService = ServiceProvider.getInstance().getSettingsService();
        this._mainView = new LinearLayout(context);
        this._mainView.setOrientation(1);
        SettingCheckBoxView settingCheckBoxView = new SettingCheckBoxView(this.getContext());
        settingCheckBoxView.setTitle(this.getContext().getText(2131690117));
        settingCheckBoxView.setDescription(context.getString(2131690118));
        CheckBox checkBox = settingCheckBoxView.getCheckBox();
        checkBox.setChecked(settingsService.getAutoQueen());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                settingsService.setAutoQueen(bl);
            }
        });
        this._mainView.addView((View)settingCheckBoxView, -1, -2);
        settingCheckBoxView = new SettingCheckBoxView(this.getContext());
        settingCheckBoxView.setTitle(this.getContext().getText(2131690170));
        settingCheckBoxView.setDescription(context.getString(2131690171));
        checkBox = settingCheckBoxView.getCheckBox();
        checkBox.setChecked(settingsService.premovesAllowed());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                settingsService.setPremovesAllowed(bl);
            }
        });
        this._mainView.addView((View)settingCheckBoxView, -1, -2);
        settingCheckBoxView = new SettingCheckBoxView(this.getContext());
        settingCheckBoxView.setTitle(this.getContext().getText(2131690120));
        settingCheckBoxView.setDescription(context.getString(2131690121));
        context = settingCheckBoxView.getCheckBox();
        context.setChecked(settingsService.confirmMove());
        context.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                settingsService.setConfirmMove(bl);
            }
        });
        this._mainView.addView((View)settingCheckBoxView, -1, -2);
    }

    @Override
    public View getSettingsView() {
        return this._mainView;
    }

}
