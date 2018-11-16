// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import java.util.LinkedList;
import java.util.List;

public class PriceSingleSelectionSetting extends AbstractSingleSelectionSetting<PriceSettingItem>
{
    @Override
    protected List<PriceSettingItem> createOptions() {
        final LinkedList<PriceSettingItem> list = new LinkedList<PriceSettingItem>();
        list.add(new PriceSettingItem(0));
        list.add(new PriceSettingItem(1));
        list.add(new PriceSettingItem(3));
        return list;
    }
    
    @Override
    public String getName(final Resources resources) {
        return resources.getString(2131690396);
    }
}
