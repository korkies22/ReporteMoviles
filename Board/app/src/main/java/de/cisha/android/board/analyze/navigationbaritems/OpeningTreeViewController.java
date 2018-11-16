// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import android.view.View;
import android.content.Context;
import de.cisha.android.board.analyze.view.OpeningTreeView;
import android.widget.ScrollView;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.board.IContentPresenter;

public class OpeningTreeViewController extends AbstractNavigationBarItem
{
    private IContentPresenter _contentPresenter;
    private MenuBarItem _menuBarItem;
    private MoveExecutor _moveExecutor;
    private PositionObservable _positionObservable;
    private ScrollView _treeScrollView;
    private OpeningTreeView _treeView;
    
    public OpeningTreeViewController(final IContentPresenter contentPresenter, final PositionObservable positionObservable, final MoveExecutor moveExecutor) {
        this._contentPresenter = contentPresenter;
        this._positionObservable = positionObservable;
        this._moveExecutor = moveExecutor;
    }
    
    @Override
    public View getContentView(final Context context) {
        if (this._treeView == null) {
            this._treeView = new OpeningTreeView(context, this._contentPresenter);
            this._positionObservable.addPositionObserver(this._treeView);
            this._treeView.setAdapter((OpeningTreeView.OpeningTreeViewSourceAdapter)new OpeningTreeView.OpeningTreeViewSourceAdapter() {
                @Override
                public OpeningPositionInformation getInformation(final Position position) {
                    return ServiceProvider.getInstance().getOpeningTreeService().getInformationForPosition(position);
                }
            });
            this._treeScrollView = new ScrollView(context);
            this._treeView.setMoveExecutor(this._moveExecutor);
            this._treeScrollView.addView((View)this._treeView);
            this._treeView.setOpeningTreeInformation(this._positionObservable.getPosition());
        }
        return (View)this._treeScrollView;
    }
    
    @Override
    public String getEventTrackingName() {
        return "Show Opening Tree";
    }
    
    @Override
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, context.getString(2131689530), 2131230857, 2131230858, -1)).setSelectionGroup(1);
        }
        return this._menuBarItem;
    }
    
    @Override
    public void handleClick() {
    }
}
