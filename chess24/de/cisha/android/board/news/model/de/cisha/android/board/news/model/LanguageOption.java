/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.news.model;

import de.cisha.android.board.Language;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

public class LanguageOption
extends OptionDialogFragment.Option {
    private Language _language;

    public LanguageOption(String string, Language language) {
        super(string);
        this._language = language;
    }

    public Language getLanguage() {
        return this._language;
    }
}
