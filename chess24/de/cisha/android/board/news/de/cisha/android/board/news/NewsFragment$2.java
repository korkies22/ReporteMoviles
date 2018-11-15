/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.news;

import de.cisha.android.board.Language;
import de.cisha.android.board.news.model.LanguageOption;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

class NewsFragment
implements OptionDialogFragment.OptionSelectionListener<LanguageOption> {
    NewsFragment() {
    }

    @Override
    public void onOptionSelected(LanguageOption languageOption) {
        NewsFragment.this._selectedLanguage = languageOption.getLanguage();
        NewsFragment.this.loadLatestNews();
    }
}
