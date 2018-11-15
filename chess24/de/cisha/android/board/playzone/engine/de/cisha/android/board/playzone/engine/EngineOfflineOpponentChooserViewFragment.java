/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.playzone.engine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.engine.view.EngineOfflineOpponentChooserView;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.ILocalGameService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.board.user.User;

public class EngineOfflineOpponentChooserViewFragment
extends BaseFragment
implements EngineOpponentChooserListener,
IProfileDataService.IUserDataChangedListener {
    private IContentPresenter _contentPresenter;
    private EngineOpponentChooserListener _listener;
    private BoardAction _premiumButtonAction;
    private StoredGameInformation _storedGameInformation;
    private EngineOfflineOpponentChooserView _view;

    @Override
    public void onChoosen(TimeControl timeControl, int n, String string, boolean bl) {
        if (this._listener != null) {
            this._listener.onChoosen(timeControl, n, string, bl);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ServiceProvider.getInstance().getProfileDataService().addUserDataChangedListener(this);
        this._premiumButtonAction = new BoardAction(){

            @Override
            public void perform() {
                if (EngineOfflineOpponentChooserViewFragment.this._contentPresenter != null) {
                    EngineOfflineOpponentChooserViewFragment.this._contentPresenter.showFragment(new StoreFragment(), true, true);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater object, @Nullable ViewGroup object2, @Nullable Bundle bundle) {
        this._storedGameInformation = ServiceProvider.getInstance().getLocalGameService().loadGame();
        this._view = new EngineOfflineOpponentChooserView((Context)this.getActivity(), this._storedGameInformation);
        this._view.setEngineOpponentChooserListener(this);
        object = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        object2 = this._view;
        boolean bl = object == null || !object.isPremium();
        object2.setPremiumFeatureWallEnabled(bl, this._premiumButtonAction);
        return this._view;
    }

    public void setContentPresenter(IContentPresenter iContentPresenter) {
        this._contentPresenter = iContentPresenter;
    }

    public void setEngineOpponentChooserListener(EngineOpponentChooserListener engineOpponentChooserListener) {
        this._listener = engineOpponentChooserListener;
    }

    @Override
    public void userDataChanged(final User user) {
        if (user != null) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    EngineOfflineOpponentChooserViewFragment.this._view.setPremiumFeatureWallEnabled(user.isPremium() ^ true, EngineOfflineOpponentChooserViewFragment.this._premiumButtonAction);
                }
            });
        }
    }

}
