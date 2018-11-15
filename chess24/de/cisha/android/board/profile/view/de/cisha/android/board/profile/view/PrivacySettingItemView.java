/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.board.profile.view.PrivacySettingChooserView;
import de.cisha.android.board.service.IWebSettingsService;

public class PrivacySettingItemView
extends RelativeLayout {
    private PrivacySettingChooserView _privacyView;
    private TextView _subtitleView;
    private TextView _titleView;

    public PrivacySettingItemView(Context context) {
        super(context);
        this.init();
    }

    public PrivacySettingItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        PrivacySettingItemView.inflate((Context)this.getContext(), (int)2131427518, (ViewGroup)this);
        this._privacyView = (PrivacySettingChooserView)this.findViewById(2131296836);
        this._titleView = (TextView)this.findViewById(2131296838);
        this._subtitleView = (TextView)this.findViewById(2131296837);
    }

    public void setOnPrivacySettingChangedListener(PrivacySettingChooserView.PrivacySettingChangedListener privacySettingChangedListener) {
        this._privacyView.setOnPrivacySettingChangedListener(privacySettingChangedListener);
    }

    public void setPrivacySetting(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this._privacyView.setPrivacySetting(privacySetting, privacyValue);
        this._titleView.setText(privacySetting.getNameResId());
        this._subtitleView.setText(privacySetting.getShortDescriptionResId());
    }
}
