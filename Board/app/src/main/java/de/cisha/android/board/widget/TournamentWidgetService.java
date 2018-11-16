// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.widget;

import java.net.URL;
import android.graphics.Bitmap;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.WebImageService;
import android.widget.RemoteViews;
import java.util.Iterator;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.broadcast.view.TournamentInfoView;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import android.content.Intent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import java.util.List;
import android.annotation.TargetApi;
import android.widget.RemoteViewsService;

@TargetApi(14)
public class TournamentWidgetService extends RemoteViewsService
{
    private static final String TOURNAMENT_WIDGET_PROVIDER_LOG_TAG = "TournamentWidgetProvider";
    private TournamentRemoteViewsFactory _factory;
    private List<TournamentInfo> _tournamentInfos;
    
    public TournamentWidgetService() {
        this._tournamentInfos = new LinkedList<TournamentInfo>();
    }
    
    private void refreshTournamentsList(final Context context) {
        final ComponentName componentName = new ComponentName(this.getApplicationContext(), (Class)TournamentsWidgetProvider.class);
        final AppWidgetManager instance = AppWidgetManager.getInstance(context);
        instance.notifyAppWidgetViewDataChanged(instance.getAppWidgetIds(componentName), 2131297182);
    }
    
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(final Intent intent) {
        if (this._factory == null) {
            this._factory = new TournamentRemoteViewsFactory(this.getApplicationContext());
        }
        return (RemoteViewsService.RemoteViewsFactory)this._factory;
    }
    
    class TournamentRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
    {
        private static final int NUMBER_OF_TOURNAMENTS = 20;
        private Context _context;
        private long _lastUpdateTime;
        private boolean _updatePending;
        
        public TournamentRemoteViewsFactory(final Context context) {
            this._lastUpdateTime = 0L;
            this._context = context;
        }
        
        private CharSequence getDetailsTextForInfo(final TournamentInfo tournamentInfo) {
            return TournamentInfoView.getInfoSubtext(tournamentInfo, this._context);
        }
        
        private Intent getIntentFor(final TournamentInfo tournamentInfo) {
            final Intent intent = new Intent(this._context, (Class)MainActivity.class);
            intent.putExtra("action_show_fragment", TournamentDetailsFragment.class.getName());
            final TournamentID tournamentId = tournamentInfo.getTournamentId();
            if (tournamentId != null) {
                intent.putExtra("tournamentId", tournamentId.getUuid());
            }
            return intent;
        }
        
        private void reloadTournaments() {
            final long currentTimeMillis = System.currentTimeMillis();
            final long n = currentTimeMillis - this._lastUpdateTime;
            final int n2 = 0;
            final boolean b = n > 7200000L;
            boolean b2 = false;
            Label_0193: {
                if (TournamentWidgetService.this._tournamentInfos != null) {
                    for (final TournamentInfo tournamentInfo : TournamentWidgetService.this._tournamentInfos) {
                        if ((tournamentInfo.getState() == TournamentState.PAUSED || tournamentInfo.getState() == TournamentState.UPCOMING) && tournamentInfo.getStartDate() != null && tournamentInfo.getStartDate().getTime() < currentTimeMillis && n > 300000L) {
                            final Logger instance = Logger.getInstance();
                            final String name = this.getClass().getName();
                            final StringBuilder sb = new StringBuilder();
                            sb.append("Special Update for widget for tournament ");
                            sb.append(tournamentInfo.getTitle());
                            instance.debug(name, sb.toString());
                            b2 = true;
                            break Label_0193;
                        }
                    }
                }
                b2 = false;
            }
            int n3 = n2;
            Label_0243: {
                if (!this._updatePending) {
                    if (!b) {
                        n3 = n2;
                        if (!b2) {
                            break Label_0243;
                        }
                    }
                    this._updatePending = true;
                    SocketIOTournamentListService.getInstance(this._context).getTournaments(0, 20, new LoadCommandCallback<List<TournamentInfo>>() {
                        @Override
                        public void loadingCancelled() {
                            TournamentRemoteViewsFactory.this._updatePending = false;
                        }
                        
                        @Override
                        public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                            TournamentRemoteViewsFactory.this._updatePending = false;
                        }
                        
                        @Override
                        public void loadingSucceded(final List<TournamentInfo> list) {
                            List<TournamentInfo> list2 = list;
                            if (list == null) {
                                list2 = new LinkedList<TournamentInfo>();
                            }
                            TournamentWidgetService.this._tournamentInfos = list2;
                            TournamentRemoteViewsFactory.this._lastUpdateTime = System.currentTimeMillis();
                            Logger.getInstance().debug("TournamentWidgetProvider", "tournaments loaded - updateing widgets ");
                            TournamentWidgetService.this.refreshTournamentsList(TournamentRemoteViewsFactory.this._context);
                            TournamentRemoteViewsFactory.this._updatePending = false;
                        }
                    });
                    n3 = n2;
                }
            }
        Label_0263_Outer:
            while (true) {
                if (!this._updatePending || n3 >= 1000) {
                    return;
                }
                while (true) {
                    try {
                        Thread.sleep(10L);
                        ++n3;
                        continue Label_0263_Outer;
                    }
                    catch (InterruptedException ex) {
                        continue;
                    }
                    break;
                }
            }
        }
        
        public int getCount() {
            Logger.getInstance().debug("TournamentWidgetProvider", "TournamentWidgetService getCount");
            if (TournamentWidgetService.this._tournamentInfos != null) {
                return Math.min(TournamentWidgetService.this._tournamentInfos.size(), 20);
            }
            return 0;
        }
        
        public long getItemId(final int n) {
            if (TournamentWidgetService.this._tournamentInfos != null && TournamentWidgetService.this._tournamentInfos.size() > n) {
                return TournamentWidgetService.this._tournamentInfos.get(n).getTournamentId().getUuid().hashCode();
            }
            return 0L;
        }
        
        public RemoteViews getLoadingView() {
            return null;
        }
        
        public RemoteViews getViewAt(int n) {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("TournamentWidgetService getViewAt: ");
            sb.append(n);
            instance.debug("TournamentWidgetProvider", sb.toString());
            final RemoteViews remoteViews = new RemoteViews(this._context.getPackageName(), 2131427585);
            if (TournamentWidgetService.this._tournamentInfos != null && TournamentWidgetService.this._tournamentInfos.size() > n) {
                final TournamentInfo tournamentInfo = TournamentWidgetService.this._tournamentInfos.get(n);
                remoteViews.setViewVisibility(2131297173, 0);
                remoteViews.setTextViewText(2131297177, (CharSequence)tournamentInfo.getTitle());
                remoteViews.setTextViewText(2131297174, this.getDetailsTextForInfo(tournamentInfo));
                if (tournamentInfo.hasLiveVideo()) {
                    remoteViews.setImageViewResource(2131297176, 2131230992);
                }
                else {
                    remoteViews.setImageViewResource(2131297176, 2131230993);
                }
                if (tournamentInfo.getNumberOfOngoingGames() > 0) {
                    n = 0;
                }
                else {
                    n = 8;
                }
                remoteViews.setViewVisibility(2131297176, n);
                remoteViews.setImageViewResource(2131297175, 0);
                remoteViews.setOnClickFillInIntent(2131297173, this.getIntentFor(tournamentInfo));
                final URL iconImageURL = tournamentInfo.getIconImageURL();
                if (iconImageURL != null) {
                    final Bitmap webImageCached = WebImageService.getInstance(this._context).getWebImageCached(iconImageURL.toExternalForm(), new LoadCommandCallbackWithTimeout<Bitmap>() {
                        @Override
                        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        }
                        
                        @Override
                        protected void succeded(final Bitmap bitmap) {
                            TournamentWidgetService.this.refreshTournamentsList(TournamentRemoteViewsFactory.this._context);
                        }
                    });
                    if (webImageCached != null) {
                        remoteViews.setImageViewBitmap(2131297175, webImageCached);
                        return remoteViews;
                    }
                    remoteViews.setImageViewResource(2131297175, 2131231786);
                    return remoteViews;
                }
                else {
                    remoteViews.setImageViewResource(2131297175, 2131231786);
                }
            }
            return remoteViews;
        }
        
        public int getViewTypeCount() {
            return 1;
        }
        
        public boolean hasStableIds() {
            return true;
        }
        
        public void onCreate() {
            Logger.getInstance().debug("TournamentWidgetProvider", "TournamentWidgetService created");
        }
        
        public void onDataSetChanged() {
            Logger.getInstance().debug("TournamentWidgetProvider", "TournamentWidgetService dataSetChanged");
            this.reloadTournaments();
        }
        
        public void onDestroy() {
            Logger.getInstance().debug("TournamentWidgetProvider", "TournamentWidgetService destroyed");
        }
    }
}
