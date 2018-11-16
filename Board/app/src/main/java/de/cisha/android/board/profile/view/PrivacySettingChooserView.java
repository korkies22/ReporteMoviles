// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.service.IWebSettingsService;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class PrivacySettingChooserView extends LinearLayout implements View.OnClickListener
{
    private View _friendsView;
    private View _hiddenView;
    private PrivacySettingChangedListener _listener;
    private IWebSettingsService.PrivacySetting.PrivacyValue _privacyValue;
    private IWebSettingsService.PrivacySetting _setting;
    private View _worldView;
    
    public PrivacySettingChooserView(final Context context) {
        super(context);
        this.init();
    }
    
    public PrivacySettingChooserView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427517, (ViewGroup)this);
        this._hiddenView = this.findViewById(2131296834);
        this._friendsView = this.findViewById(2131296833);
        this._worldView = this.findViewById(2131296835);
        this._hiddenView.setOnClickListener((View.OnClickListener)this);
        this._friendsView.setOnClickListener((View.OnClickListener)this);
        this._worldView.setOnClickListener((View.OnClickListener)this);
    }
    
    public void onClick(final View view) {
        if (this._setting != null) {
            final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue = this._privacyValue;
            if (view == this._hiddenView) {
                this._privacyValue = IWebSettingsService.PrivacySetting.PrivacyValue.HIDDEN;
            }
            else if (view == this._friendsView) {
                this._privacyValue = IWebSettingsService.PrivacySetting.PrivacyValue.FRIENDS;
            }
            else if (view == this._worldView) {
                this._privacyValue = IWebSettingsService.PrivacySetting.PrivacyValue.EVERYONE;
            }
            final View hiddenView = this._hiddenView;
            final View hiddenView2 = this._hiddenView;
            final boolean b = false;
            hiddenView.setSelected(view == hiddenView2);
            this._friendsView.setSelected(view == this._friendsView);
            final View worldView = this._worldView;
            boolean selected = b;
            if (view == this._worldView) {
                selected = true;
            }
            worldView.setSelected(selected);
            if (this._listener != null && privacyValue != this._privacyValue) {
                this._listener.onPrivacySettingChanged(this._setting, this._privacyValue);
            }
        }
    }
    
    public void setOnPrivacySettingChangedListener(final PrivacySettingChangedListener listener) {
        this._listener = listener;
    }
    
    public void setPrivacySetting(final IWebSettingsService.PrivacySetting setting, final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this._setting = setting;
        this._privacyValue = privacyValue;
        final View hiddenView = this._hiddenView;
        final IWebSettingsService.PrivacySetting.PrivacyValue hidden = IWebSettingsService.PrivacySetting.PrivacyValue.HIDDEN;
        final boolean b = false;
        hiddenView.setSelected(privacyValue == hidden);
        this._friendsView.setSelected(privacyValue == IWebSettingsService.PrivacySetting.PrivacyValue.FRIENDS);
        final View worldView = this._worldView;
        boolean selected = b;
        if (privacyValue == IWebSettingsService.PrivacySetting.PrivacyValue.EVERYONE) {
            selected = true;
        }
        worldView.setSelected(selected);
    }
    
    public interface PrivacySettingChangedListener
    {
        void onPrivacySettingChanged(final IWebSettingsService.PrivacySetting p0, final IWebSettingsService.PrivacySetting.PrivacyValue p1);
    }
}
