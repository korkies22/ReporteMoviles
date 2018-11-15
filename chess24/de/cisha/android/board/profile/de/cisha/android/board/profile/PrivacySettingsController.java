/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import de.cisha.android.board.profile.view.PrivacySettingChooserView;
import de.cisha.android.board.profile.view.PrivacySettingItemView;
import de.cisha.android.board.profile.view.PrivacySettingView;
import de.cisha.android.board.service.IWebSettingsService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class PrivacySettingsController
implements PrivacySettingChooserView.PrivacySettingChangedListener {
    private Set<PrivacySettingChooserView.PrivacySettingChangedListener> _listeners = Collections.newSetFromMap(new WeakHashMap());
    private Map<IWebSettingsService.PrivacySetting, PrivacySettingItemView> _mapSettingView = new HashMap<IWebSettingsService.PrivacySetting, PrivacySettingItemView>();
    private Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> _mapSettingsToValues;
    private LinearLayout _view;

    public PrivacySettingsController(PrivacySettingView privacySettingView) {
        this._view = privacySettingView;
        this._view.setOrientation(1);
        for (IWebSettingsService.PrivacySetting privacySetting : IWebSettingsService.PrivacySetting.values()) {
            PrivacySettingItemView privacySettingItemView = new PrivacySettingItemView(privacySettingView.getContext());
            privacySettingItemView.setPrivacySetting(privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue.HIDDEN);
            privacySettingItemView.setOnPrivacySettingChangedListener(this);
            privacySettingItemView.setVisibility(8);
            this._view.addView((View)privacySettingItemView);
            this._mapSettingView.put(privacySetting, privacySettingItemView);
        }
    }

    private void notifySettingChanged(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        Iterator<PrivacySettingChooserView.PrivacySettingChangedListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPrivacySettingChanged(privacySetting, privacyValue);
        }
    }

    public void addSettingChangedListener(PrivacySettingChooserView.PrivacySettingChangedListener privacySettingChangedListener) {
        this._listeners.add(privacySettingChangedListener);
    }

    @Override
    public void onPrivacySettingChanged(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this.notifySettingChanged(privacySetting, privacyValue);
    }

    public void resetPrivacySetting(IWebSettingsService.PrivacySetting privacySetting) {
        IWebSettingsService.PrivacySetting.PrivacyValue privacyValue;
        PrivacySettingItemView privacySettingItemView;
        if (this._mapSettingsToValues != null && (privacyValue = this._mapSettingsToValues.get((Object)privacySetting)) != null && (privacySettingItemView = this._mapSettingView.get((Object)privacySetting)) != null) {
            privacySettingItemView.setPrivacySetting(privacySetting, privacyValue);
        }
    }

    public void setPrivacySettingValues(final Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> map) {
        this._view.post(new Runnable(){

            @Override
            public void run() {
                PrivacySettingsController.this._mapSettingsToValues = map;
                for (Map.Entry entry : map.entrySet()) {
                    PrivacySettingItemView privacySettingItemView = (PrivacySettingItemView)((Object)PrivacySettingsController.this._mapSettingView.get(entry.getKey()));
                    if (privacySettingItemView != null) {
                        privacySettingItemView.setPrivacySetting((IWebSettingsService.PrivacySetting)((Object)entry.getKey()), (IWebSettingsService.PrivacySetting.PrivacyValue)((Object)entry.getValue()));
                    }
                    int n = privacySettingItemView != null ? 0 : 8;
                    privacySettingItemView.setVisibility(n);
                }
            }
        });
    }

    public void updatePrivacySetting(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        PrivacySettingItemView privacySettingItemView = this._mapSettingView.get((Object)privacySetting);
        this._mapSettingsToValues.put(privacySetting, privacyValue);
        if (privacySettingItemView != null) {
            privacySettingItemView.setPrivacySetting(privacySetting, privacyValue);
        }
    }

}
