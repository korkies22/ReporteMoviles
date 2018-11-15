/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.news;

import android.view.View;

class NewsFragment
implements View.OnClickListener {
    NewsFragment() {
    }

    public void onClick(View view) {
        NewsFragment.this.showLanguageChooserDialog();
    }
}
