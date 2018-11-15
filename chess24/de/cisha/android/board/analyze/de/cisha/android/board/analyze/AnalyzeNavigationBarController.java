/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.RelativeLayout
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.view.VariationSelectionView;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesObserver;
import java.util.HashMap;
import java.util.Map;

public class AnalyzeNavigationBarController
implements MenuBar.MenuBarObserver,
MovesObserver,
StateHolder {
    private static final String MENUITEM_TAG_PREFIX = "MenuBarItem";
    private static final String SELECTED_MENU_BAR_KEY = "SelectedMenuBarItemOfTheAnalyzeController";
    private boolean _closeSubmenuOnMoveChanges = true;
    private ViewGroup _contentView;
    private Context _context;
    private RelativeLayout _modalViewContainer;
    private MoveExecutor _moveExecutor;
    private MoveSelector _moveSelector;
    private MenuBar _movesBar;
    private MovesObservable _movesObservable;
    private MenuBar _navigationBar;
    private Map<MenuBarItem, AnalyzeNavigationBarItem> _navigationBarItems = new HashMap<MenuBarItem, AnalyzeNavigationBarItem>();
    private MenuBarItem _next;
    private MenuBarItem _previous;
    private MenuBarItem _selectedItem;
    private boolean _showsVariations;
    private ViewGroup _submenuView;
    private int tagNumber = 1;

    public AnalyzeNavigationBarController(MenuBar menuBar, MenuBar menuBar2, ViewGroup viewGroup, ViewGroup viewGroup2, MovesObservable movesObservable, MoveExecutor moveExecutor, MoveSelector moveSelector) {
        this._navigationBar = menuBar;
        this._movesBar = menuBar2;
        this._context = this._navigationBar.getContext();
        this._contentView = viewGroup;
        this._submenuView = viewGroup2;
        this._previous = (MenuBarItem)this._movesBar.findViewById(2131296313);
        this._next = (MenuBarItem)this._movesBar.findViewById(2131296312);
        this._moveSelector = moveSelector;
        this._moveExecutor = moveExecutor;
        this._movesObservable = movesObservable;
        movesObservable.addMovesObserver(this);
        this.selectedMoveChanged(movesObservable.getCurrentMove());
        this._navigationBar.setObserver(this);
        this._navigationBar.setPopupViewGroup(this._submenuView);
        this._movesBar.setObserver(this);
        this._movesBar.setPopupViewGroup(this._submenuView);
    }

    private void clearSubMenuView() {
        this._navigationBar.hidePopup();
        this._movesBar.hidePopup();
        this._showsVariations = false;
    }

    private AnalyzeNavigationBarItem getAnalyzeNavigationBarItemForMenuItem(MenuBarItem menuBarItem) {
        return this._navigationBarItems.get((Object)menuBarItem);
    }

    private void updatePositionNavigationButtons(Move move) {
        boolean bl = this._movesObservable.getRootMoveContainer().hasChildren();
        boolean bl2 = false;
        bl = move != null && move.hasChildren() || move == null && bl;
        this._next.setEnabled(bl);
        MenuBarItem menuBarItem = this._previous;
        bl = bl2;
        if (move != null) {
            bl = true;
        }
        menuBarItem.setEnabled(bl);
    }

    public void addAnalyseBarItem(AnalyzeNavigationBarItem analyzeNavigationBarItem) {
        MenuBarItem menuBarItem = analyzeNavigationBarItem.getMenuItem(this._context);
        if (menuBarItem.getTag() == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MENUITEM_TAG_PREFIX);
            int n = this.tagNumber;
            this.tagNumber = n + 1;
            stringBuilder.append(n);
            menuBarItem.setTag((Object)stringBuilder.toString());
        }
        this._navigationBarItems.put(menuBarItem, analyzeNavigationBarItem);
        this._navigationBar.addMenuItem(menuBarItem);
    }

    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
        this._navigationBar.post(new Runnable(){

            @Override
            public void run() {
                AnalyzeNavigationBarController.this._next.setEnabled(moveContainer.hasChildren());
                AnalyzeNavigationBarController.this._previous.setEnabled(false);
                if (AnalyzeNavigationBarController.this._closeSubmenuOnMoveChanges) {
                    AnalyzeNavigationBarController.this.clearSubMenuView();
                }
            }
        });
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public void menuItemClicked(MenuBarItem object) {
        AnalyzeNavigationBarItem analyzeNavigationBarItem;
        boolean bl = this._showsVariations;
        this.clearSubMenuView();
        if (object.isSelectable()) {
            analyzeNavigationBarItem = this.getAnalyzeNavigationBarItemForMenuItem(this._selectedItem);
            if (this._selectedItem != null && this._selectedItem != analyzeNavigationBarItem && analyzeNavigationBarItem != null) {
                analyzeNavigationBarItem.handleDeselection();
            }
            this._selectedItem = object;
        }
        if (this._modalViewContainer != null) {
            this._modalViewContainer.removeAllViews();
        }
        if ((analyzeNavigationBarItem = this.getAnalyzeNavigationBarItemForMenuItem((MenuBarItem)((Object)object))) != null) {
            object = analyzeNavigationBarItem.getContentView(this._context);
            if (object != null) {
                if (object.getParent() == null) {
                    this._contentView.addView((View)object, -1, -1);
                }
                for (int i = 0; i < this._contentView.getChildCount(); ++i) {
                    View view = this._contentView.getChildAt(i);
                    int n = view == object ? 0 : 8;
                    view.setVisibility(n);
                }
            }
            if ((object = analyzeNavigationBarItem.createSubmenuView(this._context)) != null) {
                this._navigationBar.showPopup((ArrowBottomContainerView)((Object)object));
            }
            analyzeNavigationBarItem.handleClick();
            return;
        }
        if (object == this._next) {
            object = this._movesObservable.getCurrentMove() != null ? this._movesObservable.getCurrentMove() : this._movesObservable.getRootMoveContainer();
            if (!bl && object.hasVariants()) {
                object = new VariationSelectionView(this._navigationBar.getContext(), (MoveContainer)object);
                object.setMoveSelector(this._moveSelector);
                this._showsVariations = true;
                this._movesBar.showPopup((ArrowBottomContainerView)((Object)object));
                return;
            }
            this._moveExecutor.advanceOneMoveInCurrentLine();
            return;
        }
        if (object == this._previous) {
            this._moveExecutor.goBackOneMove();
        }
    }

    @Override
    public void menuItemLongClicked(MenuBarItem menuBarItem) {
        AnalyzeNavigationBarItem analyzeNavigationBarItem = this.getAnalyzeNavigationBarItemForMenuItem(menuBarItem);
        this.clearSubMenuView();
        if (analyzeNavigationBarItem != null) {
            analyzeNavigationBarItem.handleLongClick();
            return;
        }
        if (menuBarItem == this._previous) {
            this._moveExecutor.gotoStartingPosition();
            return;
        }
        if (menuBarItem == this._next) {
            this._moveExecutor.gotoEndingPosition();
        }
    }

    @Override
    public void newMove(Move move) {
        if (move != this._movesObservable.getCurrentMove()) {
            this._navigationBar.post(new Runnable(){

                @Override
                public void run() {
                    AnalyzeNavigationBarController.this.updatePositionNavigationButtons(AnalyzeNavigationBarController.this._movesObservable.getCurrentMove());
                }
            });
        }
    }

    @Override
    public void restoreState(Bundle object) {
        if ((object = object.getString(SELECTED_MENU_BAR_KEY)) != null && (object = (MenuBarItem)this._navigationBar.findViewWithTag(object)) != null) {
            object.setSelected(true);
            this.menuItemClicked((MenuBarItem)((Object)object));
            this._navigationBar.selectItem((MenuBarItem)((Object)object));
        }
    }

    @Override
    public void saveState(Bundle bundle) {
        if (this._selectedItem != null && this._selectedItem.getTag() instanceof String) {
            bundle.putString(SELECTED_MENU_BAR_KEY, (String)this._selectedItem.getTag());
        }
    }

    public void selectNavigationBarItem(AnalyzeNavigationBarItem object) {
        if ((object = object.getMenuItem(this._context)) != null && object.isSelectable()) {
            object.setSelected(true);
            this.menuItemClicked((MenuBarItem)((Object)object));
            this._navigationBar.selectItem((MenuBarItem)((Object)object));
        }
    }

    @Override
    public void selectedMoveChanged(final Move move) {
        this._navigationBar.post(new Runnable(){

            @Override
            public void run() {
                AnalyzeNavigationBarController.this.updatePositionNavigationButtons(move);
                if (AnalyzeNavigationBarController.this._closeSubmenuOnMoveChanges) {
                    AnalyzeNavigationBarController.this.clearSubMenuView();
                }
            }
        });
    }

    public void setCloseSubmenuOnMoveChanges(boolean bl) {
        this._closeSubmenuOnMoveChanges = bl;
    }

}
