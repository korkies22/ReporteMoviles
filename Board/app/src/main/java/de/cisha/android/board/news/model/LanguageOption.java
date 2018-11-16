// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.news.model;

import de.cisha.android.board.Language;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

public class LanguageOption extends Option
{
    private Language _language;
    
    public LanguageOption(final String s, final Language language) {
        super(s);
        this._language = language;
    }
    
    public Language getLanguage() {
        return this._language;
    }
}
