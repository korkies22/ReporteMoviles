// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import android.widget.CheckBox;
import android.view.View;
import android.widget.CompoundButton;
import de.cisha.android.board.service.SettingsService;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.cisha.android.board.settings.view.SettingCheckBoxView;
import de.cisha.android.board.service.ServiceProvider;
import android.content.Context;
import android.widget.LinearLayout;

public class PlayzoneSettingsController extends SettingsViewController
{
    private LinearLayout _mainView;
    
    public PlayzoneSettingsController(final Context context) {
        super(context);
        final SettingsService settingsService = ServiceProvider.getInstance().getSettingsService();
        (this._mainView = new LinearLayout(context)).setOrientation(1);
        final SettingCheckBoxView settingCheckBoxView = new SettingCheckBoxView(this.getContext());
        settingCheckBoxView.setTitle(this.getContext().getText(2131690117));
        settingCheckBoxView.setDescription(context.getString(2131690118));
        final CheckBox checkBox = settingCheckBoxView.getCheckBox();
        checkBox.setChecked(settingsService.getAutoQueen());
        checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean autoQueen) {
                settingsService.setAutoQueen(autoQueen);
            }
        });
        this._mainView.addView((View)settingCheckBoxView, -1, -2);
        final SettingCheckBoxView settingCheckBoxView2 = new SettingCheckBoxView(this.getContext());
        settingCheckBoxView2.setTitle(this.getContext().getText(2131690170));
        settingCheckBoxView2.setDescription(context.getString(2131690171));
        final CheckBox checkBox2 = settingCheckBoxView2.getCheckBox();
        checkBox2.setChecked(settingsService.premovesAllowed());
        checkBox2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean premovesAllowed) {
                settingsService.setPremovesAllowed(premovesAllowed);
            }
        });
        this._mainView.addView((View)settingCheckBoxView2, -1, -2);
        final SettingCheckBoxView settingCheckBoxView3 = new SettingCheckBoxView(this.getContext());
        settingCheckBoxView3.setTitle(this.getContext().getText(2131690120));
        settingCheckBoxView3.setDescription(context.getString(2131690121));
        final CheckBox checkBox3 = settingCheckBoxView3.getCheckBox();
        checkBox3.setChecked(settingsService.confirmMove());
        checkBox3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean confirmMove) {
                settingsService.setConfirmMove(confirmMove);
            }
        });
        this._mainView.addView((View)settingCheckBoxView3, -1, -2);
    }
    
    @Override
    public View getSettingsView() {
        return (View)this._mainView;
    }
}
