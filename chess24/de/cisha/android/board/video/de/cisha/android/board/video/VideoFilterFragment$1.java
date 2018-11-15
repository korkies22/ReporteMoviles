/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.view.View;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;

class VideoFilterFragment
implements View.OnClickListener {
    final /* synthetic */ Setting val$setting;

    VideoFilterFragment(Setting setting) {
        this.val$setting = setting;
    }

    public void onClick(View view) {
        VideoFilterFragment.this.loadSettingSelectionFragment(this.val$setting);
    }
}
