// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import java.util.List;
import android.content.res.Resources;

public interface Setting<Type extends SettingItem>
{
    void commit();
    
    String getName(final Resources p0);
    
    List<Type> getOptions();
    
    String getSelectedText(final Resources p0);
    
    boolean isMultipleSelectionAllowed();
    
    boolean isSelected(final SettingItem p0);
    
    void registerOnSettingChangedListener(final OnSettingChangeListener p0);
    
    void rollback();
    
    void setSelected(final SettingItem p0);
    
    void unregisterOnSettingChangedListener(final OnSettingChangeListener p0);
    
    public interface OnSettingChangeListener
    {
        void onSettingChanged();
    }
}
