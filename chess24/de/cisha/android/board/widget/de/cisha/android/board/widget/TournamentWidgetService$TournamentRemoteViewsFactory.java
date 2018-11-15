/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.widget.RemoteViews
 *  android.widget.RemoteViewsService
 *  android.widget.RemoteViewsService$RemoteViewsFactory
 *  org.json.JSONObject
 */
package de.cisha.android.board.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.view.TournamentInfoView;
import de.cisha.android.board.service.WebImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.widget.TournamentWidgetService;
import de.cisha.chess.util.Logger;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

class TournamentWidgetService.TournamentRemoteViewsFactory
implements RemoteViewsService.RemoteViewsFactory {
    private static final int NUMBER_OF_TOURNAMENTS = 20;
    private Context _context;
    private long _lastUpdateTime = 0L;
    private boolean _updatePending;

    public TournamentWidgetService.TournamentRemoteViewsFactory(Context context) {
        this._context = context;
    }

    private CharSequence getDetailsTextForInfo(TournamentInfo tournamentInfo) {
        return TournamentInfoView.getInfoSubtext(tournamentInfo, this._context);
    }

    private Intent getIntentFor(TournamentInfo object) {
        Intent intent = new Intent(this._context, MainActivity.class);
        intent.putExtra("action_show_fragment", TournamentDetailsFragment.class.getName());
        object = object.getTournamentId();
        if (object != null) {
            intent.putExtra("tournamentId", object.getUuid());
        }
        return intent;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void reloadTournaments() {
        int n;
        block10 : {
            int n2;
            block11 : {
                boolean bl;
                long l = System.currentTimeMillis();
                long l2 = l - this._lastUpdateTime;
                n2 = 0;
                boolean bl2 = l2 > 7200000L;
                if (TournamentWidgetService.this._tournamentInfos != null) {
                    Object object = TournamentWidgetService.this._tournamentInfos.iterator();
                    while (object.hasNext()) {
                        TournamentInfo tournamentInfo = (TournamentInfo)object.next();
                        if (tournamentInfo.getState() != TournamentState.PAUSED && tournamentInfo.getState() != TournamentState.UPCOMING || tournamentInfo.getStartDate() == null || tournamentInfo.getStartDate().getTime() >= l || l2 <= 300000L) continue;
                        object = Logger.getInstance();
                        String string = this.getClass().getName();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Special Update for widget for tournament ");
                        stringBuilder.append(tournamentInfo.getTitle());
                        object.debug(string, stringBuilder.toString());
                        bl = true;
                        break;
                    }
                } else {
                    bl = false;
                }
                n = n2;
                if (this._updatePending) break block10;
                if (bl2) break block11;
                n = n2;
                if (!bl) break block10;
            }
            this._updatePending = true;
            SocketIOTournamentListService.getInstance(this._context).getTournaments(0, 20, new LoadCommandCallback<List<TournamentInfo>>(){

                @Override
                public void loadingCancelled() {
                    TournamentRemoteViewsFactory.this._updatePending = false;
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    TournamentRemoteViewsFactory.this._updatePending = false;
                }

                @Override
                public void loadingSucceded(List<TournamentInfo> list) {
                    List<TournamentInfo> list2 = list;
                    if (list == null) {
                        list2 = new LinkedList<TournamentInfo>();
                    }
                    TournamentWidgetService.this._tournamentInfos = list2;
                    TournamentRemoteViewsFactory.this._lastUpdateTime = System.currentTimeMillis();
                    Logger.getInstance().debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, "tournaments loaded - updateing widgets ");
                    TournamentWidgetService.this.refreshTournamentsList(TournamentRemoteViewsFactory.this._context);
                    TournamentRemoteViewsFactory.this._updatePending = false;
                }
            });
            n = n2;
        }
        while (this._updatePending && n < 1000) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {}
            ++n;
        }
    }

    public int getCount() {
        Logger.getInstance().debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, "TournamentWidgetService getCount");
        if (TournamentWidgetService.this._tournamentInfos != null) {
            return Math.min(TournamentWidgetService.this._tournamentInfos.size(), 20);
        }
        return 0;
    }

    public long getItemId(int n) {
        if (TournamentWidgetService.this._tournamentInfos != null && TournamentWidgetService.this._tournamentInfos.size() > n) {
            return ((TournamentInfo)TournamentWidgetService.this._tournamentInfos.get(n)).getTournamentId().getUuid().hashCode();
        }
        return 0L;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public RemoteViews getViewAt(int n) {
        Logger logger = Logger.getInstance();
        Object object = new StringBuilder();
        object.append("TournamentWidgetService getViewAt: ");
        object.append(n);
        logger.debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, object.toString());
        logger = new RemoteViews(this._context.getPackageName(), 2131427585);
        if (TournamentWidgetService.this._tournamentInfos != null && TournamentWidgetService.this._tournamentInfos.size() > n) {
            object = (TournamentInfo)TournamentWidgetService.this._tournamentInfos.get(n);
            logger.setViewVisibility(2131297173, 0);
            logger.setTextViewText(2131297177, (CharSequence)object.getTitle());
            logger.setTextViewText(2131297174, this.getDetailsTextForInfo((TournamentInfo)object));
            if (object.hasLiveVideo()) {
                logger.setImageViewResource(2131297176, 2131230992);
            } else {
                logger.setImageViewResource(2131297176, 2131230993);
            }
            n = object.getNumberOfOngoingGames() > 0 ? 0 : 8;
            logger.setViewVisibility(2131297176, n);
            logger.setImageViewResource(2131297175, 0);
            logger.setOnClickFillInIntent(2131297173, this.getIntentFor((TournamentInfo)object));
            object = object.getIconImageURL();
            if (object != null) {
                object = WebImageService.getInstance(this._context).getWebImageCached(object.toExternalForm(), (LoadCommandCallback<Bitmap>)new LoadCommandCallbackWithTimeout<Bitmap>(){

                    @Override
                    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    }

                    @Override
                    protected void succeded(Bitmap bitmap) {
                        TournamentWidgetService.this.refreshTournamentsList(TournamentRemoteViewsFactory.this._context);
                    }
                });
                if (object != null) {
                    logger.setImageViewBitmap(2131297175, (Bitmap)object);
                    return logger;
                }
                logger.setImageViewResource(2131297175, 2131231786);
                return logger;
            }
            logger.setImageViewResource(2131297175, 2131231786);
        }
        return logger;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onCreate() {
        Logger.getInstance().debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, "TournamentWidgetService created");
    }

    public void onDataSetChanged() {
        Logger.getInstance().debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, "TournamentWidgetService dataSetChanged");
        this.reloadTournaments();
    }

    public void onDestroy() {
        Logger.getInstance().debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, "TournamentWidgetService destroyed");
    }

}
