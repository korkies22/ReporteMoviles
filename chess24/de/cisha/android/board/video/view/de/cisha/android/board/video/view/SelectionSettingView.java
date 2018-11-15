/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.view.SettingItemView;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class SelectionSettingView
extends LinearLayout {
    private Setting<? extends SettingItem> _setting;
    private WeakHashMap<SettingItem, SettingItemView> _weakItemViewMap;

    public SelectionSettingView(Context context, Setting<? extends SettingItem> setting) {
        super(context);
        this._setting = setting;
        this._weakItemViewMap = new WeakHashMap();
        this.initView();
    }

    private void initView() {
        this.setOrientation(1);
        for (final SettingItem settingItem : this._setting.getOptions()) {
            SettingItemView settingItemView = new SettingItemView(this.getContext(), settingItem.getTitle(this.getResources()), this._setting.isSelected(settingItem));
            this._weakItemViewMap.put(settingItem, settingItemView);
            settingItemView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    SelectionSettingView.this._setting.setSelected(settingItem);
                    SelectionSettingView.this.updateAllItemViews();
                }
            });
            this.addView((View)settingItemView);
        }
    }

    private void updateAllItemViews() {
        for (Map.Entry<SettingItem, SettingItemView> entry : this._weakItemViewMap.entrySet()) {
            entry.getValue().setChecked(this._setting.isSelected(entry.getKey()));
        }
    }

}
