/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.Messenger
 */
package de.cisha.android.board;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import de.cisha.chess.util.Logger;

class Chess24Application
implements ServiceConnection {
    Chess24Application() {
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Chess24Application.this.mService = new Messenger(iBinder);
        Logger.getInstance().debug("chess24", "Service connected.");
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Chess24Application.this.mService = null;
        Logger.getInstance().debug("chess24", "Service disconnected.");
    }
}
