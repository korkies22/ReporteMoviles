/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.filter.SettingItem;

public class PriceSettingItem
implements SettingItem {
    private int _priceCat;

    public PriceSettingItem(int n) {
        this._priceCat = n;
    }

    private static String getTitleFromCat(int n) {
        switch (n) {
            default: {
                return "";
            }
            case 3: {
                return "## 15 - 20 eur ##";
            }
            case 2: {
                return "## 10 - 15 Eur ##";
            }
            case 1: {
                return "## 5 - 10 Eur ##";
            }
            case 0: 
        }
        return "## 0 - 5 Eur ##";
    }

    @Override
    public String getTitle(Resources resources) {
        return PriceSettingItem.getTitleFromCat(this._priceCat);
    }
}
