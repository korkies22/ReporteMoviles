/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.text.format.DateUtils
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.pgn;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.pgn.PGNGameView;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.util.AndroidPGNReader;
import de.cisha.android.board.util.FileHelper;
import de.cisha.android.board.util.HTTPHelperAsyn;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.PGNGame;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import de.cisha.chess.util.PGNReader;
import de.cisha.chess.util.PGNReaderDelegate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PGNListFragment
extends AbstractDatabaseListFragment<PGNGameView>
implements PGNReaderDelegate {
    private static final int GAMES_TO_ADD_AT_ONCE = 10;
    private List<GameInfo> _allGameInfos = new ArrayList<GameInfo>(100);
    private DateFormat _dateFormat = SimpleDateFormat.getDateInstance(2, Locale.getDefault());
    private Map<GameInfo, String> _gameInfosToPGNStrings = new HashMap<GameInfo, String>();
    private List<GameInfo> _recentlyAddedInfos = new ArrayList<GameInfo>(10);
    private Uri _uriToLoad;

    private void addCachedInfos() {
        this._allGameInfos.addAll(this._recentlyAddedInfos);
        this._recentlyAddedInfos.clear();
        this.updateList();
    }

    private void readFileOrContent() {
        Object object = this._uriToLoad.getScheme();
        try {
            object = object.equals("file") ? new FileInputStream(new File(this._uriToLoad.getPath())) : this.getActivity().getContentResolver().openInputStream(this._uriToLoad);
            FileHelper.readStreamAsString((InputStream)object, new FileHelper.FileReaderDelegate(){

                @Override
                public void fileRead(String string) {
                    PGNListFragment.this.readPGNString(string);
                }
            }, null);
            return;
        }
        catch (IOException iOException) {
            String string = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IOException while reading PGN File:");
            stringBuilder.append((Object)this._uriToLoad);
            Log.d((String)string, (String)stringBuilder.toString(), (Throwable)iOException);
            return;
        }
    }

    private void readPGNString(String string) {
        ((PGNReader)new AndroidPGNReader()).readPGN(string, this);
    }

    private void readWebUrl() {
        try {
            HTTPHelperAsyn.HTTPHelperDelegate hTTPHelperDelegate = new HTTPHelperAsyn.HTTPHelperDelegate(){

                @Override
                public void loadingFailed(int n, String string) {
                }

                @Override
                public void urlLoaded(String string) {
                    PGNListFragment.this.readPGNString(string);
                }
            };
            HTTPHelperAsyn.loadUrlGet(new URL(this._uriToLoad.toString()), hTTPHelperDelegate);
            return;
        }
        catch (MalformedURLException malformedURLException) {
            String string = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IOException while reading PGN URL:");
            stringBuilder.append((Object)this._uriToLoad);
            Log.d((String)string, (String)stringBuilder.toString(), (Throwable)malformedURLException);
            return;
        }
    }

    @Override
    public void addPGNGame(PGNGame pGNGame) {
        GameInfo gameInfo = new GameInfo(pGNGame);
        this._recentlyAddedInfos.add(gameInfo);
        this._gameInfosToPGNStrings.put(gameInfo, pGNGame.getGamePGNString());
        if (this._allGameInfos.size() % 10 == 0) {
            this.addCachedInfos();
        }
    }

    @Override
    protected PGNGameView createListItemView() {
        PGNGameView pGNGameView = new PGNGameView((Context)this.getActivity());
        pGNGameView.setIconResourceId(2131231483);
        return pGNGameView;
    }

    @Override
    public void finishedReadingPGN() {
        this.addCachedInfos();
    }

    @Override
    protected List<GameInfo> getGameInfoList() {
        return this._allGameInfos;
    }

    @Override
    public String getHeaderText(Resources resources) {
        return "PGN";
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }

    @Override
    protected List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(GameInfo gameInfo) {
        LinkedList<RookieMoreDialogFragment.ListOption> linkedList = new LinkedList<RookieMoreDialogFragment.ListOption>();
        linkedList.add(new CopyGameListOption(gameInfo));
        return linkedList;
    }

    @Override
    protected int getMaximumNumbersOfItemsToShow() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getTrackingName() {
        return "PGNList";
    }

    @Override
    protected View inflateIntroView(ViewGroup viewGroup) {
        return new View((Context)this.getActivity());
    }

    @Override
    protected void itemClicked(GameInfo gameInfo) {
        AnalyzeFragment analyzeFragment = new AnalyzeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pgnGame", (Serializable)((Object)this._gameInfosToPGNStrings.get(gameInfo)));
        analyzeFragment.setArguments(bundle);
        this.getContentPresenter().showFragment(analyzeFragment, true, true);
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        if (this._uriToLoad != null) {
            object = this._uriToLoad.getScheme();
            if (object != null && (object.equals("http") || object.equals("https"))) {
                this.readWebUrl();
                return;
            }
            if (object != null && (object.equals("file") || object.equals("content"))) {
                this.readFileOrContent();
                return;
            }
            Logger logger = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown data scheme: ");
            stringBuilder.append((String)object);
            stringBuilder.append(" in uri: ");
            stringBuilder.append((Object)this._uriToLoad);
            logger.debug("PGNListActivity", stringBuilder.toString());
            return;
        }
        Logger.getInstance().error("PGNListActivity", "Error: no uri was set when opening fragment");
    }

    public void setUri(Uri uri) {
        this._uriToLoad = uri;
    }

    @Override
    protected void updateListItem(PGNGameView pGNGameView, GameInfo object, boolean bl) {
        if (object.getWhitePlayer() != null) {
            pGNGameView.setPlayerLeftName(object.getWhitePlayer().getName());
            pGNGameView.setLeftRating(object.getWhitePlayer().getRating());
            pGNGameView.setLeftCountry(object.getWhitePlayer().getCountry());
        } else {
            pGNGameView.setPlayerLeftName("");
            pGNGameView.setLeftRating(null);
            pGNGameView.setLeftCountry(null);
        }
        if (object.getBlackplayer() != null) {
            pGNGameView.setPlayerRightName(object.getBlackplayer().getName());
            pGNGameView.setRightRating(object.getBlackplayer().getRating());
            pGNGameView.setRightCountry(object.getBlackplayer().getCountry());
        } else {
            pGNGameView.setPlayerRightName("");
            pGNGameView.setRightRating(null);
            pGNGameView.setRightCountry(null);
        }
        pGNGameView.setResultText(object.getResultStatus().getResult().getShortDescription());
        Date date = object.getDate();
        if (DateUtils.isToday((long)date.getTime())) {
            long l = (System.currentTimeMillis() - date.getTime()) / 60000L;
            if (l > 60L) {
                object = new StringBuilder();
                object.append(l /= 60L);
                object.append(" hours ago");
                object = object.toString();
            } else {
                object = new StringBuilder();
                object.append(l);
                object.append(" minutes ago");
                object = object.toString();
            }
        } else {
            object = this._dateFormat.format(object.getDate());
        }
        pGNGameView.setDateTimeText((String)object);
    }

    protected class CopyGameListOption
    implements RookieMoreDialogFragment.ListOption {
        private GameInfo _info;
        private String _string;

        public CopyGameListOption(GameInfo gameInfo) {
            this._string = PGNListFragment.this.getString(2131690105);
            this._info = gameInfo;
        }

        @Override
        public void executeAction() {
            new AsyncTask<Void, Void, Void>(){

                protected /* varargs */ Void doInBackground(Void ... object) {
                    object = new PGNReader();
                    GameHolder gameHolder = new GameHolder(new Game());
                    object.readSingleGame((String)PGNListFragment.this._gameInfosToPGNStrings.get(CopyGameListOption.this._info), gameHolder, null);
                    object = gameHolder.getRootMoveContainer().getGame();
                    ServiceProvider.getInstance().getGameService().saveAnalysis((Game)object);
                    return null;
                }

                protected void onPostExecute(Void void_) {
                    PGNListFragment.this.hideDialogWaiting();
                    PGNListFragment.this.showAnalysisSavedToast();
                }

                protected void onPreExecute() {
                    PGNListFragment.this.showDialogWaiting(false, false, "", null);
                }
            }.execute((Object[])new Void[]{null});
        }

        @Override
        public String getString() {
            return this._string;
        }

    }

}
