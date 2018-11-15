/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ScrollView
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.board.view.ScrollChromeView;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesEditor;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesObserver;

public class MoveListViewController
extends AbstractNavigationBarItem
implements MovesObserver {
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

    public MoveListViewController(Context context, MovesObservable movesObservable, MoveSelector moveSelector, MovesEditor movesEditor) {
        this._movesObservable = movesObservable;
        this._moveEditor = movesEditor;
        this._scroller = new ScrollChromeView(context);
        this._subNavBar = (ViewGroup)((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427375, this._scroller.getTopBarView());
        this._notationList = new NotationListView(context);
        this._notationList.allMovesChanged(movesObservable.getRootMoveContainer());
        movesObservable.addMovesObserver(this._notationList);
        movesObservable.addMovesObserver(this);
        this._notationList.setMoveSelector(moveSelector);
        this._notationList.setPadding(5, 0, 5, 0);
        this._scrollView = (ScrollView)this._scroller.getScrollView();
        this._scrollView.addView((View)this._notationList);
        this._deleteYesNoDialog = this._subNavBar.findViewById(2131296334);
        this._deleteYesNoDialog.setVisibility(8);
        this._deleteButton = this._subNavBar.findViewById(2131296331);
        this._deleteButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MoveListViewController.this.showDeleteDialog();
            }
        });
        this._promoteButton = this._subNavBar.findViewById(2131296335);
        this._promoteButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MoveListViewController.this._moveEditor.promoteCurrentMove();
            }
        });
        this._deleteYesNoDialog.findViewById(2131296332).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MoveListViewController.this.hideDeleteYesNoDialog();
            }
        });
        this._deleteYesNoDialog.findViewById(2131296333).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MoveListViewController.this._moveEditor.deleteCurrentMove();
            }
        });
    }

    private void hideDeleteYesNoDialog() {
        if (this._deleteYesNoDialog.getVisibility() == 0) {
            this._deleteYesNoDialog.post(new Runnable(){

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
        this._scroller.post(new Runnable(){

            @Override
            public void run() {
                int n = MoveListViewController.this._notationList.getTopPositionForMove(move);
                if (Build.VERSION.SDK_INT > 10) {
                    MoveListViewController.this._scrollView.smoothScrollTo(0, n);
                    return;
                }
                MoveListViewController.this._scrollView.scrollTo(0, n);
            }
        });
    }

    private void showDeleteDialog() {
        final Move move = this._movesObservable.getCurrentMove();
        if (move != null) {
            this._deleteYesNoDialog.post(new Runnable(){

                @Override
                public void run() {
                    MoveListViewController.this._deleteYesNoDialog.setVisibility(0);
                    MoveListViewController.this._deleteButton.setSelected(true);
                    MoveListViewController.this._notationList.markMovesWithDeletionFlag(move);
                }
            });
        }
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this.hideDeleteYesNoDialog();
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public View getContentView(Context context) {
        return this.getView();
    }

    @Override
    public String getEventTrackingName() {
        return "Show Notation List";
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getString(2131689526), 2131230842, 2131230843, -1);
            this._menuBarItem.setSelectionGroup(1);
        }
        return this._menuBarItem;
    }

    public View getView() {
        return this._scroller;
    }

    @Override
    public void handleClick() {
        this.scrollToCurrenMove();
    }

    @Override
    public void newMove(Move move) {
        this.hideDeleteYesNoDialog();
    }

    public void scrollToCurrenMove() {
        this.scrollToSelectedMove(this._movesObservable.getCurrentMove());
    }

    @Override
    public void selectedMoveChanged(Move move) {
        this.hideDeleteYesNoDialog();
        this.scrollToSelectedMove(move);
        boolean bl = false;
        boolean bl2 = move != null && (!this._useUserMoves || move.isUserMove());
        if (bl2) {
            this._scroller.activateTopbar();
        } else {
            this._scroller.deactivateTopbar();
        }
        this._deleteButton.setEnabled(bl2);
        View view = this._promoteButton;
        bl2 = bl;
        if (move != null) {
            bl2 = bl;
            if (move.isVariationStart()) {
                bl2 = true;
            }
        }
        view.setEnabled(bl2);
    }

    public void setUseUserMoves(boolean bl) {
        this._useUserMoves = bl;
        this._notationList.setMarkUserMoves(bl);
        if (bl) {
            this._promoteButton.setVisibility(8);
            return;
        }
        this._promoteButton.setVisibility(0);
    }

}
