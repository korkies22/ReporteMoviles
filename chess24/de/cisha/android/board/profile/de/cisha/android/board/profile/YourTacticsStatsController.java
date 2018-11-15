/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.profile.ContentPresenterHolder;
import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.profile.view.YourTacticsStatsView;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.tactics.TacticsStartFragment;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;

public abstract class YourTacticsStatsController
implements ContentPresenterHolder {
    private BoardView _boardView;
    private YourTacticsStatsView _view;

    public YourTacticsStatsController(YourTacticsStatsView yourTacticsStatsView, FragmentManager fragmentManager) {
        this._view = yourTacticsStatsView;
        this._boardView = BoardViewFactory.getInstance().createBoardView(yourTacticsStatsView.getContext(), fragmentManager, true, false, false);
        yourTacticsStatsView.getFieldView().setBoardView(this._boardView);
        yourTacticsStatsView.getGotoTacticsButton().setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = YourTacticsStatsController.this.getContentPresenter();
                if (object != null) {
                    object.showFragment(new TacticsStartFragment(), true, false);
                }
            }
        });
    }

    public void release() {
        ServiceProvider.getInstance().getSettingsService().removeBoardObserver(this._boardView);
    }

    public void setTacticsData(final TacticStatisticData tacticStatisticData) {
        this._view.setTacticStatisticData(tacticStatisticData);
        this._boardView.postDelayed(new Runnable(){

            @Override
            public void run() {
                YourTacticsStatsController.this._boardView.setPosition(new Position(tacticStatisticData.getFenClassic()));
            }
        }, 10L);
    }

}
