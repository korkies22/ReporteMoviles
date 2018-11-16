// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import java.util.List;

abstract class AbstractMultipleSelectionSetting<Type extends SettingItem> extends AbstractSelectionSetting<Type> implements MultipleSelectionSetting<Type>
{
    @Override
    public List<Type> getSelectedItems() {
        return super.getSelectedItems();
    }
    
    @Override
    public String getSelectedText(final Resources resources) {
        final List<Type> selectedItems = this.getSelectedItems();
        if (selectedItems != null && !selectedItems.isEmpty()) {
            String s = "";
            for (int size = selectedItems.size(), i = 0; i < size; ++i) {
                final StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.append(selectedItems.get(i).getTitle(resources));
                final String s2 = s = sb.toString();
                if (i < size - 1) {
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(s2);
                    sb2.append(", ");
                    s = sb2.toString();
                }
            }
            return s;
        }
        return resources.getString(2131689984);
    }
    
    @Override
    public boolean isMultipleSelectionAllowed() {
        return true;
    }
}
