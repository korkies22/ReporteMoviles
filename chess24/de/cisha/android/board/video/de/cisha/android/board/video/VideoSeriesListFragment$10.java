/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

class VideoSeriesListFragment
implements View.OnClickListener {
    VideoSeriesListFragment() {
    }

    public void onClick(View view) {
        VideoSeriesListFragment.this._drawerLayout.closeDrawers();
    }
}
