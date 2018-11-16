// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

public interface SingleSelectionSetting<Type extends SettingItem> extends Setting<Type>
{
    Type getSelectedItem();
}
