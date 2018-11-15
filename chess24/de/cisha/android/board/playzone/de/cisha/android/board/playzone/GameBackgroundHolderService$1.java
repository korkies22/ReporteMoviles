/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 */
package de.cisha.android.board.playzone;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

class GameBackgroundHolderService
implements ClockListener {
    private String _oldTime;
    final /* synthetic */ AbstractGameModel val$model;

    GameBackgroundHolderService(AbstractGameModel abstractGameModel) {
        this.val$model = abstractGameModel;
    }

    private PendingIntent createPendingIntentOpenPlayzone() {
        Intent intent = new Intent(GameBackgroundHolderService.this.getApplicationContext(), MainActivity.class);
        intent.putExtra("action_show_fragment", PlayzoneRemoteFragment.class.getName());
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setFlags(335544320);
        return PendingIntent.getActivity((Context)GameBackgroundHolderService.this.getApplicationContext(), (int)0, (Intent)intent, (int)268435456);
    }

    @Override
    public void onClockChanged(long l, boolean bl) {
        if (bl == this.val$model.playerIsWhite()) {
            Object object = GameBackgroundHolderService.this.formatTime(l);
            if (this._oldTime == null || !this._oldTime.equals(object)) {
                this._oldTime = object;
                object = new NotificationCompat.Builder((Context)GameBackgroundHolderService.this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("your clock has changed to ");
                stringBuilder.append(this._oldTime);
                object.setContentText(stringBuilder.toString());
                object.setContentTitle("Open Playzone Game");
                object.setSmallIcon(2131230861);
                object.setWhen(System.currentTimeMillis());
                object.setContentIntent(this.createPendingIntentOpenPlayzone());
                object = object.build();
                object.flags |= 16;
                ((NotificationManager)GameBackgroundHolderService.this.getSystemService("notification")).notify(3474681, (Notification)object);
            }
        }
    }

    @Override
    public void onClockFlag(boolean bl) {
        if (bl == this.val$model.playerIsWhite()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder((Context)GameBackgroundHolderService.this);
            builder.setContentText("You have lost your open Playzone Game on time");
            builder.setContentTitle("Open Playzone Game");
            builder.setSmallIcon(2131230861);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentIntent(this.createPendingIntentOpenPlayzone());
            builder = builder.build();
            builder.flags |= 16;
            ((NotificationManager)GameBackgroundHolderService.this.getSystemService("notification")).notify(3474681, (Notification)builder);
        }
        GameBackgroundHolderService.this.stopServiceIfNoGameActive();
    }

    @Override
    public void onClockStarted() {
    }

    @Override
    public void onClockStopped() {
    }
}
