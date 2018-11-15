/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.FrameLayout
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopup;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.MenuPopupWindow;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class CascadingMenuPopup
extends MenuPopup
implements MenuPresenter,
View.OnKeyListener,
PopupWindow.OnDismissListener {
    static final int HORIZ_POSITION_LEFT = 0;
    static final int HORIZ_POSITION_RIGHT = 1;
    static final int SUBMENU_TIMEOUT_MS = 200;
    private View mAnchorView;
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            if (CascadingMenuPopup.this.mTreeObserver != null) {
                if (!CascadingMenuPopup.this.mTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }
    };
    private final Context mContext;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon;
    private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        public void onGlobalLayout() {
            if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.mShowingMenus.size() > 0 && !CascadingMenuPopup.this.mShowingMenus.get((int)0).window.isModal()) {
                Object object = CascadingMenuPopup.this.mShownAnchorView;
                if (object != null && object.isShown()) {
                    object = CascadingMenuPopup.this.mShowingMenus.iterator();
                    while (object.hasNext()) {
                        ((CascadingMenuInfo)object.next()).window.show();
                    }
                } else {
                    CascadingMenuPopup.this.dismiss();
                }
            }
        }
    };
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private int mLastPosition;
    private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener(){

        @Override
        public void onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem object) {
            int n;
            CascadingMenuInfo cascadingMenuInfo;
            block4 : {
                Handler handler = CascadingMenuPopup.this.mSubMenuHoverHandler;
                cascadingMenuInfo = null;
                handler.removeCallbacksAndMessages(null);
                int n2 = CascadingMenuPopup.this.mShowingMenus.size();
                for (n = 0; n < n2; ++n) {
                    if (menuBuilder != CascadingMenuPopup.this.mShowingMenus.get((int)n).menu) {
                        continue;
                    }
                    break block4;
                }
                n = -1;
            }
            if (n == -1) {
                return;
            }
            if (++n < CascadingMenuPopup.this.mShowingMenus.size()) {
                cascadingMenuInfo = CascadingMenuPopup.this.mShowingMenus.get(n);
            }
            object = new Runnable((MenuItem)object, menuBuilder){
                final /* synthetic */ MenuItem val$item;
                final /* synthetic */ MenuBuilder val$menu;
                {
                    this.val$item = menuItem;
                    this.val$menu = menuBuilder;
                }

                @Override
                public void run() {
                    if (cascadingMenuInfo != null) {
                        CascadingMenuPopup.this.mShouldCloseImmediately = true;
                        cascadingMenuInfo.menu.close(false);
                        CascadingMenuPopup.this.mShouldCloseImmediately = false;
                    }
                    if (this.val$item.isEnabled() && this.val$item.hasSubMenu()) {
                        this.val$menu.performItemAction(this.val$item, 4);
                    }
                }
            };
            long l = SystemClock.uptimeMillis();
            CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime((Runnable)object, (Object)menuBuilder, l + 200L);
        }

        @Override
        public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages((Object)menuBuilder);
        }

    };
    private final int mMenuMaxWidth;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final List<MenuBuilder> mPendingMenus = new ArrayList<MenuBuilder>();
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    private int mRawDropDownGravity = 0;
    boolean mShouldCloseImmediately;
    private boolean mShowTitle;
    final List<CascadingMenuInfo> mShowingMenus = new ArrayList<CascadingMenuInfo>();
    View mShownAnchorView;
    final Handler mSubMenuHoverHandler;
    private ViewTreeObserver mTreeObserver;
    private int mXOffset;
    private int mYOffset;

    public CascadingMenuPopup(@NonNull Context context, @NonNull View view, @AttrRes int n, @StyleRes int n2, boolean bl) {
        this.mContext = context;
        this.mAnchorView = view;
        this.mPopupStyleAttr = n;
        this.mPopupStyleRes = n2;
        this.mOverflowOnly = bl;
        this.mForceShowIcon = false;
        this.mLastPosition = this.getInitialMenuPosition();
        context = context.getResources();
        this.mMenuMaxWidth = Math.max(context.getDisplayMetrics().widthPixels / 2, context.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.mSubMenuHoverHandler = new Handler();
    }

    private MenuPopupWindow createPopupWindow() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.mAnchorView);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    private int findIndexOfAddedMenu(@NonNull MenuBuilder menuBuilder) {
        int n = this.mShowingMenus.size();
        for (int i = 0; i < n; ++i) {
            if (menuBuilder != this.mShowingMenus.get((int)i).menu) continue;
            return i;
        }
        return -1;
    }

    private MenuItem findMenuItemForSubmenu(@NonNull MenuBuilder menuBuilder, @NonNull MenuBuilder menuBuilder2) {
        int n = menuBuilder.size();
        for (int i = 0; i < n; ++i) {
            MenuItem menuItem = menuBuilder.getItem(i);
            if (!menuItem.hasSubMenu() || menuBuilder2 != menuItem.getSubMenu()) continue;
            return menuItem;
        }
        return null;
    }

    @Nullable
    private View findParentViewForSubmenu(@NonNull CascadingMenuInfo object, @NonNull MenuBuilder menuBuilder) {
        int n;
        int n2;
        ListView listView;
        block8 : {
            if ((menuBuilder = this.findMenuItemForSubmenu(object.menu, menuBuilder)) == null) {
                return null;
            }
            listView = object.getListView();
            object = listView.getAdapter();
            boolean bl = object instanceof HeaderViewListAdapter;
            n2 = 0;
            if (bl) {
                object = (HeaderViewListAdapter)object;
                n = object.getHeadersCount();
                object = (MenuAdapter)object.getWrappedAdapter();
            } else {
                object = (MenuAdapter)((Object)object);
                n = 0;
            }
            int n3 = object.getCount();
            while (n2 < n3) {
                if (menuBuilder != object.getItem(n2)) {
                    ++n2;
                    continue;
                }
                break block8;
            }
            n2 = -1;
        }
        if (n2 == -1) {
            return null;
        }
        if ((n2 = n2 + n - listView.getFirstVisiblePosition()) >= 0) {
            if (n2 >= listView.getChildCount()) {
                return null;
            }
            return listView.getChildAt(n2);
        }
        return null;
    }

    private int getInitialMenuPosition() {
        int n = ViewCompat.getLayoutDirection(this.mAnchorView);
        int n2 = 1;
        if (n == 1) {
            n2 = 0;
        }
        return n2;
    }

    private int getNextMenuPosition(int n) {
        ListView listView = this.mShowingMenus.get(this.mShowingMenus.size() - 1).getListView();
        int[] arrn = new int[2];
        listView.getLocationOnScreen(arrn);
        Rect rect = new Rect();
        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
        if (this.mLastPosition == 1) {
            if (arrn[0] + listView.getWidth() + n > rect.right) {
                return 0;
            }
            return 1;
        }
        if (arrn[0] - n < 0) {
            return 1;
        }
        return 0;
    }

    private void showMenu(@NonNull MenuBuilder menuBuilder) {
        Object object;
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.mContext);
        Object object2 = new MenuAdapter(menuBuilder, layoutInflater, this.mOverflowOnly);
        if (!this.isShowing() && this.mForceShowIcon) {
            object2.setForceShowIcon(true);
        } else if (this.isShowing()) {
            object2.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(menuBuilder));
        }
        int n = CascadingMenuPopup.measureIndividualMenuWidth((ListAdapter)object2, null, this.mContext, this.mMenuMaxWidth);
        MenuPopupWindow menuPopupWindow = this.createPopupWindow();
        menuPopupWindow.setAdapter((ListAdapter)object2);
        menuPopupWindow.setContentWidth(n);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        if (this.mShowingMenus.size() > 0) {
            object2 = this.mShowingMenus.get(this.mShowingMenus.size() - 1);
            object = this.findParentViewForSubmenu((CascadingMenuInfo)object2, menuBuilder);
        } else {
            object = object2 = null;
        }
        if (object != null) {
            int n2;
            menuPopupWindow.setTouchModal(false);
            menuPopupWindow.setEnterTransition(null);
            int n3 = this.getNextMenuPosition(n);
            int n4 = n3 == 1 ? 1 : 0;
            this.mLastPosition = n3;
            if (Build.VERSION.SDK_INT >= 26) {
                menuPopupWindow.setAnchorView((View)object);
                n2 = n3 = 0;
            } else {
                int[] arrn = new int[2];
                this.mAnchorView.getLocationOnScreen(arrn);
                int[] arrn2 = new int[2];
                object.getLocationOnScreen(arrn2);
                if ((this.mDropDownGravity & 7) == 5) {
                    arrn[0] = arrn[0] + this.mAnchorView.getWidth();
                    arrn2[0] = arrn2[0] + object.getWidth();
                }
                n2 = arrn2[0] - arrn[0];
                n3 = arrn2[1] - arrn[1];
            }
            n4 = (this.mDropDownGravity & 5) == 5 ? (n4 != 0 ? n2 + n : n2 - object.getWidth()) : (n4 != 0 ? n2 + object.getWidth() : n2 - n);
            menuPopupWindow.setHorizontalOffset(n4);
            menuPopupWindow.setOverlapAnchor(true);
            menuPopupWindow.setVerticalOffset(n3);
        } else {
            if (this.mHasXOffset) {
                menuPopupWindow.setHorizontalOffset(this.mXOffset);
            }
            if (this.mHasYOffset) {
                menuPopupWindow.setVerticalOffset(this.mYOffset);
            }
            menuPopupWindow.setEpicenterBounds(this.getEpicenterBounds());
        }
        object = new CascadingMenuInfo(menuPopupWindow, menuBuilder, this.mLastPosition);
        this.mShowingMenus.add((CascadingMenuInfo)object);
        menuPopupWindow.show();
        object = menuPopupWindow.getListView();
        object.setOnKeyListener((View.OnKeyListener)this);
        if (object2 == null && this.mShowTitle && menuBuilder.getHeaderTitle() != null) {
            object2 = (FrameLayout)layoutInflater.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup)object, false);
            layoutInflater = (TextView)object2.findViewById(16908310);
            object2.setEnabled(false);
            layoutInflater.setText(menuBuilder.getHeaderTitle());
            object.addHeaderView((View)object2, null, false);
            menuPopupWindow.show();
        }
    }

    @Override
    public void addMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (this.isShowing()) {
            this.showMenu(menuBuilder);
            return;
        }
        this.mPendingMenus.add(menuBuilder);
    }

    @Override
    protected boolean closeMenuOnSubMenuOpened() {
        return false;
    }

    @Override
    public void dismiss() {
        int n = this.mShowingMenus.size();
        if (n > 0) {
            CascadingMenuInfo[] arrcascadingMenuInfo = this.mShowingMenus.toArray(new CascadingMenuInfo[n]);
            --n;
            while (n >= 0) {
                CascadingMenuInfo cascadingMenuInfo = arrcascadingMenuInfo[n];
                if (cascadingMenuInfo.window.isShowing()) {
                    cascadingMenuInfo.window.dismiss();
                }
                --n;
            }
        }
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    @Override
    public ListView getListView() {
        if (this.mShowingMenus.isEmpty()) {
            return null;
        }
        return this.mShowingMenus.get(this.mShowingMenus.size() - 1).getListView();
    }

    @Override
    public boolean isShowing() {
        boolean bl;
        int n = this.mShowingMenus.size();
        boolean bl2 = bl = false;
        if (n > 0) {
            bl2 = bl;
            if (this.mShowingMenus.get((int)0).window.isShowing()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        int n = this.findIndexOfAddedMenu(menuBuilder);
        if (n < 0) {
            return;
        }
        int n2 = n + 1;
        if (n2 < this.mShowingMenus.size()) {
            this.mShowingMenus.get((int)n2).menu.close(false);
        }
        CascadingMenuInfo cascadingMenuInfo = this.mShowingMenus.remove(n);
        cascadingMenuInfo.menu.removeMenuPresenter(this);
        if (this.mShouldCloseImmediately) {
            cascadingMenuInfo.window.setExitTransition(null);
            cascadingMenuInfo.window.setAnimationStyle(0);
        }
        cascadingMenuInfo.window.dismiss();
        n = this.mShowingMenus.size();
        this.mLastPosition = n > 0 ? this.mShowingMenus.get((int)(n - 1)).position : this.getInitialMenuPosition();
        if (n == 0) {
            this.dismiss();
            if (this.mPresenterCallback != null) {
                this.mPresenterCallback.onCloseMenu(menuBuilder, true);
            }
            if (this.mTreeObserver != null) {
                if (this.mTreeObserver.isAlive()) {
                    this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
                }
                this.mTreeObserver = null;
            }
            this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            this.mOnDismissListener.onDismiss();
            return;
        }
        if (bl) {
            this.mShowingMenus.get((int)0).menu.close(false);
        }
    }

    public void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        block3 : {
            int n = this.mShowingMenus.size();
            for (int i = 0; i < n; ++i) {
                cascadingMenuInfo = this.mShowingMenus.get(i);
                if (cascadingMenuInfo.window.isShowing()) {
                    continue;
                }
                break block3;
            }
            cascadingMenuInfo = null;
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false);
        }
    }

    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && n == 82) {
            this.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return null;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        for (CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
            if (subMenuBuilder != cascadingMenuInfo.menu) continue;
            cascadingMenuInfo.getListView().requestFocus();
            return true;
        }
        if (subMenuBuilder.hasVisibleItems()) {
            this.addMenu(subMenuBuilder);
            if (this.mPresenterCallback != null) {
                this.mPresenterCallback.onOpenSubMenu(subMenuBuilder);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setAnchorView(@NonNull View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view;
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView));
        }
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override
    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
    }

    @Override
    public void setGravity(int n) {
        if (this.mRawDropDownGravity != n) {
            this.mRawDropDownGravity = n;
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection(this.mAnchorView));
        }
    }

    @Override
    public void setHorizontalOffset(int n) {
        this.mHasXOffset = true;
        this.mXOffset = n;
    }

    @Override
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override
    public void setShowTitle(boolean bl) {
        this.mShowTitle = bl;
    }

    @Override
    public void setVerticalOffset(int n) {
        this.mHasYOffset = true;
        this.mYOffset = n;
    }

    @Override
    public void show() {
        if (this.isShowing()) {
            return;
        }
        Iterator<MenuBuilder> iterator = this.mPendingMenus.iterator();
        while (iterator.hasNext()) {
            this.showMenu(iterator.next());
        }
        this.mPendingMenus.clear();
        this.mShownAnchorView = this.mAnchorView;
        if (this.mShownAnchorView != null) {
            boolean bl = this.mTreeObserver == null;
            this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            if (bl) {
                this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
            }
            this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    @Override
    public void updateMenuView(boolean bl) {
        Iterator<CascadingMenuInfo> iterator = this.mShowingMenus.iterator();
        while (iterator.hasNext()) {
            CascadingMenuPopup.toMenuAdapter(iterator.next().getListView().getAdapter()).notifyDataSetChanged();
        }
    }

    private static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int n) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = n;
        }

        public ListView getListView() {
            return this.window.getListView();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HorizPosition {
    }

}
