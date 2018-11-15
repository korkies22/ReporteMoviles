/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.Application
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Process
 *  android.os.RemoteException
 *  org.json.JSONObject
 */
package de.cisha.android.board;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import de.cisha.android.board.IChessServiceContext;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.android.board.util.AndroidLogger;
import de.cisha.android.board.util.DummyLogger;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.chess.util.Logger;
import de.cisha.stockfishservice.StockfishService;
import io.fabric.sdk.android.Fabric;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class Chess24Application
extends Application
implements IChessServiceContext {
    private static final String expectedProcessName = "de.cisha.android.board";
    private ServiceConnection mConnection = new ServiceConnection(){

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Chess24Application.this.mService = new Messenger(iBinder);
            Logger.getInstance().debug("chess24", "Service connected.");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Chess24Application.this.mService = null;
            Logger.getInstance().debug("chess24", "Service disconnected.");
        }
    };
    private Messenger mMessenger = null;
    Messenger mService = null;

    @Nullable
    private String getProcessName() {
        int n = Process.myPid();
        Object object = ((ActivityManager)this.getSystemService("activity")).getRunningAppProcesses();
        if (object == null) {
            return null;
        }
        object = object.iterator();
        while (object.hasNext()) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)object.next();
            if (runningAppProcessInfo.pid != n) continue;
            return runningAppProcessInfo.processName;
        }
        return null;
    }

    private void initCrashlytics() {
        Fabric.with((Context)this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(false).build()).build(), new Crashlytics());
    }

    @Override
    public void bindToChessService() {
        if (this.mService == null) {
            this.bindService(new Intent((Context)this, StockfishService.class), this.mConnection, 1);
        }
    }

    @Override
    public Messenger getChessServiceConnection() {
        return this.mService;
    }

    public void onCreate() {
        if (!expectedProcessName.equals(this.getProcessName())) {
            return;
        }
        ServiceProvider.getInstance().initialize(this.getApplicationContext());
        boolean bl = ServiceProvider.getInstance().getConfigService().isDebugMode();
        this.initCrashlytics();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        if (bl) {
            Logger.setLogger(new AndroidLogger());
        } else {
            Logger.setLogger(new DummyLogger());
        }
        UCIEngineRemote.setChessServiceContext(this);
        new AsyncTask<Void, Void, Void>(){

            public /* varargs */ Void doInBackground(Void ... arrvoid) {
                ServiceProvider.getInstance().getLoginService().verifyAuthToken((LoadCommandCallback<UserLoginData>)new LoadCommandCallbackWithTimeout<UserLoginData>(){

                    @Override
                    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> object, JSONObject object2) {
                        object = Logger.getInstance();
                        object2 = new StringBuilder();
                        object2.append("login in with old authToken failed:");
                        object2.append(string);
                        object.debug("Chess24App", object2.toString());
                        if (aPIStatusCode == APIStatusCode.API_ERROR_INVALID_AUTHTOKEN) {
                            ServiceProvider.getInstance().getLoginService().logOut(new ILoginService.LogoutCallback(){

                                @Override
                                public void logoutFailed(String string) {
                                }

                                @Override
                                public void logoutSucceeded() {
                                }
                            });
                        }
                    }

                    @Override
                    protected void succeded(UserLoginData userLoginData) {
                        Logger logger = Logger.getInstance();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("logged in with old uuid:");
                        stringBuilder.append(userLoginData);
                        logger.debug("Chess24App", stringBuilder.toString());
                        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>(){

                            @Override
                            public void loadingCancelled() {
                            }

                            @Override
                            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                            }

                            @Override
                            public void loadingSucceded(User user) {
                            }
                        });
                    }

                });
                return null;
            }

        }.execute((Object[])new Void[]{null});
        super.onCreate();
    }

    public void onTerminate() {
        super.onTerminate();
        this.unbindChessService();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean sendChessCommand(String string, Messenger messenger) {
        if (this.mService == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("MSG_KEY", string);
        string = Message.obtain();
        string.replyTo = messenger;
        string.setData(bundle);
        try {
            this.mService.send((Message)string);
            return true;
        }
        catch (RemoteException remoteException) {}
        this.mService = null;
        return false;
        catch (NullPointerException nullPointerException) {}
        this.mService = null;
        return false;
    }

    @Override
    public void unbindChessService() {
        if (this.mService != null) {
            this.unbindService(this.mConnection);
            this.mService = null;
        }
    }

}
