// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import java.util.Iterator;
import de.cisha.android.board.broadcast.actions.ShowTournamentDetailsAction;
import de.cisha.android.board.broadcast.view.TournamentInfoView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import java.util.List;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.broadcast.TournamentListFragment;
import android.view.View.OnClickListener;
import de.cisha.android.board.profile.view.TournamentsWidgetView;
import android.widget.LinearLayout;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.LoadingHelper;
import android.widget.TextView;
import android.view.View;

public abstract class TournamentsWidgetController implements ContentPresenterHolder
{
    private View _buttonShowAll;
    private TextView _errorTextView;
    private LoadingHelper _loadingHelper;
    private View _progressbar;
    private ITournamentListService _tournamentService;
    private LinearLayout _tournamentsContainerView;
    private TournamentsWidgetView _view;
    
    public TournamentsWidgetController(final TournamentsWidgetView view, final ITournamentListService tournamentService, final LoadingHelper loadingHelper) {
        this._view = view;
        this._tournamentService = tournamentService;
        this._loadingHelper = loadingHelper;
        this._tournamentsContainerView = (LinearLayout)this._view.getViewGroupTournamentsList();
        this._progressbar = this._view.getViewLoadingProgressbar();
        this._errorTextView = this._view.getViewErrorText();
        this._buttonShowAll = this._view.getViewShowAllButton();
        this.setState(TournamentWidgetState.IS_LOADING);
        this._buttonShowAll.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TournamentsWidgetController.this.getContentPresenter().showFragment(new TournamentListFragment(), false, true);
            }
        });
        this.loadTournaments();
    }
    
    private void loadTournaments() {
        this._loadingHelper.addLoadable(this);
        this._tournamentService.getTournaments(0, 3, new LoadCommandCallbackWithTimeout<List<TournamentInfo>>() {
            private void notifyLoadingCompleted() {
                TournamentsWidgetController.this._loadingHelper.loadingCompleted(TournamentsWidgetController.this);
            }
            
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TournamentsWidgetController.this.setState(TournamentWidgetState.LOADING_FAILED);
                this.notifyLoadingCompleted();
            }
            
            @Override
            protected void succeded(final List<TournamentInfo> list) {
                TournamentsWidgetController.this._view.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TournamentsWidgetController.this.updateTournaments(list);
                    }
                });
                this.notifyLoadingCompleted();
            }
        });
    }
    
    private void setState(final TournamentWidgetState tournamentWidgetState) {
        this._buttonShowAll.setEnabled(true);
        switch (TournamentsWidgetController.4..SwitchMap.de.cisha.android.board.profile.TournamentsWidgetController.TournamentWidgetState[tournamentWidgetState.ordinal()]) {
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
                break;
            }
        }
        switch (TournamentsWidgetController.4..SwitchMap.de.cisha.android.board.profile.TournamentsWidgetController.TournamentWidgetState[tournamentWidgetState.ordinal()]) {
            default: {}
            case 2: {
                this._errorTextView.setText(2131690230);
            }
            case 1: {
                this._errorTextView.setText(2131690231);
            }
        }
    }
    
    private void updateTournaments(final List<TournamentInfo> list) {
        if (list.size() == 0) {
            this.setState(TournamentWidgetState.LOADING_SUCCEEDED_WITH_NO_TOURNAMENTS);
            return;
        }
        this._tournamentsContainerView.removeAllViews();
        this.setState(TournamentWidgetState.LOADING_SUCCEEDED);
        int n = 0;
        final LayoutInflater from = LayoutInflater.from(this._view.getContext());
        from.inflate(2131427444, (ViewGroup)this._tournamentsContainerView);
        for (final TournamentInfo tournamentInfo : list) {
            final TournamentInfoView tournamentInfoView = new TournamentInfoView(this._view.getContext());
            tournamentInfoView.setTournamentInfo(tournamentInfo);
            this._tournamentsContainerView.addView((View)tournamentInfoView);
            tournamentInfoView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    new ShowTournamentDetailsAction(TournamentsWidgetController.this.getContentPresenter(), tournamentInfo).perform();
                }
            });
            from.inflate(2131427444, (ViewGroup)this._tournamentsContainerView);
            if (++n >= 3) {
                break;
            }
        }
    }
    
    public void reloadTournaments() {
        this.loadTournaments();
    }
    
    private enum TournamentWidgetState
    {
        IS_LOADING, 
        LOADING_FAILED, 
        LOADING_SUCCEEDED, 
        LOADING_SUCCEEDED_WITH_NO_TOURNAMENTS;
    }
}
