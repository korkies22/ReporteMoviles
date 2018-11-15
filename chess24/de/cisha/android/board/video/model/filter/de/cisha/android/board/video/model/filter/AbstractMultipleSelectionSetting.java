/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.filter.AbstractSelectionSetting;
import de.cisha.android.board.video.model.filter.MultipleSelectionSetting;
import de.cisha.android.board.video.model.filter.SettingItem;
import java.util.List;

abstract class AbstractMultipleSelectionSetting<Type extends SettingItem>
extends AbstractSelectionSetting<Type>
implements MultipleSelectionSetting<Type> {
    AbstractMultipleSelectionSetting() {
    }

    @Override
    public List<Type> getSelectedItems() {
        return super.getSelectedItems();
    }

    @Override
    public String getSelectedText(Resources resources) {
        List<Type> list = this.getSelectedItems();
        if (list != null && !list.isEmpty()) {
            CharSequence charSequence = "";
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append(((SettingItem)list.get(i)).getTitle(resources));
                charSequence2 = charSequence2.toString();
                charSequence = charSequence2;
                if (i >= n - 1) continue;
                charSequence = new StringBuilder();
                charSequence.append((String)charSequence2);
                charSequence.append(", ");
                charSequence = charSequence.toString();
            }
            return charSequence;
        }
        return resources.getString(2131689984);
    }

    @Override
    public boolean isMultipleSelectionAllowed() {
        return true;
    }
}
