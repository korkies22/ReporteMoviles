// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.playzone.engine.OpponentChooserFragment;
import android.view.View;
import android.view.View.OnClickListener;
import de.cisha.android.board.view.BoardViewFactory;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.profile.view.YourGamesView;

public abstract class YourGamesViewController implements ContentPresenterHolder
{
    private YourGamesView _view;
    
    public YourGamesViewController(final YourGamesView view, final FragmentManager fragmentManager) {
        view.getFieldView().setBoardView(BoardViewFactory.getInstance().createBoardView(view.getContext(), fragmentManager, true, false, false));
        this._view = view;
        view.getPlayNowButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final IContentPresenter contentPresenter = YourGamesViewController.this.getContentPresenter();
                if (contentPresenter != null) {
                    contentPresenter.showFragment(new OpponentChooserFragment(), true, false);
                }
            }
        });
    }
    
    public void release() {
        ServiceProvider.getInstance().getSettingsService().removeBoardObserver((SettingsService.BoardSettingObserver)this._view.getFieldView().getBoardView());
    }
    
    public void setPlayzoneData(final PlayzoneStatisticData playzoneStatisticData) {
        this._view.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                YourGamesViewController.this._view.setPlayzonGameData(playzoneStatisticData);
            }
        }, 10L);
    }
}
