// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone;

import android.os.Binder;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.MainActivity;
import android.app.PendingIntent;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.chess.util.Logger;
import java.util.TreeMap;
import android.content.Intent;
import java.util.Iterator;
import android.os.IBinder;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.model.ClockListener;
import java.util.Map;
import android.app.Service;

public class GameBackgroundHolderService extends Service
{
    public static final int NOTIFICATION_ID = 3474681;
    private Map<String, ClockListener> _clockListeners;
    private Map<String, RemoteGameAdapter> _mapGameAdapter;
    private final IBinder localBinder;
    
    public GameBackgroundHolderService() {
        this.localBinder = (IBinder)new ServiceBinder();
    }
    
    private String formatTime(final long n) {
        final int n2 = (int)(n / 60000L);
        final int n3 = (int)(n / 1000L) % 60;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(n2);
        sb.append(":");
        String s;
        if (n3 < 10) {
            s = "0";
        }
        else {
            s = "";
        }
        sb.append(s);
        sb.append(n3);
        return sb.toString();
    }
    
    private boolean hasNoActiveGame() {
        final Iterator<Map.Entry<String, RemoteGameAdapter>> iterator = this._mapGameAdapter.entrySet().iterator();
        boolean b = true;
        while (iterator.hasNext()) {
            final Map.Entry<String, RemoteGameAdapter> entry = iterator.next();
            if (entry.getValue().isGameActive()) {
                b = false;
            }
            else {
                entry.getValue().destroy();
                iterator.remove();
            }
        }
        return b;
    }
    
    public RemoteGameAdapter getRunningGameAdapter(final String s) {
        if (this._mapGameAdapter.containsKey(s)) {
            final RemoteGameAdapter remoteGameAdapter = this._mapGameAdapter.get(s);
            if (remoteGameAdapter.isGameActive()) {
                return remoteGameAdapter;
            }
        }
        return null;
    }
    
    public IBinder onBind(final Intent intent) {
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
    
    public void onStart(final Intent intent, final int n) {
        super.onStart(intent, n);
    }
    
    public void setNotifyInTitleBar(final String s, final boolean b) {
        final RemoteGameAdapter remoteGameAdapter = this._mapGameAdapter.get(s);
        if (b) {
            if (remoteGameAdapter != null && remoteGameAdapter.isGameActive()) {
                final ClockListener clockListener = new ClockListener() {
                    private String _oldTime;
                    
                    private PendingIntent createPendingIntentOpenPlayzone() {
                        final Intent intent = new Intent(GameBackgroundHolderService.this.getApplicationContext(), (Class)MainActivity.class);
                        intent.putExtra("action_show_fragment", PlayzoneRemoteFragment.class.getName());
                        intent.setAction("android.intent.action.MAIN");
                        intent.addCategory("android.intent.category.LAUNCHER");
                        intent.setFlags(335544320);
                        return PendingIntent.getActivity(GameBackgroundHolderService.this.getApplicationContext(), 0, intent, 268435456);
                    }
                    
                    @Override
                    public void onClockChanged(final long n, final boolean b) {
                        if (b == remoteGameAdapter.playerIsWhite()) {
                            final String access.000 = GameBackgroundHolderService.this.formatTime(n);
                            if (this._oldTime == null || !this._oldTime.equals(access.000)) {
                                this._oldTime = access.000;
                                final NotificationCompat.Builder builder = new NotificationCompat.Builder((Context)GameBackgroundHolderService.this);
                                final StringBuilder sb = new StringBuilder();
                                sb.append("your clock has changed to ");
                                sb.append(this._oldTime);
                                builder.setContentText(sb.toString());
                                builder.setContentTitle("Open Playzone Game");
                                builder.setSmallIcon(2131230861);
                                builder.setWhen(System.currentTimeMillis());
                                builder.setContentIntent(this.createPendingIntentOpenPlayzone());
                                final Notification build = builder.build();
                                build.flags |= 0x10;
                                ((NotificationManager)GameBackgroundHolderService.this.getSystemService("notification")).notify(3474681, build);
                            }
                        }
                    }
                    
                    @Override
                    public void onClockFlag(final boolean b) {
                        if (b == remoteGameAdapter.playerIsWhite()) {
                            final NotificationCompat.Builder builder = new NotificationCompat.Builder((Context)GameBackgroundHolderService.this);
                            builder.setContentText("You have lost your open Playzone Game on time");
                            builder.setContentTitle("Open Playzone Game");
                            builder.setSmallIcon(2131230861);
                            builder.setWhen(System.currentTimeMillis());
                            builder.setContentIntent(this.createPendingIntentOpenPlayzone());
                            final Notification build = builder.build();
                            build.flags |= 0x10;
                            ((NotificationManager)GameBackgroundHolderService.this.getSystemService("notification")).notify(3474681, build);
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
                remoteGameAdapter.getChessClock().addOnClockListener(clockListener);
                this._clockListeners.put(s, clockListener);
            }
        }
        else {
            if (remoteGameAdapter != null) {
                remoteGameAdapter.getChessClock().removeClockListener(this._clockListeners.get(s));
                this._clockListeners.remove(s);
            }
            ((NotificationManager)this.getSystemService("notification")).cancel(3474681);
        }
    }
    
    public void startUpInService(final String s, final RemoteGameAdapter remoteGameAdapter) {
        if (this._mapGameAdapter.containsKey(s)) {
            this._mapGameAdapter.get(s).destroy();
        }
        this._mapGameAdapter.put(s, remoteGameAdapter);
        remoteGameAdapter.startUp();
    }
    
    public void stopServiceIfNoGameActive() {
        if (this.hasNoActiveGame()) {
            this.stopSelf();
            Logger.getInstance().debug("gameholder", "stop service");
        }
    }
    
    public class ServiceBinder extends Binder
    {
        public GameBackgroundHolderService getService() {
            return GameBackgroundHolderService.this;
        }
    }
}
