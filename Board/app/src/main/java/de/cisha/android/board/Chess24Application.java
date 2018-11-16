// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import android.os.RemoteException;
import android.os.Message;
import android.os.Bundle;
import de.cisha.android.board.user.User;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.ILoginService;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import android.os.AsyncTask;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.android.board.util.DummyLogger;
import de.cisha.android.board.util.AndroidLogger;
import com.facebook.FacebookSdk;
import de.cisha.android.board.service.ServiceProvider;
import android.content.Intent;
import de.cisha.stockfishservice.StockfishService;
import android.content.Context;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Kit;
import android.support.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager;
import android.os.Process;
import de.cisha.chess.util.Logger;
import android.os.IBinder;
import android.content.ComponentName;
import android.os.Messenger;
import android.content.ServiceConnection;
import android.app.Application;

public class Chess24Application extends Application implements IChessServiceContext
{
    private static final String expectedProcessName = "de.cisha.android.board";
    private ServiceConnection mConnection;
    private Messenger mMessenger;
    Messenger mService;
    
    public Chess24Application() {
        this.mService = null;
        this.mMessenger = null;
        this.mConnection = (ServiceConnection)new ServiceConnection() {
            public void onServiceConnected(final ComponentName componentName, final IBinder binder) {
                Chess24Application.this.mService = new Messenger(binder);
                Logger.getInstance().debug("chess24", "Service connected.");
            }
            
            public void onServiceDisconnected(final ComponentName componentName) {
                Chess24Application.this.mService = null;
                Logger.getInstance().debug("chess24", "Service disconnected.");
            }
        };
    }
    
    @Nullable
    private String getProcessName() {
        final int myPid = Process.myPid();
        final List runningAppProcesses = ((ActivityManager)this.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (final ActivityManager.RunningAppProcessInfo activityManager.RunningAppProcessInfo : runningAppProcesses) {
            if (activityManager.RunningAppProcessInfo.pid == myPid) {
                return activityManager.RunningAppProcessInfo.processName;
            }
        }
        return null;
    }
    
    private void initCrashlytics() {
        Fabric.with((Context)this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(false).build()).build(), new Crashlytics());
    }
    
    public void bindToChessService() {
        if (this.mService == null) {
            this.bindService(new Intent((Context)this, (Class)StockfishService.class), this.mConnection, 1);
        }
    }
    
    public Messenger getChessServiceConnection() {
        return this.mService;
    }
    
    public void onCreate() {
        if (!"de.cisha.android.board".equals(this.getProcessName())) {
            return;
        }
        ServiceProvider.getInstance().initialize(this.getApplicationContext());
        final boolean debugMode = ServiceProvider.getInstance().getConfigService().isDebugMode();
        this.initCrashlytics();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        if (debugMode) {
            Logger.setLogger(new AndroidLogger());
        }
        else {
            Logger.setLogger(new DummyLogger());
        }
        UCIEngineRemote.setChessServiceContext(this);
        new AsyncTask<Void, Void, Void>() {
            public Void doInBackground(final Void... array) {
                ServiceProvider.getInstance().getLoginService().verifyAuthToken(new LoadCommandCallbackWithTimeout<UserLoginData>() {
                    @Override
                    protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        final Logger instance = Logger.getInstance();
                        final StringBuilder sb = new StringBuilder();
                        sb.append("login in with old authToken failed:");
                        sb.append(s);
                        instance.debug("Chess24App", sb.toString());
                        if (apiStatusCode == APIStatusCode.API_ERROR_INVALID_AUTHTOKEN) {
                            ServiceProvider.getInstance().getLoginService().logOut((ILoginService.LogoutCallback)new ILoginService.LogoutCallback() {
                                @Override
                                public void logoutFailed(final String s) {
                                }
                                
                                @Override
                                public void logoutSucceeded() {
                                }
                            });
                        }
                    }
                    
                    @Override
                    protected void succeded(final UserLoginData userLoginData) {
                        final Logger instance = Logger.getInstance();
                        final StringBuilder sb = new StringBuilder();
                        sb.append("logged in with old uuid:");
                        sb.append(userLoginData);
                        instance.debug("Chess24App", sb.toString());
                        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>() {
                            @Override
                            public void loadingCancelled() {
                            }
                            
                            @Override
                            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                            }
                            
                            @Override
                            public void loadingSucceded(final User user) {
                            }
                        });
                    }
                });
                return null;
            }
        }.execute((Object[])new Void[] { null });
        super.onCreate();
    }
    
    public void onTerminate() {
        super.onTerminate();
        this.unbindChessService();
    }
    
    public boolean sendChessCommand(final String s, final Messenger replyTo) {
        if (this.mService == null) {
            return false;
        }
        final Bundle data = new Bundle();
        data.putString("MSG_KEY", s);
        final Message obtain = Message.obtain();
        obtain.replyTo = replyTo;
        obtain.setData(data);
        while (true) {
            while (true) {
                try {
                    this.mService.send(obtain);
                    return true;
                    this.mService = null;
                    return false;
                    this.mService = null;
                    return false;
                }
                catch (RemoteException ex) {
                    continue;
                }
                catch (NullPointerException ex2) {}
                break;
            }
            continue;
        }
    }
    
    public void unbindChessService() {
        if (this.mService != null) {
            this.unbindService(this.mConnection);
            this.mService = null;
        }
    }
}
