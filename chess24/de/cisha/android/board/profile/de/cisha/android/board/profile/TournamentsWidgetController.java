/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.broadcast.actions.ShowTournamentDetailsAction;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.view.TournamentInfoView;
import de.cisha.android.board.profile.ContentPresenterHolder;
import de.cisha.android.board.profile.view.TournamentsWidgetView;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public abstract class TournamentsWidgetController
implements ContentPresenterHolder {
    private View _buttonShowAll;
    private TextView _errorTextView;
    private LoadingHelper _loadingHelper;
    private View _progressbar;
    private ITournamentListService _tournamentService;
    private LinearLayout _tournamentsContainerView;
    private TournamentsWidgetView _view;

    public TournamentsWidgetController(TournamentsWidgetView tournamentsWidgetView, ITournamentListService iTournamentListService, LoadingHelper loadingHelper) {
        this._view = tournamentsWidgetView;
        this._tournamentService = iTournamentListService;
        this._loadingHelper = loadingHelper;
        this._tournamentsContainerView = (LinearLayout)this._view.getViewGroupTournamentsList();
        this._progressbar = this._view.getViewLoadingProgressbar();
        this._errorTextView = this._view.getViewErrorText();
        this._buttonShowAll = this._view.getViewShowAllButton();
        this.setState(TournamentWidgetState.IS_LOADING);
        this._buttonShowAll.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = new TournamentListFragment();
                TournamentsWidgetController.this.getContentPresenter().showFragment((AbstractContentFragment)object, false, true);
            }
        });
        this.loadTournaments();
    }

    private void loadTournaments() {
        this._loadingHelper.addLoadable(this);
        this._tournamentService.getTournaments(0, 3, (LoadCommandCallback<List<TournamentInfo>>)new LoadCommandCallbackWithTimeout<List<TournamentInfo>>(){

            private void notifyLoadingCompleted() {
                TournamentsWidgetController.this._loadingHelper.loadingCompleted(TournamentsWidgetController.this);
            }

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TournamentsWidgetController.this.setState(TournamentWidgetState.LOADING_FAILED);
                this.notifyLoadingCompleted();
            }

            @Override
            protected void succeded(final List<TournamentInfo> list) {
                TournamentsWidgetController.this._view.post(new Runnable(){

                    @Override
                    public void run() {
                        TournamentsWidgetController.this.updateTournaments(list);
                    }
                });
                this.notifyLoadingCompleted();
            }

        });
    }

    private void setState(TournamentWidgetState tournamentWidgetState) {
        this._buttonShowAll.setEnabled(true);
        switch (.$SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState[tournamentWidgetState.ordinal()]) {
            default: {
                break;
            }
            case 4: {
                this._tournamentsContainerView.setVisibility(0);
                this._progressbar.setVisibility(8);
                this._errorTextView.setVisibility(4);
                break;
            }
            case 3: {
                this._tournamentsContainerView.setVisibility(4);
                this._progressbar.setVisibility(0);
                this._errorTextView.setVisibility(4);
                break;
            }
            case 1: {
                this._buttonShowAll.setEnabled(false);
            }
            case 2: {
                this._tournamentsContainerView.setVisibility(4);
                this._progressbar.setVisibility(8);
                this._errorTextView.setVisibility(0);
            }
        }
        switch (.$SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState[tournamentWidgetState.ordinal()]) {
            default: {
                return;
            }
            case 2: {
                this._errorTextView.setText(2131690230);
                return;
            }
            case 1: 
        }
        this._errorTextView.setText(2131690231);
    }

    private void updateTournaments(List<TournamentInfo> object) {
        if (object.size() == 0) {
            this.setState(TournamentWidgetState.LOADING_SUCCEEDED_WITH_NO_TOURNAMENTS);
            return;
        }
        this._tournamentsContainerView.removeAllViews();
        this.setState(TournamentWidgetState.LOADING_SUCCEEDED);
        int n = 0;
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this._view.getContext());
        layoutInflater.inflate(2131427444, (ViewGroup)this._tournamentsContainerView);
        object = object.iterator();
        while (object.hasNext()) {
            int n2;
            final TournamentInfo tournamentInfo = (TournamentInfo)object.next();
            TournamentInfoView tournamentInfoView = new TournamentInfoView(this._view.getContext());
            tournamentInfoView.setTournamentInfo(tournamentInfo);
            this._tournamentsContainerView.addView((View)tournamentInfoView);
            tournamentInfoView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    new ShowTournamentDetailsAction(TournamentsWidgetController.this.getContentPresenter(), tournamentInfo).perform();
                }
            });
            layoutInflater.inflate(2131427444, (ViewGroup)this._tournamentsContainerView);
            n = n2 = n + 1;
            if (n2 < 3) continue;
        }
    }

    public void reloadTournaments() {
        this.loadTournaments();
    }

    private static enum TournamentWidgetState {
        IS_LOADING,
        LOADING_FAILED,
        LOADING_SUCCEEDED,
        LOADING_SUCCEEDED_WITH_NO_TOURNAMENTS;
        

        private TournamentWidgetState() {
        }
    }

}
