/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.IBinder
 */
package de.cisha.android.board.playzone;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.chess.util.Logger;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GameBackgroundHolderService
extends Service {
    public static final int NOTIFICATION_ID = 3474681;
    private Map<String, ClockListener> _clockListeners;
    private Map<String, RemoteGameAdapter> _mapGameAdapter;
    private final IBinder localBinder = new ServiceBinder();

    private String formatTime(long l) {
        int n = (int)(l / 60000L);
        int n2 = (int)(l / 1000L) % 60;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        stringBuilder.append(":");
        String string = n2 < 10 ? "0" : "";
        stringBuilder.append(string);
        stringBuilder.append(n2);
        return stringBuilder.toString();
    }

    private boolean hasNoActiveGame() {
        Iterator<Map.Entry<String, RemoteGameAdapter>> iterator = this._mapGameAdapter.entrySet().iterator();
        boolean bl = true;
        while (iterator.hasNext()) {
            Map.Entry<String, RemoteGameAdapter> entry = iterator.next();
            if (entry.getValue().isGameActive()) {
                bl = false;
                continue;
            }
            entry.getValue().destroy();
            iterator.remove();
        }
        return bl;
    }

    public RemoteGameAdapter getRunningGameAdapter(String object) {
        if (this._mapGameAdapter.containsKey(object) && (object = this._mapGameAdapter.get(object)).isGameActive()) {
            return object;
        }
        return null;
    }

    public IBinder onBind(Intent intent) {
        return this.localBinder;
    }

    public void onCreate() {
        super.onCreate();
        if (this._mapGameAdapter == null) {
            this._mapGameAdapter = new TreeMap<String, RemoteGameAdapter>();
        }
        this._clockListeners = new TreeMap<String, ClockListener>();
    }

    public void onDestroy() {
        Logger.getInstance().debug("gameholder", "destroyed");
    }

    public void onStart(Intent intent, int n) {
        super.onStart(intent, n);
    }

    public void setNotifyInTitleBar(String string, boolean bl) {
        final AbstractGameModel abstractGameModel = this._mapGameAdapter.get(string);
        if (bl) {
            if (abstractGameModel != null && abstractGameModel.isGameActive()) {
                ClockListener clockListener = new ClockListener(){
                    private String _oldTime;

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
                        if (bl == abstractGameModel.playerIsWhite()) {
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
                        if (bl == abstractGameModel.playerIsWhite()) {
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
                };
                abstractGameModel.getChessClock().addOnClockListener(clockListener);
                this._clockListeners.put(string, clockListener);
                return;
            }
        } else {
            if (abstractGameModel != null) {
                ClockListener clockListener = this._clockListeners.get(string);
                abstractGameModel.getChessClock().removeClockListener(clockListener);
                this._clockListeners.remove(string);
            }
            ((NotificationManager)this.getSystemService("notification")).cancel(3474681);
        }
    }

    public void startUpInService(String string, RemoteGameAdapter remoteGameAdapter) {
        if (this._mapGameAdapter.containsKey(string)) {
            this._mapGameAdapter.get(string).destroy();
        }
        this._mapGameAdapter.put(string, remoteGameAdapter);
        remoteGameAdapter.startUp();
    }

    public void stopServiceIfNoGameActive() {
        if (this.hasNoActiveGame()) {
            this.stopSelf();
            Logger.getInstance().debug("gameholder", "stop service");
        }
    }

    public class ServiceBinder
    extends Binder {
        public GameBackgroundHolderService getService() {
            return GameBackgroundHolderService.this;
        }
    }

}
