// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.user.User;
import android.content.Context;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.service.ServiceProvider;
import android.os.Bundle;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.engine.view.EngineOfflineOpponentChooserView;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.BaseFragment;

public class EngineOfflineOpponentChooserViewFragment extends BaseFragment implements EngineOpponentChooserListener, IUserDataChangedListener
{
    private IContentPresenter _contentPresenter;
    private EngineOpponentChooserListener _listener;
    private BoardAction _premiumButtonAction;
    private StoredGameInformation _storedGameInformation;
    private EngineOfflineOpponentChooserView _view;
    
    @Override
    public void onChoosen(final TimeControl timeControl, final int n, final String s, final boolean b) {
        if (this._listener != null) {
            this._listener.onChoosen(timeControl, n, s, b);
        }
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        ServiceProvider.getInstance().getProfileDataService().addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
        this._premiumButtonAction = new BoardAction() {
            @Override
            public void perform() {
                if (EngineOfflineOpponentChooserViewFragment.this._contentPresenter != null) {
                    EngineOfflineOpponentChooserViewFragment.this._contentPresenter.showFragment(new StoreFragment(), true, true);
                }
            }
        };
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        this._storedGameInformation = ServiceProvider.getInstance().getLocalGameService().loadGame();
        (this._view = new EngineOfflineOpponentChooserView((Context)this.getActivity(), this._storedGameInformation)).setEngineOpponentChooserListener(this);
        final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        this._view.setPremiumFeatureWallEnabled(currentUserData == null || !currentUserData.isPremium(), this._premiumButtonAction);
        return (View)this._view;
    }
    
    public void setContentPresenter(final IContentPresenter contentPresenter) {
        this._contentPresenter = contentPresenter;
    }
    
    public void setEngineOpponentChooserListener(final EngineOpponentChooserListener listener) {
        this._listener = listener;
    }
    
    @Override
    public void userDataChanged(final User user) {
        if (user != null) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                @Override
                public void run() {
                    EngineOfflineOpponentChooserViewFragment.this._view.setPremiumFeatureWallEnabled(user.isPremium() ^ true, EngineOfflineOpponentChooserViewFragment.this._premiumButtonAction);
                }
            });
        }
    }
}
