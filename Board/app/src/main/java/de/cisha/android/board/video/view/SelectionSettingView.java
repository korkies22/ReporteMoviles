// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import java.util.Map;
import java.util.Iterator;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import java.util.WeakHashMap;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.model.filter.Setting;
import android.widget.LinearLayout;

public class SelectionSettingView extends LinearLayout
{
    private Setting<? extends SettingItem> _setting;
    private WeakHashMap<SettingItem, SettingItemView> _weakItemViewMap;
    
    public SelectionSettingView(final Context context, final Setting<? extends SettingItem> setting) {
        super(context);
        this._setting = setting;
        this._weakItemViewMap = new WeakHashMap<SettingItem, SettingItemView>();
        this.initView();
    }
    
    private void initView() {
        this.setOrientation(1);
        for (final SettingItem settingItem : this._setting.getOptions()) {
            final SettingItemView settingItemView = new SettingItemView(this.getContext(), settingItem.getTitle(this.getResources()), this._setting.isSelected(settingItem));
            this._weakItemViewMap.put(settingItem, settingItemView);
            settingItemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    SelectionSettingView.this._setting.setSelected(settingItem);
                    SelectionSettingView.this.updateAllItemViews();
                }
            });
            this.addView((View)settingItemView);
        }
    }
    
    private void updateAllItemViews() {
        for (final Map.Entry<SettingItem, SettingItemView> entry : this._weakItemViewMap.entrySet()) {
            entry.getValue().setChecked(this._setting.isSelected(entry.getKey()));
        }
    }
}
