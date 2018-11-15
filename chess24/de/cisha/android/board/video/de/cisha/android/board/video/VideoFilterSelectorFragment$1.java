/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

class VideoFilterSelectorFragment
implements View.OnClickListener {
    VideoFilterSelectorFragment() {
    }

    public void onClick(View view) {
        VideoFilterSelectorFragment.this.getFragmentManager().beginTransaction().hide(VideoFilterSelectorFragment.this).commit();
    }
}
