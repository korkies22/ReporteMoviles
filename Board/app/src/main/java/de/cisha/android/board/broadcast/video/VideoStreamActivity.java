// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.video;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import de.cisha.android.board.registration.SingleScreenFragmentActivity;

public class VideoStreamActivity extends SingleScreenFragmentActivity
{
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427583);
        this.getSupportFragmentManager().beginTransaction().add(2131297197, new VideoStreamFragment()).commit();
    }
}
