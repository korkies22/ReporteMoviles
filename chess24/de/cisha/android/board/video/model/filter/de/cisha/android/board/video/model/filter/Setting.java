/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.filter.SettingItem;
import java.util.List;

public interface Setting<Type extends SettingItem> {
    public void commit();

    public String getName(Resources var1);

    public List<Type> getOptions();

    public String getSelectedText(Resources var1);

    public boolean isMultipleSelectionAllowed();

    public boolean isSelected(SettingItem var1);

    public void registerOnSettingChangedListener(OnSettingChangeListener var1);

    public void rollback();

    public void setSelected(SettingItem var1);

    public void unregisterOnSettingChangedListener(OnSettingChangeListener var1);

    public static interface OnSettingChangeListener {
        public void onSettingChanged();
    }

}
