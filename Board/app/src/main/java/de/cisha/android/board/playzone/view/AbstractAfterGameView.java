// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import java.util.Iterator;
import android.view.View;
import android.view.View.OnClickListener;
import de.cisha.android.board.playzone.model.GameEndMessageHelper;
import java.util.HashMap;
import java.util.TreeMap;
import android.view.ViewGroup;
import de.cisha.chess.model.GameEndReason;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.ModelHolder;
import android.content.Context;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import java.util.Map;
import de.cisha.android.board.modalfragments.RematchDialogFragment;
import android.support.v4.app.FragmentManager;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import android.widget.RelativeLayout;

public abstract class AbstractAfterGameView extends RelativeLayout implements OnDialogCloseListener, MenuBarObserver
{
    private boolean _canRematch;
    private FragmentManager _fragmentManager;
    private RematchDialogFragment _infoDialogFragment;
    protected Map<AfterGameCategory, ArrowBottomContainerView> _mapCat2View;
    protected Map<MenuBarItem, AfterGameCategory> _mapItem2Cat;
    private MenuBar _menuBar;
    private AfterGameCategory _selectAfter;
    
    public AbstractAfterGameView(final Context context, final ModelHolder<AfterGameInformation> modelHolder, final FragmentManager fragmentManager, final boolean b) {
        super(context);
        this._fragmentManager = fragmentManager;
        this._canRematch = (modelHolder.getModel().getStatus().getReason() != GameEndReason.ABORTED);
        this.initLayout();
        if (b) {
            this.initScoreView(modelHolder);
        }
    }
    
    private AfterGameCategory getCatForItem(final MenuBarItem menuBarItem) {
        return this._mapItem2Cat.get(menuBarItem);
    }
    
    private String getScoreText(final float n) {
        if (n == 0.0f) {
            return "0";
        }
        if (n < 1.0f) {
            return "&frac12;";
        }
        final double n2 = n;
        final double floor = Math.floor(n2);
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append((int)floor);
        String s;
        if (n2 - floor == 0.5) {
            s = "&frac12;";
        }
        else {
            s = "";
        }
        sb.append(s);
        return sb.toString();
    }
    
    private void initItemDrawables() {
        final AfterGameCategory[] values = AfterGameCategory.values();
        for (int i = 0; i < values.length; ++i) {
            final AfterGameCategory afterGameCategory = values[i];
            final MenuBarItem menuBarItem = (MenuBarItem)this.findViewById(afterGameCategory.getResId());
            menuBarItem.setIconDrawablesForStates(this.getResources().getDrawable(afterGameCategory.getResIdItemDrawable()), this.getResources().getDrawable(afterGameCategory.getResIdItemDrawableActive()), this.getResources().getDrawable(afterGameCategory.getResIdItemDrawableDisabled()));
            menuBarItem.setTitle(afterGameCategory.getName(this.getContext()));
            this._mapItem2Cat.put(menuBarItem, afterGameCategory);
        }
    }
    
    private void initLayout() {
        inflate(this.getContext(), 2131427486, (ViewGroup)this);
        this._mapCat2View = new TreeMap<AfterGameCategory, ArrowBottomContainerView>();
        this._mapItem2Cat = new HashMap<MenuBarItem, AfterGameCategory>();
        this.initItemDrawables();
        final ViewGroup popupViewGroup = (ViewGroup)this.findViewById(2131296682);
        (this._menuBar = (MenuBar)this.findViewById(2131296689)).setObserver((MenuBar.MenuBarObserver)this);
        this._menuBar.setPopupViewGroup(popupViewGroup);
        this.findViewById(AfterGameCategory.REMATCH.getResId()).setEnabled(this._canRematch);
    }
    
    private void initScoreView(final ModelHolder<AfterGameInformation> modelHolder) {
        final AfterGameInformation afterGameInformation = modelHolder.getModel();
        final String gameEndTitleMessage = new GameEndMessageHelper(this.getResources()).getGameEndTitleMessage(afterGameInformation.getStatus(), afterGameInformation.playerIsWhite());
        (this._infoDialogFragment = RematchDialogFragment.createInstance(modelHolder, this.waitForResult())).setOnRematchButtonClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractAfterGameView.this.rematchButtonClicked();
            }
        });
        this._infoDialogFragment.setOnDialogCloseListener((AbstractDialogFragment.OnDialogCloseListener)this);
        this._infoDialogFragment.setTitle(gameEndTitleMessage);
        if (!this._canRematch) {
            this._infoDialogFragment.deactivateRematchButton();
        }
        this._infoDialogFragment.show(this._fragmentManager, "");
    }
    
    private void selectDialogFor(final AfterGameCategory afterGameCategory, final MenuBarItem menuBarItem) {
        if (afterGameCategory != null) {
            ArrowBottomContainerView arrowBottomContainerView;
            if ((arrowBottomContainerView = this._mapCat2View.get(afterGameCategory)) == null) {
                final ArrowBottomContainerView dialog = this.getDialogFor(afterGameCategory);
                if ((arrowBottomContainerView = dialog) != null) {
                    this._mapCat2View.put(afterGameCategory, dialog);
                    arrowBottomContainerView = dialog;
                }
            }
            if (arrowBottomContainerView != null) {
                if (arrowBottomContainerView.getParent() == null) {
                    this._menuBar.showPopup(arrowBottomContainerView, menuBarItem);
                    return;
                }
                this._menuBar.hidePopup();
                this._menuBar.selectItem(null);
            }
            else {
                this._menuBar.hidePopup();
            }
        }
    }
    
    protected void closeInfoDialog() {
        if (this._infoDialogFragment != null && this._infoDialogFragment.isVisible()) {
            this._infoDialogFragment.dismiss();
        }
    }
    
    protected void deactivateRematch() {
        if (this._infoDialogFragment != null) {
            this._infoDialogFragment.deactivateRematchButton();
        }
    }
    
    protected abstract ArrowBottomContainerView getDialogFor(final AfterGameCategory p0);
    
    protected FragmentManager getFragmentManager() {
        return this._fragmentManager;
    }
    
    public void menuItemClicked(final MenuBarItem menuBarItem) {
        final AfterGameCategory catForItem = this.getCatForItem(menuBarItem);
        if (catForItem != null) {
            this.selectCategory(catForItem);
        }
    }
    
    public void menuItemLongClicked(final MenuBarItem menuBarItem) {
    }
    
    public void onDialogClosed() {
        if (this._selectAfter != null) {
            this.selectCategory(this._selectAfter);
        }
    }
    
    public abstract void rematchButtonClicked();
    
    protected void selectCategory(final AfterGameCategory afterGameCategory) {
        if (afterGameCategory == null) {
            this._menuBar.hidePopup();
        }
        for (final Map.Entry<MenuBarItem, AfterGameCategory> entry : this._mapItem2Cat.entrySet()) {
            if (entry.getValue() == afterGameCategory) {
                this.selectDialogFor(afterGameCategory, entry.getKey());
                break;
            }
        }
    }
    
    public abstract boolean waitForResult();
    
    protected enum AfterGameCategory
    {
        ANALYZE(2131690114, 2131296690, 2131231551, 2131231552), 
        NEW_GAME(2131690115, 2131296693, 2131231555, 2131231556), 
        REMATCH(2131690116, 2131296694, 2131231558, 2131231559, 2131231557);
        
        private int _nameResourceId;
        private int _resId;
        private int _resIdItemDrawable;
        private int _resIdItemDrawableActive;
        private int _resIdItemDrawableDisabled;
        
        private AfterGameCategory(final int n2, final int n3, final int n4, final int n5) {
            this(n2, n3, n4, n5, -1);
        }
        
        private AfterGameCategory(final int nameResourceId, final int resId, final int resIdItemDrawable, final int resIdItemDrawableActive, final int resIdItemDrawableDisabled) {
            this._resIdItemDrawableDisabled = -1;
            this._nameResourceId = nameResourceId;
            this._resId = resId;
            this._resIdItemDrawable = resIdItemDrawable;
            this._resIdItemDrawableActive = resIdItemDrawableActive;
            this._resIdItemDrawableDisabled = resIdItemDrawableDisabled;
        }
        
        public String getName(final Context context) {
            return context.getResources().getString(this._nameResourceId);
        }
        
        public int getResId() {
            return this._resId;
        }
        
        public int getResIdItemDrawable() {
            return this._resIdItemDrawable;
        }
        
        public int getResIdItemDrawableActive() {
            return this._resIdItemDrawableActive;
        }
        
        public int getResIdItemDrawableDisabled() {
            if (this._resIdItemDrawableDisabled == -1) {
                return this._resIdItemDrawable;
            }
            return this._resIdItemDrawableDisabled;
        }
    }
}
