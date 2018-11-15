/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package de.cisha.android.board.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.service.PlayzoneService;

class PlayzoneService
implements ServiceConnection {
    final /* synthetic */ PlayzoneService.BinderCallback val$callback;

    PlayzoneService(PlayzoneService.BinderCallback binderCallback) {
        this.val$callback = binderCallback;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        PlayzoneService.this._backgroundHolderService = ((GameBackgroundHolderService.ServiceBinder)iBinder).getService();
        this.val$callback.onServiceBinded(PlayzoneService.this._backgroundHolderService);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        PlayzoneService.this._backgroundHolderService = null;
    }
}
