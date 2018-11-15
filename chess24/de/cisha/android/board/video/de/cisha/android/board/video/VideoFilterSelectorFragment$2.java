/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;

class VideoFilterSelectorFragment
implements Runnable {
    final /* synthetic */ Setting val$setting;

    VideoFilterSelectorFragment(Setting setting) {
        this.val$setting = setting;
    }

    @Override
    public void run() {
        VideoFilterSelectorFragment.this._setting = this.val$setting;
        VideoFilterSelectorFragment.this.updateView();
    }
}
