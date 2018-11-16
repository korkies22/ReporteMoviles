// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;

abstract class AbstractSingleSelectionSetting<Type extends SettingItem> extends AbstractSelectionSetting<Type> implements SingleSelectionSetting<Type>
{
    @Override
    public Type getSelectedItem() {
        return super.getSelectedItem();
    }
    
    @Override
    public String getSelectedText(final Resources resources) {
        final SettingItem selectedItem = this.getSelectedItem();
        if (selectedItem == null) {
            return resources.getString(2131689984);
        }
        return selectedItem.getTitle(resources);
    }
    
    @Override
    public boolean isMultipleSelectionAllowed() {
        return false;
    }
}
