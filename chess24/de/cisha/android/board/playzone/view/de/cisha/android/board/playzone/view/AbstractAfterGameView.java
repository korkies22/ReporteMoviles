/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.RelativeLayout
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.modalfragments.RematchDialogFragment;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.model.GameEndMessageHelper;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class AbstractAfterGameView
extends RelativeLayout
implements AbstractDialogFragment.OnDialogCloseListener,
MenuBar.MenuBarObserver {
    private boolean _canRematch;
    private FragmentManager _fragmentManager;
    private RematchDialogFragment _infoDialogFragment;
    protected Map<AfterGameCategory, ArrowBottomContainerView> _mapCat2View;
    protected Map<MenuBarItem, AfterGameCategory> _mapItem2Cat;
    private MenuBar _menuBar;
    private AfterGameCategory _selectAfter;

    public AbstractAfterGameView(Context context, ModelHolder<AfterGameInformation> modelHolder, FragmentManager fragmentManager, boolean bl) {
        super(context);
        this._fragmentManager = fragmentManager;
        boolean bl2 = modelHolder.getModel().getStatus().getReason() != GameEndReason.ABORTED;
        this._canRematch = bl2;
        this.initLayout();
        if (bl) {
            this.initScoreView(modelHolder);
        }
    }

    private AfterGameCategory getCatForItem(MenuBarItem menuBarItem) {
        return this._mapItem2Cat.get((Object)menuBarItem);
    }

    private String getScoreText(float f) {
        if (f == 0.0f) {
            return "0";
        }
        if (f < 1.0f) {
            return "&frac12;";
        }
        double d = f;
        double d2 = Math.floor(d);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append((int)d2);
        String string = d - d2 == 0.5 ? "&frac12;" : "";
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private void initItemDrawables() {
        for (AfterGameCategory afterGameCategory : AfterGameCategory.values()) {
            MenuBarItem menuBarItem = (MenuBarItem)this.findViewById(afterGameCategory.getResId());
            menuBarItem.setIconDrawablesForStates(this.getResources().getDrawable(afterGameCategory.getResIdItemDrawable()), this.getResources().getDrawable(afterGameCategory.getResIdItemDrawableActive()), this.getResources().getDrawable(afterGameCategory.getResIdItemDrawableDisabled()));
            menuBarItem.setTitle(afterGameCategory.getName(this.getContext()));
            this._mapItem2Cat.put(menuBarItem, afterGameCategory);
        }
    }

    private void initLayout() {
        AbstractAfterGameView.inflate((Context)this.getContext(), (int)2131427486, (ViewGroup)this);
        this._mapCat2View = new TreeMap<AfterGameCategory, ArrowBottomContainerView>();
        this._mapItem2Cat = new HashMap<MenuBarItem, AfterGameCategory>();
        this.initItemDrawables();
        ViewGroup viewGroup = (ViewGroup)this.findViewById(2131296682);
        this._menuBar = (MenuBar)this.findViewById(2131296689);
        this._menuBar.setObserver(this);
        this._menuBar.setPopupViewGroup(viewGroup);
        this.findViewById(AfterGameCategory.REMATCH.getResId()).setEnabled(this._canRematch);
    }

    private void initScoreView(ModelHolder<AfterGameInformation> modelHolder) {
        Object object = modelHolder.getModel();
        object = new GameEndMessageHelper(this.getResources()).getGameEndTitleMessage(object.getStatus(), object.playerIsWhite());
        this._infoDialogFragment = RematchDialogFragment.createInstance(modelHolder, this.waitForResult());
        this._infoDialogFragment.setOnRematchButtonClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractAfterGameView.this.rematchButtonClicked();
            }
        });
        this._infoDialogFragment.setOnDialogCloseListener(this);
        this._infoDialogFragment.setTitle((CharSequence)object);
        if (!this._canRematch) {
            this._infoDialogFragment.deactivateRematchButton();
        }
        this._infoDialogFragment.show(this._fragmentManager, "");
    }

    private void selectDialogFor(AfterGameCategory afterGameCategory, MenuBarItem menuBarItem) {
        if (afterGameCategory != null) {
            ArrowBottomContainerView arrowBottomContainerView;
            ArrowBottomContainerView arrowBottomContainerView2 = arrowBottomContainerView = this._mapCat2View.get((Object)afterGameCategory);
            if (arrowBottomContainerView == null) {
                arrowBottomContainerView2 = arrowBottomContainerView = this.getDialogFor(afterGameCategory);
                if (arrowBottomContainerView != null) {
                    this._mapCat2View.put(afterGameCategory, arrowBottomContainerView);
                    arrowBottomContainerView2 = arrowBottomContainerView;
                }
            }
            if (arrowBottomContainerView2 != null) {
                if (arrowBottomContainerView2.getParent() == null) {
                    this._menuBar.showPopup(arrowBottomContainerView2, menuBarItem);
                    return;
                }
                this._menuBar.hidePopup();
                this._menuBar.selectItem(null);
                return;
            }
            this._menuBar.hidePopup();
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

    protected abstract ArrowBottomContainerView getDialogFor(AfterGameCategory var1);

    protected FragmentManager getFragmentManager() {
        return this._fragmentManager;
    }

    @Override
    public void menuItemClicked(MenuBarItem object) {
        if ((object = this.getCatForItem((MenuBarItem)((Object)object))) != null) {
            this.selectCategory((AfterGameCategory)((Object)object));
        }
    }

    @Override
    public void menuItemLongClicked(MenuBarItem menuBarItem) {
    }

    @Override
    public void onDialogClosed() {
        if (this._selectAfter != null) {
            this.selectCategory(this._selectAfter);
        }
    }

    public abstract void rematchButtonClicked();

    protected void selectCategory(AfterGameCategory afterGameCategory) {
        if (afterGameCategory == null) {
            this._menuBar.hidePopup();
        }
        for (Map.Entry<MenuBarItem, AfterGameCategory> entry : this._mapItem2Cat.entrySet()) {
            if (entry.getValue() != afterGameCategory) continue;
            this.selectDialogFor(afterGameCategory, entry.getKey());
            break;
        }
    }

    public abstract boolean waitForResult();

    protected static enum AfterGameCategory {
        REMATCH(2131690116, 2131296694, 2131231558, 2131231559, 2131231557),
        NEW_GAME(2131690115, 2131296693, 2131231555, 2131231556),
        ANALYZE(2131690114, 2131296690, 2131231551, 2131231552);
        
        private int _nameResourceId;
        private int _resId;
        private int _resIdItemDrawable;
        private int _resIdItemDrawableActive;
        private int _resIdItemDrawableDisabled = -1;

        private AfterGameCategory(int n2, int n3, int n4, int n5) {
            this(n2, n3, n4, n5, -1);
        }

        private AfterGameCategory(int n2, int n3, int n4, int n5, int n6) {
            this._nameResourceId = n2;
            this._resId = n3;
            this._resIdItemDrawable = n4;
            this._resIdItemDrawableActive = n5;
            this._resIdItemDrawableDisabled = n6;
        }

        public String getName(Context context) {
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
