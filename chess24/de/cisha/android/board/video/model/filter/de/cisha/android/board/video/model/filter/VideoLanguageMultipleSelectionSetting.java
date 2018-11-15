/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.filter.AbstractMultipleSelectionSetting;
import de.cisha.android.board.video.model.filter.LanguageSettingItem;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import java.util.LinkedList;
import java.util.List;

public class VideoLanguageMultipleSelectionSetting
extends AbstractMultipleSelectionSetting<LanguageSettingItem> {
    @Override
    protected List<LanguageSettingItem> createOptions() {
        LinkedList<LanguageSettingItem> linkedList = new LinkedList<LanguageSettingItem>();
        VideoLanguage[] arrvideoLanguage = VideoLanguage.values();
        int n = arrvideoLanguage.length;
        for (int i = 0; i < n; ++i) {
            linkedList.add(new LanguageSettingItem(arrvideoLanguage[i]));
        }
        return linkedList;
    }

    @Override
    public String getName(Resources resources) {
        return resources.getString(2131690022);
    }
}
