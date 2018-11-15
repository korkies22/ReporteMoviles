/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.widget.TextView
 */
package de.cisha.android.board.video;

import android.content.res.Resources;
import android.widget.TextView;
import de.cisha.android.board.video.model.filter.Setting;

class VideoFilterFragment
implements Setting.OnSettingChangeListener {
    final /* synthetic */ TextView val$detailText;
    final /* synthetic */ Setting val$setting;

    VideoFilterFragment(TextView textView, Setting setting) {
        this.val$detailText = textView;
        this.val$setting = setting;
    }

    @Override
    public void onSettingChanged() {
        TextView textView = this.val$detailText;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.val$setting.getSelectedText(VideoFilterFragment.this.getResources()));
        stringBuilder.append(" >");
        textView.setText((CharSequence)stringBuilder.toString());
    }
}
