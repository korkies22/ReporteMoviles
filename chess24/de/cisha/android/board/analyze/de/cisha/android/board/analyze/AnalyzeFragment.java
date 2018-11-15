/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.Toast
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.AbstractAnalyseFragment;
import de.cisha.android.board.analyze.AnalyzeNavigationBarController;
import de.cisha.android.board.analyze.AnalyzeSavedToast;
import de.cisha.android.board.analyze.EditGameInformationFragment;
import de.cisha.android.board.analyze.IAnalyzeDelegate;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.InfoNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.ResetToNewGameNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.SetupBoardNavigationBarItem;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.android.board.util.AndroidPGNReader;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.util.Logger;
import de.cisha.chess.util.PGNReader;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalyzeFragment
extends AbstractAnalyseFragment
implements MovesObserver {
    public static final String ARGUMENT_GAME_ID = "argGameId";
    public static final String ARGUMENT_JSON_GAME = "jsonGame";
    public static final String ARGUMENT_PGN_GAME = "pgnGame";
    private static final int REQUEST_GAME_INFO_EDIT = 34573168;
    private GameHolder _gameHolder;
    private ImageView _saveButton;
    private boolean _showMenu;

    private void activateSaveButton() {
        if (this._saveButton != null) {
            this.getActivity().runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    AnalyzeFragment.this._saveButton.setSelected(true);
                }
            });
        }
    }

    private void saveGame() {
        Game game = this.getGame();
        ServiceProvider.getInstance().getGameService().saveAnalysis(game);
        game = new Toast(this.getActivity().getApplicationContext());
        game.setDuration(0);
        game.setGravity(17, 0, -50);
        game.setView((View)new AnalyzeSavedToast((Context)this.getActivity()));
        game.show();
        this._saveButton.setSelected(false);
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this.activateSaveButton();
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public void editButtonClicked() {
        EditGameInformationFragment editGameInformationFragment = new EditGameInformationFragment();
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
    public List<View> getRightButtons(Context object) {
        this._saveButton = this.createNavbarButtonForRessource((Context)object, 2131231654);
        this._saveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AnalyzeFragment.this.saveGame();
            }
        });
        object = new LinkedList();
        object.add(this._saveButton);
        return object;
    }

    @Override
    public String getTrackingName() {
        return "Analyze";
    }

    @Override
    public void newMove(Move move) {
        this.activateSaveButton();
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getArguments();
        if (object != null) {
            Object object2 = object.getSerializable(ARGUMENT_GAME_ID);
            Object object3 = object.getString(ARGUMENT_JSON_GAME);
            object = object.getString(ARGUMENT_PGN_GAME);
            if (object2 != null) {
                AsyncTaskCompatHelper.executeOnExecutorPool(new AsyncTask<Void, Void, Game>((Serializable)object2){
                    final /* synthetic */ Serializable val$gameIdSerialized;
                    {
                        this.val$gameIdSerialized = serializable;
                    }

                    protected /* varargs */ Game doInBackground(Void ... object) {
                        try {
                            object = ServiceProvider.getInstance().getGameService().getGame((GameID)this.val$gameIdSerialized);
                            return object;
                        }
                        catch (ClassCastException classCastException) {
                            Logger.getInstance().error(AnalyzeFragment.class.getName(), JSONException.class.getName(), classCastException);
                            return null;
                        }
                    }

                    protected void onPostExecute(Game game) {
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
            if (object3 != null) {
                AsyncTaskCompatHelper.executeOnExecutorPool(new AsyncTask<Void, Void, Game>((String)object3){
                    final /* synthetic */ String val$jsonGameString;
                    {
                        this.val$jsonGameString = string;
                    }

                    protected /* varargs */ Game doInBackground(Void ... object) {
                        try {
                            object = new JSONGameParser().parseResult(new JSONObject(this.val$jsonGameString));
                            return object;
                        }
                        catch (JSONException jSONException) {
                            Logger.getInstance().debug(this.getClass().getName(), JSONException.class.getName(), (Throwable)jSONException);
                        }
                        catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                            Logger.getInstance().debug(this.getClass().getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                        }
                        return null;
                    }

                    protected void onPostExecute(Game game) {
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
            if (object != null) {
                object2 = new AndroidPGNReader();
                this.getGameHolder().setUpdateObservers(false);
                this.showDialogWaiting(false, true, "", null);
                object3 = new PGNReader.PGNReaderFinishDelegate(){

                    @Override
                    public void finishedReadingMoves() {
                        AnalyzeFragment.this.hideDialogWaiting();
                        AnalyzeFragment.this.getGameHolder().setUpdateObservers(true);
                    }
                };
                object2.readSingleGame((String)object, this.getGameHolder(), (PGNReader.PGNReaderFinishDelegate)object3);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void selectedMoveChanged(Move move) {
    }

    public void setShowsMenu(boolean bl) {
        this._showMenu = bl;
    }

    @Override
    protected void setupMenuBar(AnalyzeNavigationBarController analyzeNavigationBarController) {
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
