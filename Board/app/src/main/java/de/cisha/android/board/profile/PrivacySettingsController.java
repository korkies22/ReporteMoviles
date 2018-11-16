// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import java.util.Iterator;
import android.view.View;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.HashMap;
import de.cisha.android.board.profile.view.PrivacySettingView;
import android.widget.LinearLayout;
import de.cisha.android.board.profile.view.PrivacySettingItemView;
import de.cisha.android.board.service.IWebSettingsService;
import java.util.Map;
import java.util.Set;
import de.cisha.android.board.profile.view.PrivacySettingChooserView;

public class PrivacySettingsController implements PrivacySettingChangedListener
{
    private Set<PrivacySettingChangedListener> _listeners;
    private Map<IWebSettingsService.PrivacySetting, PrivacySettingItemView> _mapSettingView;
    private Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> _mapSettingsToValues;
    private LinearLayout _view;
    
    public PrivacySettingsController(final PrivacySettingView view) {
        this._mapSettingView = new HashMap<IWebSettingsService.PrivacySetting, PrivacySettingItemView>();
        this._listeners = Collections.newSetFromMap(new WeakHashMap<PrivacySettingChangedListener, Boolean>());
        (this._view = view).setOrientation(1);
        final IWebSettingsService.PrivacySetting[] values = IWebSettingsService.PrivacySetting.values();
        for (int i = 0; i < values.length; ++i) {
            final IWebSettingsService.PrivacySetting privacySetting = values[i];
            final PrivacySettingItemView privacySettingItemView = new PrivacySettingItemView(view.getContext());
            privacySettingItemView.setPrivacySetting(privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue.HIDDEN);
            privacySettingItemView.setOnPrivacySettingChangedListener(this);
            privacySettingItemView.setVisibility(8);
            this._view.addView((View)privacySettingItemView);
            this._mapSettingView.put(privacySetting, privacySettingItemView);
        }
    }
    
    private void notifySettingChanged(final IWebSettingsService.PrivacySetting privacySetting, final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        final Iterator<PrivacySettingChangedListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPrivacySettingChanged(privacySetting, privacyValue);
        }
    }
    
    public void addSettingChangedListener(final PrivacySettingChangedListener privacySettingChangedListener) {
        this._listeners.add(privacySettingChangedListener);
    }
    
    @Override
    public void onPrivacySettingChanged(final IWebSettingsService.PrivacySetting privacySetting, final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this.notifySettingChanged(privacySetting, privacyValue);
    }
    
    public void resetPrivacySetting(final IWebSettingsService.PrivacySetting privacySetting) {
        if (this._mapSettingsToValues != null) {
            final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue = this._mapSettingsToValues.get(privacySetting);
            if (privacyValue != null) {
                final PrivacySettingItemView privacySettingItemView = this._mapSettingView.get(privacySetting);
                if (privacySettingItemView != null) {
                    privacySettingItemView.setPrivacySetting(privacySetting, privacyValue);
                }
            }
        }
    }
    
    public void setPrivacySettingValues(final Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> map) {
        this._view.post((Runnable)new Runnable() {
            @Override
            public void run() {
                PrivacySettingsController.this._mapSettingsToValues = map;
                for (final Map.Entry<Object, V> entry : map.entrySet()) {
                    final PrivacySettingItemView privacySettingItemView = PrivacySettingsController.this._mapSettingView.get(entry.getKey());
                    if (privacySettingItemView != null) {
                        privacySettingItemView.setPrivacySetting(entry.getKey(), (IWebSettingsService.PrivacySetting.PrivacyValue)entry.getValue());
                    }
                    int visibility;
                    if (privacySettingItemView != null) {
                        visibility = 0;
                    }
                    else {
                        visibility = 8;
                    }
                    privacySettingItemView.setVisibility(visibility);
                }
            }
        });
    }
    
    public void updatePrivacySetting(final IWebSettingsService.PrivacySetting privacySetting, final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        final PrivacySettingItemView privacySettingItemView = this._mapSettingView.get(privacySetting);
        this._mapSettingsToValues.put(privacySetting, privacyValue);
        if (privacySettingItemView != null) {
            privacySettingItemView.setPrivacySetting(privacySetting, privacyValue);
        }
    }
}
