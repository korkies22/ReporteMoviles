// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.pgn;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.util.PGNReader;
import android.os.AsyncTask;
import java.util.Date;
import android.text.format.DateUtils;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.AbstractContentFragment;
import java.io.Serializable;
import android.os.Bundle;
import de.cisha.android.board.analyze.AnalyzeFragment;
import android.view.ViewGroup;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import android.content.Context;
import android.view.View;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.PGNGame;
import java.net.MalformedURLException;
import de.cisha.android.board.util.HTTPHelperAsyn;
import java.net.URL;
import de.cisha.android.board.util.AndroidPGNReader;
import java.io.InputStream;
import java.io.IOException;
import android.util.Log;
import java.nio.charset.Charset;
import de.cisha.android.board.util.FileHelper;
import java.io.FileInputStream;
import java.io.File;
import java.util.Collection;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import java.util.Locale;
import java.util.HashMap;
import java.util.ArrayList;
import android.net.Uri;
import java.util.Map;
import java.text.DateFormat;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import de.cisha.chess.util.PGNReaderDelegate;
import de.cisha.android.board.database.AbstractDatabaseListFragment;

public class PGNListFragment extends AbstractDatabaseListFragment<PGNGameView> implements PGNReaderDelegate
{
    private static final int GAMES_TO_ADD_AT_ONCE = 10;
    private List<GameInfo> _allGameInfos;
    private DateFormat _dateFormat;
    private Map<GameInfo, String> _gameInfosToPGNStrings;
    private List<GameInfo> _recentlyAddedInfos;
    private Uri _uriToLoad;
    
    public PGNListFragment() {
        this._allGameInfos = new ArrayList<GameInfo>(100);
        this._gameInfosToPGNStrings = new HashMap<GameInfo, String>();
        this._recentlyAddedInfos = new ArrayList<GameInfo>(10);
        this._dateFormat = DateFormat.getDateInstance(2, Locale.getDefault());
    }
    
    private void addCachedInfos() {
        this._allGameInfos.addAll(this._recentlyAddedInfos);
        this._recentlyAddedInfos.clear();
        this.updateList();
    }
    
    private void readFileOrContent() {
        final String scheme = this._uriToLoad.getScheme();
        try {
            InputStream openInputStream;
            if (scheme.equals("file")) {
                openInputStream = new FileInputStream(new File(this._uriToLoad.getPath()));
            }
            else {
                openInputStream = this.getActivity().getContentResolver().openInputStream(this._uriToLoad);
            }
            FileHelper.readStreamAsString(openInputStream, (FileHelper.FileReaderDelegate)new FileHelper.FileReaderDelegate() {
                @Override
                public void fileRead(final String s) {
                    PGNListFragment.this.readPGNString(s);
                }
            }, null);
        }
        catch (IOException ex) {
            final String name = this.getClass().getName();
            final StringBuilder sb = new StringBuilder();
            sb.append("IOException while reading PGN File:");
            sb.append(this._uriToLoad);
            Log.d(name, sb.toString(), (Throwable)ex);
        }
    }
    
    private void readPGNString(final String s) {
        new AndroidPGNReader().readPGN(s, this);
    }
    
    private void readWebUrl() {
        try {
            HTTPHelperAsyn.loadUrlGet(new URL(this._uriToLoad.toString()), (HTTPHelperAsyn.HTTPHelperDelegate)new HTTPHelperAsyn.HTTPHelperDelegate() {
                @Override
                public void loadingFailed(final int n, final String s) {
                }
                
                @Override
                public void urlLoaded(final String s) {
                    PGNListFragment.this.readPGNString(s);
                }
            });
        }
        catch (MalformedURLException ex) {
            final String name = this.getClass().getName();
            final StringBuilder sb = new StringBuilder();
            sb.append("IOException while reading PGN URL:");
            sb.append(this._uriToLoad);
            Log.d(name, sb.toString(), (Throwable)ex);
        }
    }
    
    @Override
    public void addPGNGame(final PGNGame pgnGame) {
        final GameInfo gameInfo = new GameInfo(pgnGame);
        this._recentlyAddedInfos.add(gameInfo);
        this._gameInfosToPGNStrings.put(gameInfo, pgnGame.getGamePGNString());
        if (this._allGameInfos.size() % 10 == 0) {
            this.addCachedInfos();
        }
    }
    
    @Override
    protected PGNGameView createListItemView() {
        final PGNGameView pgnGameView = new PGNGameView((Context)this.getActivity());
        pgnGameView.setIconResourceId(2131231483);
        return pgnGameView;
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
    public String getHeaderText(final Resources resources) {
        return "PGN";
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }
    
    @Override
    protected List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(final GameInfo gameInfo) {
        final LinkedList<CopyGameListOption> list = (LinkedList<CopyGameListOption>)new LinkedList<RookieMoreDialogFragment.ListOption>();
        list.add(new CopyGameListOption(gameInfo));
        return (List<RookieMoreDialogFragment.ListOption>)list;
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
    protected View inflateIntroView(final ViewGroup viewGroup) {
        return new View((Context)this.getActivity());
    }
    
    @Override
    protected void itemClicked(final GameInfo gameInfo) {
        final AnalyzeFragment analyzeFragment = new AnalyzeFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable("pgnGame", (Serializable)this._gameInfosToPGNStrings.get(gameInfo));
        analyzeFragment.setArguments(arguments);
        this.getContentPresenter().showFragment(analyzeFragment, true, true);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        if (this._uriToLoad == null) {
            Logger.getInstance().error("PGNListActivity", "Error: no uri was set when opening fragment");
            return;
        }
        final String scheme = this._uriToLoad.getScheme();
        if (scheme != null && (scheme.equals("http") || scheme.equals("https"))) {
            this.readWebUrl();
            return;
        }
        if (scheme != null && (scheme.equals("file") || scheme.equals("content"))) {
            this.readFileOrContent();
            return;
        }
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("Unknown data scheme: ");
        sb.append(scheme);
        sb.append(" in uri: ");
        sb.append(this._uriToLoad);
        instance.debug("PGNListActivity", sb.toString());
    }
    
    public void setUri(final Uri uriToLoad) {
        this._uriToLoad = uriToLoad;
    }
    
    @Override
    protected void updateListItem(final PGNGameView pgnGameView, final GameInfo gameInfo, final boolean b) {
        if (gameInfo.getWhitePlayer() != null) {
            pgnGameView.setPlayerLeftName(gameInfo.getWhitePlayer().getName());
            pgnGameView.setLeftRating(gameInfo.getWhitePlayer().getRating());
            pgnGameView.setLeftCountry(gameInfo.getWhitePlayer().getCountry());
        }
        else {
            pgnGameView.setPlayerLeftName("");
            pgnGameView.setLeftRating(null);
            pgnGameView.setLeftCountry(null);
        }
        if (gameInfo.getBlackplayer() != null) {
            pgnGameView.setPlayerRightName(gameInfo.getBlackplayer().getName());
            pgnGameView.setRightRating(gameInfo.getBlackplayer().getRating());
            pgnGameView.setRightCountry(gameInfo.getBlackplayer().getCountry());
        }
        else {
            pgnGameView.setPlayerRightName("");
            pgnGameView.setRightRating(null);
            pgnGameView.setRightCountry(null);
        }
        pgnGameView.setResultText(gameInfo.getResultStatus().getResult().getShortDescription());
        final Date date = gameInfo.getDate();
        String dateTimeText;
        if (DateUtils.isToday(date.getTime())) {
            final long n = (System.currentTimeMillis() - date.getTime()) / 60000L;
            if (n > 60L) {
                final long n2 = n / 60L;
                final StringBuilder sb = new StringBuilder();
                sb.append(n2);
                sb.append(" hours ago");
                dateTimeText = sb.toString();
            }
            else {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(n);
                sb2.append(" minutes ago");
                dateTimeText = sb2.toString();
            }
        }
        else {
            dateTimeText = this._dateFormat.format(gameInfo.getDate());
        }
        pgnGameView.setDateTimeText(dateTimeText);
    }
    
    protected class CopyGameListOption implements ListOption
    {
        private GameInfo _info;
        private String _string;
        
        public CopyGameListOption(final GameInfo info) {
            this._string = PGNListFragment.this.getString(2131690105);
            this._info = info;
        }
        
        @Override
        public void executeAction() {
            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(final Void... array) {
                    final PGNReader pgnReader = new PGNReader();
                    final GameHolder gameHolder = new GameHolder(new Game());
                    pgnReader.readSingleGame(PGNListFragment.this._gameInfosToPGNStrings.get(CopyGameListOption.this._info), gameHolder, null);
                    ServiceProvider.getInstance().getGameService().saveAnalysis(gameHolder.getRootMoveContainer().getGame());
                    return null;
                }
                
                protected void onPostExecute(final Void void1) {
                    PGNListFragment.this.hideDialogWaiting();
                    PGNListFragment.this.showAnalysisSavedToast();
                }
                
                protected void onPreExecute() {
                    PGNListFragment.this.showDialogWaiting(false, false, "", null);
                }
            }.execute((Object[])new Void[] { null });
        }
        
        @Override
        public String getString() {
            return this._string;
        }
    }
}
