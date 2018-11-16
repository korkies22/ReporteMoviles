// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.JSONOpenGamesParser;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.service.jsonparser.JSONOnlineEngineOpponentParser;
import java.util.TreeMap;
import java.util.LinkedList;
import android.content.Intent;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.ServiceConnection;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.JSONTimeControlsParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Iterator;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.playzone.model.PlayzoneServiceDBHelper;
import java.util.concurrent.Executors;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import android.content.Context;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.List;
import de.cisha.android.board.playzone.GameBackgroundHolderService;

public class PlayzoneService
{
    private static PlayzoneService _instance;
    protected GameBackgroundHolderService _backgroundHolderService;
    protected List<TimeControl> _cachedTimeControls;
    private Context _context;
    
    private PlayzoneService(final Context context) {
        this._context = context;
    }
    
    public static PlayzoneService getInstance(final Context context) {
        if (PlayzoneService._instance == null) {
            PlayzoneService._instance = new PlayzoneService(context);
        }
        return PlayzoneService._instance;
    }
    
    private void updateDbWithOpponents(final List<OnlineEngineOpponent> list) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final PlayzoneServiceDBHelper playzoneServiceDBHelper = new PlayzoneServiceDBHelper(PlayzoneService.this._context, ServiceProvider.getInstance().getLoginService().getUserPrefix());
                final Dao<OnlineEngineOpponent, String> onlineEngineOpponentDao = playzoneServiceDBHelper.getOnlineEngineOpponentDao();
                try {
                    onlineEngineOpponentDao.deleteBuilder().delete();
                    final Iterator<OnlineEngineOpponent> iterator = (Iterator<OnlineEngineOpponent>)list.iterator();
                    while (iterator.hasNext()) {
                        onlineEngineOpponentDao.create(iterator.next());
                    }
                }
                catch (SQLException ex) {
                    Logger.getInstance().debug(PlayzoneService.class.getName(), SQLException.class.getName(), ex);
                }
                playzoneServiceDBHelper.close();
            }
        });
    }
    
    public void getAvailableClocks(final LoadCommandCallback<List<TimeControl>> loadCommandCallback) {
        if (this._cachedTimeControls != null) {
            new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                @Override
                public void run() {
                    loadCommandCallback.loadingSucceded(PlayzoneService.this._cachedTimeControls);
                }
            });
            return;
        }
        new GeneralJSONAPICommandLoader().loadApiCommandGet((LoadCommandCallback)new LoadCommandCallbackWrapper<List<TimeControl>>(loadCommandCallback) {
            @Override
            public void loadingSucceded(final List<TimeControl> cachedTimeControls) {
                synchronized (this) {
                    super.loadingSucceded(cachedTimeControls);
                    PlayzoneService.this._cachedTimeControls = cachedTimeControls;
                }
            }
        }, "mobileAPI/GetAvailableClocks", null, (JSONAPIResultParser)new JSONTimeControlsParser(), true);
    }
    
    public void getBackgroundGameHolderService(Context applicationContext, final BinderCallback binderCallback) {
        if (this._backgroundHolderService == null) {
            final ServiceConnection serviceConnection = (ServiceConnection)new ServiceConnection() {
                public void onServiceConnected(final ComponentName componentName, final IBinder binder) {
                    PlayzoneService.this._backgroundHolderService = ((GameBackgroundHolderService.ServiceBinder)binder).getService();
                    binderCallback.onServiceBinded(PlayzoneService.this._backgroundHolderService);
                }
                
                public void onServiceDisconnected(final ComponentName componentName) {
                    PlayzoneService.this._backgroundHolderService = null;
                }
            };
            applicationContext = applicationContext.getApplicationContext();
            final Intent intent = new Intent(applicationContext, (Class)GameBackgroundHolderService.class);
            applicationContext.startService(intent);
            applicationContext.bindService(intent, (ServiceConnection)serviceConnection, 1);
            return;
        }
        binderCallback.onServiceBinded(this._backgroundHolderService);
    }
    
    public List<OnlineEngineOpponent> getOnlineEngineOpponents() {
        final PlayzoneServiceDBHelper playzoneServiceDBHelper = new PlayzoneServiceDBHelper(this._context, ServiceProvider.getInstance().getLoginService().getUserPrefix());
        final Dao<OnlineEngineOpponent, String> onlineEngineOpponentDao = playzoneServiceDBHelper.getOnlineEngineOpponentDao();
        List<OnlineEngineOpponent> queryForAll;
        try {
            queryForAll = onlineEngineOpponentDao.queryForAll();
        }
        catch (SQLException ex) {
            Logger.getInstance().debug(PlayzoneService.class.getName(), SQLException.class.getName(), ex);
            queryForAll = new LinkedList<OnlineEngineOpponent>();
        }
        playzoneServiceDBHelper.close();
        return queryForAll;
    }
    
    public void getOnlineEngineOpponents(final LoadCommandCallback<List<OnlineEngineOpponent>> loadCommandCallback) {
        final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        final LoadCommandCallbackWrapper<List<OnlineEngineOpponent>> loadCommandCallbackWrapper = new LoadCommandCallbackWrapper<List<OnlineEngineOpponent>>(loadCommandCallback) {
            @Override
            protected void succeded(final List<OnlineEngineOpponent> list) {
                super.succeded(list);
                PlayzoneService.this.updateDbWithOpponents(list);
            }
        };
        treeMap.put("extendedEngineData", "true");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallbackWrapper, "mobileAPI/PlayNow", treeMap, new JSONOnlineEngineOpponentParser(), true);
    }
    
    public void getOpenGames(final LoadCommandCallback<List<PlayzoneGameSessionInfo>> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<List<PlayzoneGameSessionInfo>>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetOpenGames", null, new JSONOpenGamesParser(), true);
    }
    
    public interface BinderCallback
    {
        void onServiceBinded(final GameBackgroundHolderService p0);
    }
}
