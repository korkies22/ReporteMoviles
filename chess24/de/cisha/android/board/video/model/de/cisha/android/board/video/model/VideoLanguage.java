/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model;

import android.content.res.Resources;
import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;

public enum VideoLanguage {
    ENGLISH(LanguageCode.en, CountryCode.GB.getImageId(), 2131690024),
    GERMAN(LanguageCode.de, CountryCode.DE.getImageId(), 2131690023),
    SPANISH(LanguageCode.es, CountryCode.ES.getImageId(), 2131690025);
    
    private int _imageResId;
    private LanguageCode _langCode;
    private int _nameResId;

    private VideoLanguage(LanguageCode languageCode, int n2, int n3) {
        this._langCode = languageCode;
        this._imageResId = n2;
        this._nameResId = n3;
    }

    public static VideoLanguage from(String string) {
        for (VideoLanguage videoLanguage : VideoLanguage.values()) {
            if (!videoLanguage.getIso2Code().equalsIgnoreCase(string)) continue;
            return videoLanguage;
        }
        return ENGLISH;
    }

    public int getImageResId() {
        return this._imageResId;
    }

    public String getIso2Code() {
        return this._langCode.name();
    }

    public String getName(Resources resources) {
        return resources.getString(this._nameResId);
    }
}
