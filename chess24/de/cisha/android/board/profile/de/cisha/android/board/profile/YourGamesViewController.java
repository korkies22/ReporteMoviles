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
import de.cisha.android.board.playzone.engine.OpponentChooserFragment;
import de.cisha.android.board.profile.ContentPresenterHolder;
import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.profile.view.YourGamesView;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public abstract class YourGamesViewController
implements ContentPresenterHolder {
    private YourGamesView _view;

    public YourGamesViewController(YourGamesView yourGamesView, FragmentManager fragmentManager) {
        yourGamesView.getFieldView().setBoardView((BoardView)BoardViewFactory.getInstance().createBoardView(yourGamesView.getContext(), fragmentManager, true, false, false));
        this._view = yourGamesView;
        yourGamesView.getPlayNowButton().setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = YourGamesViewController.this.getContentPresenter();
                if (object != null) {
                    object.showFragment(new OpponentChooserFragment(), true, false);
                }
            }
        });
    }

    public void release() {
        ServiceProvider.getInstance().getSettingsService().removeBoardObserver(this._view.getFieldView().getBoardView());
    }

    public void setPlayzoneData(final PlayzoneStatisticData playzoneStatisticData) {
        this._view.postDelayed(new Runnable(){

            @Override
            public void run() {
                YourGamesViewController.this._view.setPlayzonGameData(playzoneStatisticData);
            }
        }, 10L);
    }

}
