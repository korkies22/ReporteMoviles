/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.ScrollView
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.board.analyze.view.OpeningTreeView;
import de.cisha.android.board.service.IOpeningTreeService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.PositionObserver;

public class OpeningTreeViewController
extends AbstractNavigationBarItem {
    private IContentPresenter _contentPresenter;
    private MenuBarItem _menuBarItem;
    private MoveExecutor _moveExecutor;
    private PositionObservable _positionObservable;
    private ScrollView _treeScrollView;
    private OpeningTreeView _treeView;

    public OpeningTreeViewController(IContentPresenter iContentPresenter, PositionObservable positionObservable, MoveExecutor moveExecutor) {
        this._contentPresenter = iContentPresenter;
        this._positionObservable = positionObservable;
        this._moveExecutor = moveExecutor;
    }

    @Override
    public View getContentView(Context context) {
        if (this._treeView == null) {
            this._treeView = new OpeningTreeView(context, this._contentPresenter);
            this._positionObservable.addPositionObserver(this._treeView);
            this._treeView.setAdapter(new OpeningTreeView.OpeningTreeViewSourceAdapter(){

                @Override
                public OpeningPositionInformation getInformation(Position position) {
                    return ServiceProvider.getInstance().getOpeningTreeService().getInformationForPosition(position);
                }
            });
            this._treeScrollView = new ScrollView(context);
            this._treeView.setMoveExecutor(this._moveExecutor);
            this._treeScrollView.addView((View)this._treeView);
            this._treeView.setOpeningTreeInformation(this._positionObservable.getPosition());
        }
        return this._treeScrollView;
    }

    @Override
    public String getEventTrackingName() {
        return "Show Opening Tree";
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getString(2131689530), 2131230857, 2131230858, -1);
            this._menuBarItem.setSelectionGroup(1);
        }
        return this._menuBarItem;
    }

    @Override
    public void handleClick() {
    }

}
