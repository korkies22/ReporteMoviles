/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.filter.AbstractSelectionSetting;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.model.filter.SingleSelectionSetting;

abstract class AbstractSingleSelectionSetting<Type extends SettingItem>
extends AbstractSelectionSetting<Type>
implements SingleSelectionSetting<Type> {
    AbstractSingleSelectionSetting() {
    }

    @Override
    public Type getSelectedItem() {
        return super.getSelectedItem();
    }

    @Override
    public String getSelectedText(Resources resources) {
        Type Type = this.getSelectedItem();
        if (Type == null) {
            return resources.getString(2131689984);
        }
        return Type.getTitle(resources);
    }

    @Override
    public boolean isMultipleSelectionAllowed() {
        return false;
    }
}
