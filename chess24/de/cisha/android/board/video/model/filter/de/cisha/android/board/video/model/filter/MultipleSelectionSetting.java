/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model.filter;

import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import java.util.List;

public interface MultipleSelectionSetting<Type extends SettingItem>
extends Setting<Type> {
    public List<Type> getSelectedItems();
}
