/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.cisha.android.board.service.IWebSettingsService;

public class PrivacySettingChooserView
extends LinearLayout
implements View.OnClickListener {
    private View _friendsView;
    private View _hiddenView;
    private PrivacySettingChangedListener _listener;
    private IWebSettingsService.PrivacySetting.PrivacyValue _privacyValue;
    private IWebSettingsService.PrivacySetting _setting;
    private View _worldView;

    public PrivacySettingChooserView(Context context) {
        super(context);
        this.init();
    }

    public PrivacySettingChooserView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        PrivacySettingChooserView.inflate((Context)this.getContext(), (int)2131427517, (ViewGroup)this);
        this._hiddenView = this.findViewById(2131296834);
        this._friendsView = this.findViewById(2131296833);
        this._worldView = this.findViewById(2131296835);
        this._hiddenView.setOnClickListener((View.OnClickListener)this);
        this._friendsView.setOnClickListener((View.OnClickListener)this);
        this._worldView.setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(View view) {
        if (this._setting != null) {
            IWebSettingsService.PrivacySetting.PrivacyValue privacyValue = this._privacyValue;
            if (view == this._hiddenView) {
                this._privacyValue = IWebSettingsService.PrivacySetting.PrivacyValue.HIDDEN;
            } else if (view == this._friendsView) {
                this._privacyValue = IWebSettingsService.PrivacySetting.PrivacyValue.FRIENDS;
            } else if (view == this._worldView) {
                this._privacyValue = IWebSettingsService.PrivacySetting.PrivacyValue.EVERYONE;
            }
            View view2 = this._hiddenView;
            View view3 = this._hiddenView;
            boolean bl = false;
            boolean bl2 = view == view3;
            view2.setSelected(bl2);
            view2 = this._friendsView;
            bl2 = view == this._friendsView;
            view2.setSelected(bl2);
            view2 = this._worldView;
            bl2 = bl;
            if (view == this._worldView) {
                bl2 = true;
            }
            view2.setSelected(bl2);
            if (this._listener != null && privacyValue != this._privacyValue) {
                this._listener.onPrivacySettingChanged(this._setting, this._privacyValue);
            }
        }
    }

    public void setOnPrivacySettingChangedListener(PrivacySettingChangedListener privacySettingChangedListener) {
        this._listener = privacySettingChangedListener;
    }

    public void setPrivacySetting(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this._setting = privacySetting;
        this._privacyValue = privacyValue;
        privacySetting = this._hiddenView;
        IWebSettingsService.PrivacySetting.PrivacyValue privacyValue2 = IWebSettingsService.PrivacySetting.PrivacyValue.HIDDEN;
        boolean bl = false;
        boolean bl2 = privacyValue == privacyValue2;
        privacySetting.setSelected(bl2);
        privacySetting = this._friendsView;
        bl2 = privacyValue == IWebSettingsService.PrivacySetting.PrivacyValue.FRIENDS;
        privacySetting.setSelected(bl2);
        privacySetting = this._worldView;
        bl2 = bl;
        if (privacyValue == IWebSettingsService.PrivacySetting.PrivacyValue.EVERYONE) {
            bl2 = true;
        }
        privacySetting.setSelected(bl2);
    }

    public static interface PrivacySettingChangedListener {
        public void onPrivacySettingChanged(IWebSettingsService.PrivacySetting var1, IWebSettingsService.PrivacySetting.PrivacyValue var2);
    }

}
