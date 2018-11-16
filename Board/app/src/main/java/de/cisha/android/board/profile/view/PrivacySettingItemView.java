// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import de.cisha.android.board.service.IWebSettingsService;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class PrivacySettingItemView extends RelativeLayout
{
    private PrivacySettingChooserView _privacyView;
    private TextView _subtitleView;
    private TextView _titleView;
    
    public PrivacySettingItemView(final Context context) {
        super(context);
        this.init();
    }
    
    public PrivacySettingItemView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427518, (ViewGroup)this);
        this._privacyView = (PrivacySettingChooserView)this.findViewById(2131296836);
        this._titleView = (TextView)this.findViewById(2131296838);
        this._subtitleView = (TextView)this.findViewById(2131296837);
    }
    
    public void setOnPrivacySettingChangedListener(final PrivacySettingChooserView.PrivacySettingChangedListener onPrivacySettingChangedListener) {
        this._privacyView.setOnPrivacySettingChangedListener(onPrivacySettingChangedListener);
    }
    
    public void setPrivacySetting(final IWebSettingsService.PrivacySetting privacySetting, final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this._privacyView.setPrivacySetting(privacySetting, privacyValue);
        this._titleView.setText(privacySetting.getNameResId());
        this._subtitleView.setText(privacySetting.getShortDescriptionResId());
    }
}
