// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.VideoLanguage;

public class LanguageSettingItem implements SettingItem
{
    private VideoLanguage _lang;
    
    public LanguageSettingItem(final VideoLanguage lang) {
        this._lang = lang;
    }
    
    @Override
    public String getTitle(final Resources resources) {
        return this._lang.getName(resources);
    }
    
    public VideoLanguage getVideoLanguage() {
        return this._lang;
    }
}
