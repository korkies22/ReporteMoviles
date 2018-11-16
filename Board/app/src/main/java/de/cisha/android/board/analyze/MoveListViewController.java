// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import de.cisha.chess.model.MoveContainer;
import android.os.Build.VERSION;
import de.cisha.chess.model.Move;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import de.cisha.chess.model.MoveSelector;
import android.content.Context;
import android.view.ViewGroup;
import de.cisha.android.board.view.ScrollChromeView;
import android.widget.ScrollView;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesEditor;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import android.view.View;
import de.cisha.chess.model.MovesObserver;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;

public class MoveListViewController extends AbstractNavigationBarItem implements MovesObserver
{
    private View _deleteButton;
    private View _deleteYesNoDialog;
    private MenuBarItem _menuBarItem;
    private MovesEditor _moveEditor;
    private MovesObservable _movesObservable;
    private NotationListView _notationList;
    private View _promoteButton;
    private ScrollView _scrollView;
    private ScrollChromeView _scroller;
    private ViewGroup _subNavBar;
    private boolean _useUserMoves;
    
    public MoveListViewController(final Context context, final MovesObservable movesObservable, final MoveSelector moveSelector, final MovesEditor moveEditor) {
        this._movesObservable = movesObservable;
        this._moveEditor = moveEditor;
        this._scroller = new ScrollChromeView(context);
        this._subNavBar = (ViewGroup)((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427375, this._scroller.getTopBarView());
        (this._notationList = new NotationListView(context)).allMovesChanged(movesObservable.getRootMoveContainer());
        movesObservable.addMovesObserver(this._notationList);
        movesObservable.addMovesObserver(this);
        this._notationList.setMoveSelector(moveSelector);
        this._notationList.setPadding(5, 0, 5, 0);
        (this._scrollView = (ScrollView)this._scroller.getScrollView()).addView((View)this._notationList);
        (this._deleteYesNoDialog = this._subNavBar.findViewById(2131296334)).setVisibility(8);
        (this._deleteButton = this._subNavBar.findViewById(2131296331)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MoveListViewController.this.showDeleteDialog();
            }
        });
        (this._promoteButton = this._subNavBar.findViewById(2131296335)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MoveListViewController.this._moveEditor.promoteCurrentMove();
            }
        });
        this._deleteYesNoDialog.findViewById(2131296332).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MoveListViewController.this.hideDeleteYesNoDialog();
            }
        });
        this._deleteYesNoDialog.findViewById(2131296333).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MoveListViewController.this._moveEditor.deleteCurrentMove();
            }
        });
    }
    
    private void hideDeleteYesNoDialog() {
        if (this._deleteYesNoDialog.getVisibility() == 0) {
            this._deleteYesNoDialog.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    MoveListViewController.this._deleteYesNoDialog.setVisibility(8);
                    MoveListViewController.this._deleteButton.setSelected(false);
                    MoveListViewController.this._notationList.unMarkDeletionFlagOfAllMoves();
                }
            });
        }
    }
    
    private void scrollToSelectedMove(final Move move) {
        this._scroller.post((Runnable)new Runnable() {
            @Override
            public void run() {
                final int topPositionForMove = MoveListViewController.this._notationList.getTopPositionForMove(move);
                if (Build.VERSION.SDK_INT > 10) {
                    MoveListViewController.this._scrollView.smoothScrollTo(0, topPositionForMove);
                    return;
                }
                MoveListViewController.this._scrollView.scrollTo(0, topPositionForMove);
            }
        });
    }
    
    private void showDeleteDialog() {
        final Move currentMove = this._movesObservable.getCurrentMove();
        if (currentMove != null) {
            this._deleteYesNoDialog.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    MoveListViewController.this._deleteYesNoDialog.setVisibility(0);
                    MoveListViewController.this._deleteButton.setSelected(true);
                    MoveListViewController.this._notationList.markMovesWithDeletionFlag(currentMove);
                }
            });
        }
    }
    
    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
        this.hideDeleteYesNoDialog();
    }
    
    @Override
    public boolean canSkipMoves() {
        return true;
    }
    
    @Override
    public View getContentView(final Context context) {
        return this.getView();
    }
    
    @Override
    public String getEventTrackingName() {
        return "Show Notation List";
    }
    
    @Override
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, context.getString(2131689526), 2131230842, 2131230843, -1)).setSelectionGroup(1);
        }
        return this._menuBarItem;
    }
    
    public View getView() {
        return (View)this._scroller;
    }
    
    @Override
    public void handleClick() {
        this.scrollToCurrenMove();
    }
    
    @Override
    public void newMove(final Move move) {
        this.hideDeleteYesNoDialog();
    }
    
    public void scrollToCurrenMove() {
        this.scrollToSelectedMove(this._movesObservable.getCurrentMove());
    }
    
    @Override
    public void selectedMoveChanged(final Move move) {
        this.hideDeleteYesNoDialog();
        this.scrollToSelectedMove(move);
        final boolean b = false;
        final boolean enabled = move != null && (!this._useUserMoves || move.isUserMove());
        if (enabled) {
            this._scroller.activateTopbar();
        }
        else {
            this._scroller.deactivateTopbar();
        }
        this._deleteButton.setEnabled(enabled);
        final View promoteButton = this._promoteButton;
        boolean enabled2 = b;
        if (move != null) {
            enabled2 = b;
            if (move.isVariationStart()) {
                enabled2 = true;
            }
        }
        promoteButton.setEnabled(enabled2);
    }
    
    public void setUseUserMoves(final boolean b) {
        this._useUserMoves = b;
        this._notationList.setMarkUserMoves(b);
        if (b) {
            this._promoteButton.setVisibility(8);
            return;
        }
        this._promoteButton.setVisibility(0);
    }
}
