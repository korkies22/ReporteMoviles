// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import de.cisha.android.board.analyze.navigationbaritems.SetupBoardNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.ResetToNewGameNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.analyze.navigationbaritems.InfoNavigationBarItem;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.chess.util.PGNReader;
import de.cisha.android.board.util.AndroidPGNReader;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.GameID;
import java.io.Serializable;
import android.os.AsyncTask;
import android.os.Bundle;
import de.cisha.chess.model.Move;
import java.util.LinkedList;
import android.view.View.OnClickListener;
import java.util.List;
import de.cisha.chess.model.Game;
import de.cisha.android.board.AbstractContentFragment;
import android.support.v4.app.Fragment;
import de.cisha.chess.model.MoveContainer;
import android.view.View;
import android.content.Context;
import android.widget.Toast;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import android.widget.ImageView;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MovesObserver;

public class AnalyzeFragment extends AbstractAnalyseFragment implements MovesObserver
{
    public static final String ARGUMENT_GAME_ID = "argGameId";
    public static final String ARGUMENT_JSON_GAME = "jsonGame";
    public static final String ARGUMENT_PGN_GAME = "pgnGame";
    private static final int REQUEST_GAME_INFO_EDIT = 34573168;
    private GameHolder _gameHolder;
    private ImageView _saveButton;
    private boolean _showMenu;
    
    private void activateSaveButton() {
        if (this._saveButton != null) {
            this.getActivity().runOnUiThread((Runnable)new Runnable() {
                @Override
                public void run() {
                    AnalyzeFragment.this._saveButton.setSelected(true);
                }
            });
        }
    }
    
    private void saveGame() {
        ServiceProvider.getInstance().getGameService().saveAnalysis(this.getGame());
        final Toast toast = new Toast(this.getActivity().getApplicationContext());
        toast.setDuration(0);
        toast.setGravity(17, 0, -50);
        toast.setView((View)new AnalyzeSavedToast((Context)this.getActivity()));
        toast.show();
        this._saveButton.setSelected(false);
    }
    
    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
        this.activateSaveButton();
    }
    
    @Override
    public boolean canSkipMoves() {
        return true;
    }
    
    @Override
    public void editButtonClicked() {
        final EditGameInformationFragment editGameInformationFragment = new EditGameInformationFragment();
        editGameInformationFragment.setTargetFragment(this, 34573168);
        editGameInformationFragment.setGame(this.getGame());
        this.getContentPresenter().showFragment(editGameInformationFragment, false, true);
    }
    
    @Override
    protected GameHolder getGameHolder() {
        if (this._gameHolder == null) {
            this._gameHolder = new GameHolder(new Game());
        }
        return this._gameHolder;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        (this._saveButton = this.createNavbarButtonForRessource(context, 2131231654)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AnalyzeFragment.this.saveGame();
            }
        });
        final LinkedList<ImageView> list = (LinkedList<ImageView>)new LinkedList<View>();
        list.add((View)this._saveButton);
        return (List<View>)list;
    }
    
    @Override
    public String getTrackingName() {
        return "Analyze";
    }
    
    @Override
    public void newMove(final Move move) {
        this.activateSaveButton();
    }
    
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        arguments = this.getArguments();
        if (arguments != null) {
            final Serializable serializable = arguments.getSerializable("argGameId");
            final String string = arguments.getString("jsonGame");
            final String string2 = arguments.getString("pgnGame");
            if (serializable != null) {
                AsyncTaskCompatHelper.executeOnExecutorPool((android.os.AsyncTask<Void, Object, Object>)new AsyncTask<Void, Void, Game>() {
                    protected Game doInBackground(final Void... array) {
                        try {
                            return ServiceProvider.getInstance().getGameService().getGame((GameID)serializable);
                        }
                        catch (ClassCastException ex) {
                            Logger.getInstance().error(AnalyzeFragment.class.getName(), JSONException.class.getName(), ex);
                            return null;
                        }
                    }
                    
                    protected void onPostExecute(final Game game) {
                        if (game != null) {
                            AnalyzeFragment.this.setGame(game);
                        }
                        AnalyzeFragment.this.hideDialogWaiting();
                    }
                    
                    protected void onPreExecute() {
                        AnalyzeFragment.this.showDialogWaiting(false, true, "", null);
                    }
                }, new Void[0]);
                return;
            }
            if (string != null) {
                AsyncTaskCompatHelper.executeOnExecutorPool((android.os.AsyncTask<Void, Object, Object>)new AsyncTask<Void, Void, Game>() {
                    protected Game doInBackground(final Void... array) {
                        try {
                            return new JSONGameParser().parseResult(new JSONObject(string));
                        }
                        catch (JSONException ex) {
                            Logger.getInstance().debug(this.getClass().getName(), JSONException.class.getName(), (Throwable)ex);
                        }
                        catch (InvalidJsonForObjectException ex2) {
                            Logger.getInstance().debug(this.getClass().getName(), InvalidJsonForObjectException.class.getName(), ex2);
                        }
                        return null;
                    }
                    
                    protected void onPostExecute(final Game game) {
                        if (game != null) {
                            AnalyzeFragment.this.setGame(game);
                        }
                        AnalyzeFragment.this.hideDialogWaiting();
                    }
                    
                    protected void onPreExecute() {
                        AnalyzeFragment.this.showDialogWaiting(false, true, "", null);
                    }
                }, new Void[0]);
                return;
            }
            if (string2 != null) {
                final AndroidPGNReader androidPGNReader = new AndroidPGNReader();
                this.getGameHolder().setUpdateObservers(false);
                this.showDialogWaiting(false, true, "", null);
                androidPGNReader.readSingleGame(string2, this.getGameHolder(), (PGNReader.PGNReaderFinishDelegate)new PGNReader.PGNReaderFinishDelegate() {
                    @Override
                    public void finishedReadingMoves() {
                        AnalyzeFragment.this.hideDialogWaiting();
                        AnalyzeFragment.this.getGameHolder().setUpdateObservers(true);
                    }
                });
            }
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }
    
    @Override
    public void selectedMoveChanged(final Move move) {
    }
    
    public void setShowsMenu(final boolean showMenu) {
        this._showMenu = showMenu;
    }
    
    @Override
    protected void setupMenuBar(final AnalyzeNavigationBarController analyzeNavigationBarController) {
        super.setupMenuBar(analyzeNavigationBarController);
        analyzeNavigationBarController.addAnalyseBarItem(new InfoNavigationBarItem(this.getGameHolder(), this, this.getContentPresenter()));
        analyzeNavigationBarController.addAnalyseBarItem(new ResetToNewGameNavigationBarItem(this.getGameHolder()));
        analyzeNavigationBarController.addAnalyseBarItem(new SetupBoardNavigationBarItem(this));
    }
    
    @Override
    public boolean showMenu() {
        return this._showMenu;
    }
}
