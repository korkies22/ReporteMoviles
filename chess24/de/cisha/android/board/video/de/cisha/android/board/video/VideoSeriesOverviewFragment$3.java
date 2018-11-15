/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.view.View;

class VideoSeriesOverviewFragment
implements View.OnClickListener {
    VideoSeriesOverviewFragment() {
    }

    public void onClick(View view) {
        VideoSeriesOverviewFragment.this.showConversionOrStartPurchase();
    }
}
