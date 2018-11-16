// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.VideoLanguage;
import java.util.LinkedList;
import java.util.List;

public class VideoLanguageMultipleSelectionSetting extends AbstractMultipleSelectionSetting<LanguageSettingItem>
{
    @Override
    protected List<LanguageSettingItem> createOptions() {
        final LinkedList<LanguageSettingItem> list = new LinkedList<LanguageSettingItem>();
        final VideoLanguage[] values = VideoLanguage.values();
        for (int i = 0; i < values.length; ++i) {
            list.add(new LanguageSettingItem(values[i]));
        }
        return list;
    }
    
    @Override
    public String getName(final Resources resources) {
        return resources.getString(2131690022);
    }
}
