/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model.filter;

import android.content.res.Resources;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.filter.SettingItem;

public class LanguageSettingItem
implements SettingItem {
    private VideoLanguage _lang;

    public LanguageSettingItem(VideoLanguage videoLanguage) {
        this._lang = videoLanguage;
    }

    @Override
    public String getTitle(Resources resources) {
        return this._lang.getName(resources);
    }

    public VideoLanguage getVideoLanguage() {
        return this._lang;
    }
}
