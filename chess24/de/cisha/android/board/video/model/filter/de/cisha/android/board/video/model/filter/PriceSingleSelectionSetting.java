/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.filter.AbstractSingleSelectionSetting;
import de.cisha.android.board.video.model.filter.PriceSettingItem;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import java.util.LinkedList;
import java.util.List;

public class PriceSingleSelectionSetting
extends AbstractSingleSelectionSetting<PriceSettingItem> {
    @Override
    protected List<PriceSettingItem> createOptions() {
        LinkedList<PriceSettingItem> linkedList = new LinkedList<PriceSettingItem>();
        linkedList.add(new PriceSettingItem(0));
        linkedList.add(new PriceSettingItem(1));
        linkedList.add(new PriceSettingItem(3));
        return linkedList;
    }

    @Override
    public String getName(Resources resources) {
        return resources.getString(2131690396);
    }
}
