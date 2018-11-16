// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import java.util.List;

public interface MultipleSelectionSetting<Type extends SettingItem> extends Setting<Type>
{
    List<Type> getSelectedItems();
}
