/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 */
package de.cisha.android.board.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.PlayzoneServiceDBHelper;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONOnlineEngineOpponentParser;
import de.cisha.android.board.service.jsonparser.JSONOpenGamesParser;
import de.cisha.android.board.service.jsonparser.JSONTimeControlsParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.chess.util.Logger;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;

public class PlayzoneService {
    private static PlayzoneService _instance;
    protected GameBackgroundHolderService _backgroundHolderService;
    protected List<TimeControl> _cachedTimeControls;
    private Context _context;

    private PlayzoneService(Context context) {
        this._context = context;
    }

    public static PlayzoneService getInstance(Context context) {
        if (_instance == null) {
            _instance = new PlayzoneService(context);
        }
        return _instance;
    }

    private void updateDbWithOpponents(final List<OnlineEngineOpponent> list) {
        Executors.newSingleThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                Object object = ServiceProvider.getInstance().getLoginService().getUserPrefix();
                object = new PlayzoneServiceDBHelper(PlayzoneService.this._context, (String)object);
                Dao<OnlineEngineOpponent, String> dao = object.getOnlineEngineOpponentDao();
                try {
                    dao.deleteBuilder().delete();
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        dao.create((OnlineEngineOpponent)iterator.next());
                    }
                }
                catch (SQLException sQLException) {
                    Logger.getInstance().debug(PlayzoneService.class.getName(), SQLException.class.getName(), sQLException);
                }
                object.close();
            }
        });
    }

    public void getAvailableClocks(final LoadCommandCallback<List<TimeControl>> loadCommandCallback) {
        if (this._cachedTimeControls != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    loadCommandCallback.loadingSucceded(PlayzoneService.this._cachedTimeControls);
                }
            });
            return;
        }
        new GeneralJSONAPICommandLoader().loadApiCommandGet(new LoadCommandCallbackWrapper<List<TimeControl>>(loadCommandCallback){

            @Override
            public void loadingSucceded(List<TimeControl> list) {
                synchronized (this) {
                    super.loadingSucceded(list);
                    PlayzoneService.this._cachedTimeControls = list;
                    return;
                }
            }
        }, "mobileAPI/GetAvailableClocks", null, new JSONTimeControlsParser(), true);
    }

    public void getBackgroundGameHolderService(Context context, BinderCallback object) {
        if (this._backgroundHolderService == null) {
            object = new ServiceConnection((BinderCallback)object){
                final /* synthetic */ BinderCallback val$callback;
                {
                    this.val$callback = binderCallback;
                }

                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    PlayzoneService.this._backgroundHolderService = ((GameBackgroundHolderService.ServiceBinder)iBinder).getService();
                    this.val$callback.onServiceBinded(PlayzoneService.this._backgroundHolderService);
                }

                public void onServiceDisconnected(ComponentName componentName) {
                    PlayzoneService.this._backgroundHolderService = null;
                }
            };
            context = context.getApplicationContext();
            Intent intent = new Intent(context, GameBackgroundHolderService.class);
            context.startService(intent);
            context.bindService(intent, (ServiceConnection)object, 1);
            return;
        }
        object.onServiceBinded(this._backgroundHolderService);
    }

    public List<OnlineEngineOpponent> getOnlineEngineOpponents() {
        List<OnlineEngineOpponent> list = ServiceProvider.getInstance().getLoginService().getUserPrefix();
        PlayzoneServiceDBHelper playzoneServiceDBHelper = new PlayzoneServiceDBHelper(this._context, (String)((Object)list));
        list = playzoneServiceDBHelper.getOnlineEngineOpponentDao();
        try {
            list = list.queryForAll();
        }
        catch (SQLException sQLException) {
            Logger.getInstance().debug(PlayzoneService.class.getName(), SQLException.class.getName(), sQLException);
            list = new LinkedList<OnlineEngineOpponent>();
        }
        playzoneServiceDBHelper.close();
        return list;
    }

    public void getOnlineEngineOpponents(LoadCommandCallback<List<OnlineEngineOpponent>> loadCommandCallbackWrapper) {
        GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        loadCommandCallbackWrapper = new LoadCommandCallbackWrapper<List<OnlineEngineOpponent>>(loadCommandCallbackWrapper){

            @Override
            protected void succeded(List<OnlineEngineOpponent> list) {
                super.succeded(list);
                PlayzoneService.this.updateDbWithOpponents(list);
            }
        };
        treeMap.put("extendedEngineData", "true");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallbackWrapper, "mobileAPI/PlayNow", treeMap, new JSONOnlineEngineOpponentParser(), true);
    }

    public void getOpenGames(LoadCommandCallback<List<PlayzoneGameSessionInfo>> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<List<PlayzoneGameSessionInfo>>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetOpenGames", null, new JSONOpenGamesParser(), true);
    }

    public static interface BinderCallback {
        public void onServiceBinded(GameBackgroundHolderService var1);
    }

}
