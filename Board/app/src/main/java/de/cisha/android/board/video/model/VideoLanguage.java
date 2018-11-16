// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import android.content.res.Resources;
import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;

public enum VideoLanguage
{
    ENGLISH(LanguageCode.en, CountryCode.GB.getImageId(), 2131690024), 
    GERMAN(LanguageCode.de, CountryCode.DE.getImageId(), 2131690023), 
    SPANISH(LanguageCode.es, CountryCode.ES.getImageId(), 2131690025);
    
    private int _imageResId;
    private LanguageCode _langCode;
    private int _nameResId;
    
    private VideoLanguage(final LanguageCode langCode, final int imageResId, final int nameResId) {
        this._langCode = langCode;
        this._imageResId = imageResId;
        this._nameResId = nameResId;
    }
    
    public static VideoLanguage from(final String s) {
        final VideoLanguage[] values = values();
        for (int i = 0; i < values.length; ++i) {
            final VideoLanguage videoLanguage = values[i];
            if (videoLanguage.getIso2Code().equalsIgnoreCase(s)) {
                return videoLanguage;
            }
        }
        return VideoLanguage.ENGLISH;
    }
    
    public int getImageResId() {
        return this._imageResId;
    }
    
    public String getIso2Code() {
        return this._langCode.name();
    }
    
    public String getName(final Resources resources) {
        return resources.getString(this._nameResId);
    }
}
