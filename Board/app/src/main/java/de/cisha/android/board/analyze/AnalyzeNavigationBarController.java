// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import android.os.Bundle;
import android.view.View;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.board.analyze.view.VariationSelectionView;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Move;
import java.util.HashMap;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import java.util.Map;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MoveExecutor;
import android.widget.RelativeLayout;
import android.content.Context;
import android.view.ViewGroup;
import de.cisha.android.board.StateHolder;
import de.cisha.chess.model.MovesObserver;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;

public class AnalyzeNavigationBarController implements MenuBarObserver, MovesObserver, StateHolder
{
    private static final String MENUITEM_TAG_PREFIX = "MenuBarItem";
    private static final String SELECTED_MENU_BAR_KEY = "SelectedMenuBarItemOfTheAnalyzeController";
    private boolean _closeSubmenuOnMoveChanges;
    private ViewGroup _contentView;
    private Context _context;
    private RelativeLayout _modalViewContainer;
    private MoveExecutor _moveExecutor;
    private MoveSelector _moveSelector;
    private MenuBar _movesBar;
    private MovesObservable _movesObservable;
    private MenuBar _navigationBar;
    private Map<MenuBarItem, AnalyzeNavigationBarItem> _navigationBarItems;
    private MenuBarItem _next;
    private MenuBarItem _previous;
    private MenuBarItem _selectedItem;
    private boolean _showsVariations;
    private ViewGroup _submenuView;
    private int tagNumber;
    
    public AnalyzeNavigationBarController(final MenuBar navigationBar, final MenuBar movesBar, final ViewGroup contentView, final ViewGroup submenuView, final MovesObservable movesObservable, final MoveExecutor moveExecutor, final MoveSelector moveSelector) {
        this.tagNumber = 1;
        this._navigationBarItems = new HashMap<MenuBarItem, AnalyzeNavigationBarItem>();
        this._closeSubmenuOnMoveChanges = true;
        this._navigationBar = navigationBar;
        this._movesBar = movesBar;
        this._context = this._navigationBar.getContext();
        this._contentView = contentView;
        this._submenuView = submenuView;
        this._previous = (MenuBarItem)this._movesBar.findViewById(2131296313);
        this._next = (MenuBarItem)this._movesBar.findViewById(2131296312);
        this._moveSelector = moveSelector;
        this._moveExecutor = moveExecutor;
        (this._movesObservable = movesObservable).addMovesObserver(this);
        this.selectedMoveChanged(movesObservable.getCurrentMove());
        this._navigationBar.setObserver((MenuBar.MenuBarObserver)this);
        this._navigationBar.setPopupViewGroup(this._submenuView);
        this._movesBar.setObserver((MenuBar.MenuBarObserver)this);
        this._movesBar.setPopupViewGroup(this._submenuView);
    }
    
    private void clearSubMenuView() {
        this._navigationBar.hidePopup();
        this._movesBar.hidePopup();
        this._showsVariations = false;
    }
    
    private AnalyzeNavigationBarItem getAnalyzeNavigationBarItemForMenuItem(final MenuBarItem menuBarItem) {
        return this._navigationBarItems.get(menuBarItem);
    }
    
    private void updatePositionNavigationButtons(final Move move) {
        final boolean hasChildren = this._movesObservable.getRootMoveContainer().hasChildren();
        final boolean b = false;
        this._next.setEnabled((move != null && move.hasChildren()) || (move == null && hasChildren));
        final MenuBarItem previous = this._previous;
        boolean enabled = b;
        if (move != null) {
            enabled = true;
        }
        previous.setEnabled(enabled);
    }
    
    public void addAnalyseBarItem(final AnalyzeNavigationBarItem analyzeNavigationBarItem) {
        final MenuBarItem menuItem = analyzeNavigationBarItem.getMenuItem(this._context);
        if (menuItem.getTag() == null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("MenuBarItem");
            sb.append(this.tagNumber++);
            menuItem.setTag((Object)sb.toString());
        }
        this._navigationBarItems.put(menuItem, analyzeNavigationBarItem);
        this._navigationBar.addMenuItem(menuItem);
    }
    
    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
        this._navigationBar.post((Runnable)new Runnable() {
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
    public void menuItemClicked(final MenuBarItem selectedItem) {
        final boolean showsVariations = this._showsVariations;
        this.clearSubMenuView();
        if (selectedItem.isSelectable()) {
            final AnalyzeNavigationBarItem analyzeNavigationBarItemForMenuItem = this.getAnalyzeNavigationBarItemForMenuItem(this._selectedItem);
            if (this._selectedItem != null && this._selectedItem != analyzeNavigationBarItemForMenuItem && analyzeNavigationBarItemForMenuItem != null) {
                analyzeNavigationBarItemForMenuItem.handleDeselection();
            }
            this._selectedItem = selectedItem;
        }
        if (this._modalViewContainer != null) {
            this._modalViewContainer.removeAllViews();
        }
        final AnalyzeNavigationBarItem analyzeNavigationBarItemForMenuItem2 = this.getAnalyzeNavigationBarItemForMenuItem(selectedItem);
        if (analyzeNavigationBarItemForMenuItem2 != null) {
            final View contentView = analyzeNavigationBarItemForMenuItem2.getContentView(this._context);
            if (contentView != null) {
                if (contentView.getParent() == null) {
                    this._contentView.addView(contentView, -1, -1);
                }
                for (int i = 0; i < this._contentView.getChildCount(); ++i) {
                    final View child = this._contentView.getChildAt(i);
                    int visibility;
                    if (child == contentView) {
                        visibility = 0;
                    }
                    else {
                        visibility = 8;
                    }
                    child.setVisibility(visibility);
                }
            }
            final ArrowBottomContainerView submenuView = analyzeNavigationBarItemForMenuItem2.createSubmenuView(this._context);
            if (submenuView != null) {
                this._navigationBar.showPopup(submenuView);
            }
            analyzeNavigationBarItemForMenuItem2.handleClick();
            return;
        }
        if (selectedItem != this._next) {
            if (selectedItem == this._previous) {
                this._moveExecutor.goBackOneMove();
            }
            return;
        }
        MoveContainer moveContainer;
        if (this._movesObservable.getCurrentMove() != null) {
            moveContainer = this._movesObservable.getCurrentMove();
        }
        else {
            moveContainer = this._movesObservable.getRootMoveContainer();
        }
        if (!showsVariations && moveContainer.hasVariants()) {
            final VariationSelectionView variationSelectionView = new VariationSelectionView(this._navigationBar.getContext(), moveContainer);
            variationSelectionView.setMoveSelector(this._moveSelector);
            this._showsVariations = true;
            this._movesBar.showPopup(variationSelectionView);
            return;
        }
        this._moveExecutor.advanceOneMoveInCurrentLine();
    }
    
    @Override
    public void menuItemLongClicked(final MenuBarItem menuBarItem) {
        final AnalyzeNavigationBarItem analyzeNavigationBarItemForMenuItem = this.getAnalyzeNavigationBarItemForMenuItem(menuBarItem);
        this.clearSubMenuView();
        if (analyzeNavigationBarItemForMenuItem != null) {
            analyzeNavigationBarItemForMenuItem.handleLongClick();
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
    public void newMove(final Move move) {
        if (move != this._movesObservable.getCurrentMove()) {
            this._navigationBar.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    AnalyzeNavigationBarController.this.updatePositionNavigationButtons(AnalyzeNavigationBarController.this._movesObservable.getCurrentMove());
                }
            });
        }
    }
    
    @Override
    public void restoreState(final Bundle bundle) {
        final String string = bundle.getString("SelectedMenuBarItemOfTheAnalyzeController");
        if (string != null) {
            final MenuBarItem menuBarItem = (MenuBarItem)this._navigationBar.findViewWithTag((Object)string);
            if (menuBarItem != null) {
                menuBarItem.setSelected(true);
                this.menuItemClicked(menuBarItem);
                this._navigationBar.selectItem(menuBarItem);
            }
        }
    }
    
    @Override
    public void saveState(final Bundle bundle) {
        if (this._selectedItem != null && this._selectedItem.getTag() instanceof String) {
            bundle.putString("SelectedMenuBarItemOfTheAnalyzeController", (String)this._selectedItem.getTag());
        }
    }
    
    public void selectNavigationBarItem(final AnalyzeNavigationBarItem analyzeNavigationBarItem) {
        final MenuBarItem menuItem = analyzeNavigationBarItem.getMenuItem(this._context);
        if (menuItem != null && menuItem.isSelectable()) {
            menuItem.setSelected(true);
            this.menuItemClicked(menuItem);
            this._navigationBar.selectItem(menuItem);
        }
    }
    
    @Override
    public void selectedMoveChanged(final Move move) {
        this._navigationBar.post((Runnable)new Runnable() {
            @Override
            public void run() {
                AnalyzeNavigationBarController.this.updatePositionNavigationButtons(move);
                if (AnalyzeNavigationBarController.this._closeSubmenuOnMoveChanges) {
                    AnalyzeNavigationBarController.this.clearSubMenuView();
                }
            }
        });
    }
    
    public void setCloseSubmenuOnMoveChanges(final boolean closeSubmenuOnMoveChanges) {
        this._closeSubmenuOnMoveChanges = closeSubmenuOnMoveChanges;
    }
}
