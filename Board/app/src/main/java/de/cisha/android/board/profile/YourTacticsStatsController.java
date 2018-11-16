// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import de.cisha.chess.model.position.Position;
import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.tactics.TacticsStartFragment;
import android.view.View;
import android.view.View.OnClickListener;
import de.cisha.android.board.view.BoardViewFactory;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.profile.view.YourTacticsStatsView;
import de.cisha.android.board.view.BoardView;

public abstract class YourTacticsStatsController implements ContentPresenterHolder
{
    private BoardView _boardView;
    private YourTacticsStatsView _view;
    
    public YourTacticsStatsController(final YourTacticsStatsView view, final FragmentManager fragmentManager) {
        this._view = view;
        this._boardView = BoardViewFactory.getInstance().createBoardView(view.getContext(), fragmentManager, true, false, false);
        view.getFieldView().setBoardView(this._boardView);
        view.getGotoTacticsButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final IContentPresenter contentPresenter = YourTacticsStatsController.this.getContentPresenter();
                if (contentPresenter != null) {
                    contentPresenter.showFragment(new TacticsStartFragment(), true, false);
                }
            }
        });
    }
    
    public void release() {
        ServiceProvider.getInstance().getSettingsService().removeBoardObserver((SettingsService.BoardSettingObserver)this._boardView);
    }
    
    public void setTacticsData(final TacticStatisticData tacticStatisticData) {
        this._view.setTacticStatisticData(tacticStatisticData);
        this._boardView.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                YourTacticsStatsController.this._boardView.setPosition(new Position(tacticStatisticData.getFenClassic()));
            }
        }, 10L);
    }
}
