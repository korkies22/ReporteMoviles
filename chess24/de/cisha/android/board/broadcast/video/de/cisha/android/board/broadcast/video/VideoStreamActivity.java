/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package de.cisha.android.board.broadcast.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import de.cisha.android.board.broadcast.video.VideoStreamFragment;
import de.cisha.android.board.registration.SingleScreenFragmentActivity;

public class VideoStreamActivity
extends SingleScreenFragmentActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427583);
        this.getSupportFragmentManager().beginTransaction().add(2131297197, new VideoStreamFragment()).commit();
    }
}
