/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.database;

import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.AnalyzeFragment;

class AnalyzesListFragment
implements View.OnClickListener {
    AnalyzesListFragment() {
    }

    public void onClick(View view) {
        AnalyzesListFragment.this.getContentPresenter().showFragment(new AnalyzeFragment(), true, true);
    }
}
