/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.SpinnerAdapter
 */
package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.NavItemSelectedListener;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class WindowDecorActionBar
extends ActionBar
implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long FADE_IN_DURATION_MS = 200L;
    private static final long FADE_OUT_DURATION_MS = 100L;
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "WindowDecorActionBar";
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    ActionModeImpl mActionMode;
    private Activity mActivity;
    ActionBarContainer mContainerView;
    boolean mContentAnimations = true;
    View mContentView;
    Context mContext;
    ActionBarContextView mContextView;
    private int mCurWindowVisibility = 0;
    ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    DecorToolbar mDecorToolbar;
    ActionMode mDeferredDestroyActionMode;
    ActionMode.Callback mDeferredModeDestroyCallback;
    private Dialog mDialog;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    boolean mHiddenByApp;
    boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter(){

        @Override
        public void onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
                WindowDecorActionBar.this.mContentView.setTranslationY(0.0f);
                WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.mContainerView.setVisibility(8);
            WindowDecorActionBar.this.mContainerView.setTransitioning(false);
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.completeDeferredDestroyActionMode();
            if (WindowDecorActionBar.this.mOverlayLayout != null) {
                ViewCompat.requestApplyInsets((View)WindowDecorActionBar.this.mOverlayLayout);
            }
        }
    };
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
    private boolean mNowShowing = true;
    ActionBarOverlayLayout mOverlayLayout;
    private int mSavedTabPosition = -1;
    private TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter(){

        @Override
        public void onAnimationEnd(View view) {
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.mContainerView.requestLayout();
        }
    };
    private boolean mShowingForMode;
    ScrollingTabContainerView mTabScrollView;
    private ArrayList<TabImpl> mTabs = new ArrayList();
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener(){

        @Override
        public void onAnimationUpdate(View view) {
            ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
        }
    };

    public WindowDecorActionBar(Activity activity, boolean bl) {
        this.mActivity = activity;
        activity = activity.getWindow().getDecorView();
        this.init((View)activity);
        if (!bl) {
            this.mContentView = activity.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        this.mDialog = dialog;
        this.init(dialog.getWindow().getDecorView());
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public WindowDecorActionBar(View view) {
        this.init(view);
    }

    static boolean checkShowingFlags(boolean bl, boolean bl2, boolean bl3) {
        if (bl3) {
            return true;
        }
        if (!bl && !bl2) {
            return true;
        }
        return false;
    }

    private void cleanupTabs() {
        if (this.mSelectedTab != null) {
            this.selectTab(null);
        }
        this.mTabs.clear();
        if (this.mTabScrollView != null) {
            this.mTabScrollView.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    private void configureTab(ActionBar.Tab tab, int n) {
        if ((tab = (TabImpl)tab).getCallback() == null) {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }
        tab.setPosition(n);
        this.mTabs.add(n, (TabImpl)tab);
        int n2 = this.mTabs.size();
        while (++n < n2) {
            this.mTabs.get(n).setPosition(n);
        }
    }

    private void ensureTabsExist() {
        if (this.mTabScrollView != null) {
            return;
        }
        ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.mContext);
        if (this.mHasEmbeddedTabs) {
            scrollingTabContainerView.setVisibility(0);
            this.mDecorToolbar.setEmbeddedTabView(scrollingTabContainerView);
        } else {
            if (this.getNavigationMode() == 2) {
                scrollingTabContainerView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets((View)this.mOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
            this.mContainerView.setTabContainer(scrollingTabContainerView);
        }
        this.mTabScrollView = scrollingTabContainerView;
    }

    private DecorToolbar getDecorToolbar(View object) {
        if (object instanceof DecorToolbar) {
            return (DecorToolbar)object;
        }
        if (object instanceof Toolbar) {
            return ((Toolbar)((Object)object)).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        stringBuilder.append(object);
        object = stringBuilder.toString() != null ? object.getClass().getSimpleName() : "null";
        throw new IllegalStateException((String)object);
    }

    private void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(false);
            }
            this.updateVisibility(false);
        }
    }

    private void init(View object) {
        this.mOverlayLayout = (ActionBarOverlayLayout)object.findViewById(R.id.decor_content_parent);
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = this.getDecorToolbar(object.findViewById(R.id.action_bar));
        this.mContextView = (ActionBarContextView)object.findViewById(R.id.action_context_bar);
        this.mContainerView = (ActionBarContainer)object.findViewById(R.id.action_bar_container);
        if (this.mDecorToolbar != null && this.mContextView != null && this.mContainerView != null) {
            this.mContext = this.mDecorToolbar.getContext();
            int n = (this.mDecorToolbar.getDisplayOptions() & 4) != 0 ? 1 : 0;
            if (n != 0) {
                this.mDisplayHomeAsUpSet = true;
            }
            boolean bl = (object = ActionBarPolicy.get(this.mContext)).enableHomeButtonByDefault() || n != 0;
            this.setHomeButtonEnabled(bl);
            this.setHasEmbeddedTabs(object.hasEmbeddedTabs());
            object = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
            if (object.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
                this.setHideOnContentScrollEnabled(true);
            }
            if ((n = object.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0)) != 0) {
                this.setElevation(n);
            }
            object.recycle();
            return;
        }
        object = new StringBuilder();
        object.append(this.getClass().getSimpleName());
        object.append(" can only be used ");
        object.append("with a compatible window decor layout");
        throw new IllegalStateException(object.toString());
    }

    private void setHasEmbeddedTabs(boolean bl) {
        this.mHasEmbeddedTabs = bl;
        if (!this.mHasEmbeddedTabs) {
            this.mDecorToolbar.setEmbeddedTabView(null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        } else {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        }
        int n = this.getNavigationMode();
        boolean bl2 = true;
        n = n == 2 ? 1 : 0;
        if (this.mTabScrollView != null) {
            if (n != 0) {
                this.mTabScrollView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets((View)this.mOverlayLayout);
                }
            } else {
                this.mTabScrollView.setVisibility(8);
            }
        }
        Object object = this.mDecorToolbar;
        bl = !this.mHasEmbeddedTabs && n != 0;
        object.setCollapsible(bl);
        object = this.mOverlayLayout;
        bl = !this.mHasEmbeddedTabs && n != 0 ? bl2 : false;
        object.setHasNonEmbeddedTabs(bl);
    }

    private boolean shouldAnimateContextView() {
        return ViewCompat.isLaidOut((View)this.mContainerView);
    }

    private void showForActionMode() {
        if (!this.mShowingForMode) {
            this.mShowingForMode = true;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(true);
            }
            this.updateVisibility(false);
        }
    }

    private void updateVisibility(boolean bl) {
        if (WindowDecorActionBar.checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (!this.mNowShowing) {
                this.mNowShowing = true;
                this.doShow(bl);
                return;
            }
        } else if (this.mNowShowing) {
            this.mNowShowing = false;
            this.doHide(bl);
        }
    }

    @Override
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener);
    }

    @Override
    public void addTab(ActionBar.Tab tab) {
        this.addTab(tab, this.mTabs.isEmpty());
    }

    @Override
    public void addTab(ActionBar.Tab tab, int n) {
        this.addTab(tab, n, this.mTabs.isEmpty());
    }

    @Override
    public void addTab(ActionBar.Tab tab, int n, boolean bl) {
        this.ensureTabsExist();
        this.mTabScrollView.addTab(tab, n, bl);
        this.configureTab(tab, n);
        if (bl) {
            this.selectTab(tab);
        }
    }

    @Override
    public void addTab(ActionBar.Tab tab, boolean bl) {
        this.ensureTabsExist();
        this.mTabScrollView.addTab(tab, bl);
        this.configureTab(tab, this.mTabs.size());
        if (bl) {
            this.selectTab(tab);
        }
    }

    public void animateToMode(boolean bl) {
        if (bl) {
            this.showForActionMode();
        } else {
            this.hideForActionMode();
        }
        if (this.shouldAnimateContextView()) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
            if (bl) {
                viewPropertyAnimatorCompat = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
                viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200L);
            } else {
                viewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
                viewPropertyAnimatorCompat = this.mContextView.setupAnimatorToVisibility(8, 100L);
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat, viewPropertyAnimatorCompat2);
            viewPropertyAnimatorCompatSet.start();
            return;
        }
        if (bl) {
            this.mDecorToolbar.setVisibility(4);
            this.mContextView.setVisibility(0);
            return;
        }
        this.mDecorToolbar.setVisibility(0);
        this.mContextView.setVisibility(8);
    }

    @Override
    public boolean collapseActionView() {
        if (this.mDecorToolbar != null && this.mDecorToolbar.hasExpandedActionView()) {
            this.mDecorToolbar.collapseActionView();
            return true;
        }
        return false;
    }

    void completeDeferredDestroyActionMode() {
        if (this.mDeferredModeDestroyCallback != null) {
            this.mDeferredModeDestroyCallback.onDestroyActionMode(this.mDeferredDestroyActionMode);
            this.mDeferredDestroyActionMode = null;
            this.mDeferredModeDestroyCallback = null;
        }
    }

    @Override
    public void dispatchMenuVisibilityChanged(boolean bl) {
        if (bl == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = bl;
        int n = this.mMenuVisibilityListeners.size();
        for (int i = 0; i < n; ++i) {
            this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(bl);
        }
    }

    public void doHide(boolean bl) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || bl)) {
            float f;
            Object object;
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTransitioning(true);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            float f2 = f = (float)(- this.mContainerView.getHeight());
            if (bl) {
                object = new int[2];
                int[] arrn = object;
                arrn[0] = 0;
                arrn[1] = 0;
                this.mContainerView.getLocationInWindow((int[])object);
                f2 = f - (float)object[1];
            }
            object = ViewCompat.animate((View)this.mContainerView).translationY(f2);
            object.setUpdateListener(this.mUpdateListener);
            viewPropertyAnimatorCompatSet.play((ViewPropertyAnimatorCompat)object);
            if (this.mContentAnimations && this.mContentView != null) {
                viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.mContentView).translationY(f2));
            }
            viewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
            viewPropertyAnimatorCompatSet.setDuration(250L);
            viewPropertyAnimatorCompatSet.setListener(this.mHideListener);
            this.mCurrentShowAnim = viewPropertyAnimatorCompatSet;
            viewPropertyAnimatorCompatSet.start();
            return;
        }
        this.mHideListener.onAnimationEnd(null);
    }

    public void doShow(boolean bl) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || bl)) {
            Object object;
            float f;
            this.mContainerView.setTranslationY(0.0f);
            float f2 = f = (float)(- this.mContainerView.getHeight());
            if (bl) {
                object = new int[2];
                int[] arrn = object;
                arrn[0] = 0;
                arrn[1] = 0;
                this.mContainerView.getLocationInWindow((int[])object);
                f2 = f - (float)object[1];
            }
            this.mContainerView.setTranslationY(f2);
            object = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mContainerView).translationY(0.0f);
            viewPropertyAnimatorCompat.setUpdateListener(this.mUpdateListener);
            object.play(viewPropertyAnimatorCompat);
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(f2);
                object.play(ViewCompat.animate(this.mContentView).translationY(0.0f));
            }
            object.setInterpolator(sShowInterpolator);
            object.setDuration(250L);
            object.setListener(this.mShowListener);
            this.mCurrentShowAnim = object;
            object.start();
        } else {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTranslationY(0.0f);
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(0.0f);
            }
            this.mShowListener.onAnimationEnd(null);
        }
        if (this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets((View)this.mOverlayLayout);
        }
    }

    @Override
    public void enableContentAnimations(boolean bl) {
        this.mContentAnimations = bl;
    }

    @Override
    public View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    @Override
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    @Override
    public float getElevation() {
        return ViewCompat.getElevation((View)this.mContainerView);
    }

    @Override
    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    @Override
    public int getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    @Override
    public int getNavigationItemCount() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            default: {
                return 0;
            }
            case 2: {
                return this.mTabs.size();
            }
            case 1: 
        }
        return this.mDecorToolbar.getDropdownItemCount();
    }

    @Override
    public int getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode();
    }

    @Override
    public int getSelectedNavigationIndex() {
        int n = this.mDecorToolbar.getNavigationMode();
        int n2 = -1;
        switch (n) {
            default: {
                return -1;
            }
            case 2: {
                if (this.mSelectedTab != null) {
                    n2 = this.mSelectedTab.getPosition();
                }
                return n2;
            }
            case 1: 
        }
        return this.mDecorToolbar.getDropdownSelectedPosition();
    }

    @Override
    public ActionBar.Tab getSelectedTab() {
        return this.mSelectedTab;
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    @Override
    public ActionBar.Tab getTabAt(int n) {
        return this.mTabs.get(n);
    }

    @Override
    public int getTabCount() {
        return this.mTabs.size();
    }

    @Override
    public Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            int n = typedValue.resourceId;
            this.mThemedContext = n != 0 ? new ContextThemeWrapper(this.mContext, n) : this.mContext;
        }
        return this.mThemedContext;
    }

    @Override
    public CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    public boolean hasIcon() {
        return this.mDecorToolbar.hasIcon();
    }

    public boolean hasLogo() {
        return this.mDecorToolbar.hasLogo();
    }

    @Override
    public void hide() {
        if (!this.mHiddenByApp) {
            this.mHiddenByApp = true;
            this.updateVisibility(false);
        }
    }

    @Override
    public void hideForSystem() {
        if (!this.mHiddenBySystem) {
            this.mHiddenBySystem = true;
            this.updateVisibility(true);
        }
    }

    @Override
    public boolean isHideOnContentScrollEnabled() {
        return this.mOverlayLayout.isHideOnContentScrollEnabled();
    }

    @Override
    public boolean isShowing() {
        int n = this.getHeight();
        if (this.mNowShowing && (n == 0 || this.getHideOffset() < n)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTitleTruncated() {
        if (this.mDecorToolbar != null && this.mDecorToolbar.isTitleTruncated()) {
            return true;
        }
        return false;
    }

    @Override
    public ActionBar.Tab newTab() {
        return new TabImpl();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    @Override
    public void onContentScrollStarted() {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    @Override
    public void onContentScrollStopped() {
    }

    @Override
    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        if (this.mActionMode == null) {
            return false;
        }
        Menu menu = this.mActionMode.getMenu();
        if (menu != null) {
            int n2 = keyEvent != null ? keyEvent.getDeviceId() : -1;
            n2 = KeyCharacterMap.load((int)n2).getKeyboardType();
            boolean bl = true;
            if (n2 == 1) {
                bl = false;
            }
            menu.setQwertyMode(bl);
            return menu.performShortcut(n, keyEvent, 0);
        }
        return false;
    }

    @Override
    public void onWindowVisibilityChanged(int n) {
        this.mCurWindowVisibility = n;
    }

    @Override
    public void removeAllTabs() {
        this.cleanupTabs();
    }

    @Override
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener);
    }

    @Override
    public void removeTab(ActionBar.Tab tab) {
        this.removeTabAt(tab.getPosition());
    }

    @Override
    public void removeTabAt(int n) {
        if (this.mTabScrollView == null) {
            return;
        }
        int n2 = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition;
        this.mTabScrollView.removeTabAt(n);
        TabImpl tabImpl = this.mTabs.remove(n);
        if (tabImpl != null) {
            tabImpl.setPosition(-1);
        }
        int n3 = this.mTabs.size();
        for (int i = n; i < n3; ++i) {
            this.mTabs.get(i).setPosition(i);
        }
        if (n2 == n) {
            tabImpl = this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, n - 1));
            this.selectTab(tabImpl);
        }
    }

    @Override
    public boolean requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
        if (viewGroup != null && !viewGroup.hasFocus()) {
            viewGroup.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public void selectTab(ActionBar.Tab tab) {
        int n = this.getNavigationMode();
        int n2 = -1;
        if (n != 2) {
            if (tab != null) {
                n2 = tab.getPosition();
            }
            this.mSavedTabPosition = n2;
            return;
        }
        FragmentTransaction fragmentTransaction = this.mActivity instanceof FragmentActivity && !this.mDecorToolbar.getViewGroup().isInEditMode() ? ((FragmentActivity)this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack() : null;
        if (this.mSelectedTab == tab) {
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, fragmentTransaction);
                this.mTabScrollView.animateToTab(tab.getPosition());
            }
        } else {
            ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
            if (tab != null) {
                n2 = tab.getPosition();
            }
            scrollingTabContainerView.setTabSelected(n2);
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, fragmentTransaction);
            }
            this.mSelectedTab = (TabImpl)tab;
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, fragmentTransaction);
            }
        }
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        this.mContainerView.setPrimaryBackground(drawable);
    }

    @Override
    public void setCustomView(int n) {
        this.setCustomView(LayoutInflater.from((Context)this.getThemedContext()).inflate(n, this.mDecorToolbar.getViewGroup(), false));
    }

    @Override
    public void setCustomView(View view) {
        this.mDecorToolbar.setCustomView(view);
    }

    @Override
    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.mDecorToolbar.setCustomView(view);
    }

    @Override
    public void setDefaultDisplayHomeAsUpEnabled(boolean bl) {
        if (!this.mDisplayHomeAsUpSet) {
            this.setDisplayHomeAsUpEnabled(bl);
        }
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean bl) {
        int n = bl ? 4 : 0;
        this.setDisplayOptions(n, 4);
    }

    @Override
    public void setDisplayOptions(int n) {
        if ((n & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(n);
    }

    @Override
    public void setDisplayOptions(int n, int n2) {
        int n3 = this.mDecorToolbar.getDisplayOptions();
        if ((n2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(n & n2 | ~ n2 & n3);
    }

    @Override
    public void setDisplayShowCustomEnabled(boolean bl) {
        int n = bl ? 16 : 0;
        this.setDisplayOptions(n, 16);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean bl) {
        int n = bl ? 2 : 0;
        this.setDisplayOptions(n, 2);
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean bl) {
        int n = bl ? 8 : 0;
        this.setDisplayOptions(n, 8);
    }

    @Override
    public void setDisplayUseLogoEnabled(boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public void setElevation(float f) {
        ViewCompat.setElevation((View)this.mContainerView, f);
    }

    @Override
    public void setHideOffset(int n) {
        if (n != 0 && !this.mOverlayLayout.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
        }
        this.mOverlayLayout.setActionBarHideOffset(n);
    }

    @Override
    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl && !this.mOverlayLayout.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.mHideOnContentScroll = bl;
        this.mOverlayLayout.setHideOnContentScrollEnabled(bl);
    }

    @Override
    public void setHomeActionContentDescription(int n) {
        this.mDecorToolbar.setNavigationContentDescription(n);
    }

    @Override
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.mDecorToolbar.setNavigationContentDescription(charSequence);
    }

    @Override
    public void setHomeAsUpIndicator(int n) {
        this.mDecorToolbar.setNavigationIcon(n);
    }

    @Override
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.mDecorToolbar.setNavigationIcon(drawable);
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
        this.mDecorToolbar.setHomeButtonEnabled(bl);
    }

    @Override
    public void setIcon(int n) {
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setIcon(Drawable drawable) {
        this.mDecorToolbar.setIcon(drawable);
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, new NavItemSelectedListener(onNavigationListener));
    }

    @Override
    public void setLogo(int n) {
        this.mDecorToolbar.setLogo(n);
    }

    @Override
    public void setLogo(Drawable drawable) {
        this.mDecorToolbar.setLogo(drawable);
    }

    @Override
    public void setNavigationMode(int n) {
        int n2 = this.mDecorToolbar.getNavigationMode();
        if (n2 == 2) {
            this.mSavedTabPosition = this.getSelectedNavigationIndex();
            this.selectTab(null);
            this.mTabScrollView.setVisibility(8);
        }
        if (n2 != n && !this.mHasEmbeddedTabs && this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets((View)this.mOverlayLayout);
        }
        this.mDecorToolbar.setNavigationMode(n);
        boolean bl = false;
        if (n == 2) {
            this.ensureTabsExist();
            this.mTabScrollView.setVisibility(0);
            if (this.mSavedTabPosition != -1) {
                this.setSelectedNavigationItem(this.mSavedTabPosition);
                this.mSavedTabPosition = -1;
            }
        }
        Object object = this.mDecorToolbar;
        boolean bl2 = n == 2 && !this.mHasEmbeddedTabs;
        object.setCollapsible(bl2);
        object = this.mOverlayLayout;
        bl2 = bl;
        if (n == 2) {
            bl2 = bl;
            if (!this.mHasEmbeddedTabs) {
                bl2 = true;
            }
        }
        object.setHasNonEmbeddedTabs(bl2);
    }

    @Override
    public void setSelectedNavigationItem(int n) {
        switch (this.mDecorToolbar.getNavigationMode()) {
            default: {
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
            }
            case 2: {
                this.selectTab(this.mTabs.get(n));
                return;
            }
            case 1: 
        }
        this.mDecorToolbar.setDropdownSelectedPosition(n);
    }

    @Override
    public void setShowHideAnimationEnabled(boolean bl) {
        this.mShowHideAnimationEnabled = bl;
        if (!bl && this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
    }

    @Override
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override
    public void setStackedBackgroundDrawable(Drawable drawable) {
        this.mContainerView.setStackedBackground(drawable);
    }

    @Override
    public void setSubtitle(int n) {
        this.setSubtitle(this.mContext.getString(n));
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        this.mDecorToolbar.setSubtitle(charSequence);
    }

    @Override
    public void setTitle(int n) {
        this.setTitle(this.mContext.getString(n));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override
    public void show() {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false;
            this.updateVisibility(false);
        }
    }

    @Override
    public void showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            this.updateVisibility(true);
        }
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback object) {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        object = new ActionModeImpl(this.mContextView.getContext(), (ActionMode.Callback)object);
        if (object.dispatchOnCreate()) {
            this.mActionMode = object;
            object.invalidate();
            this.mContextView.initForMode((ActionMode)object);
            this.animateToMode(true);
            this.mContextView.sendAccessibilityEvent(32);
            return object;
        }
        return null;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public class ActionModeImpl
    extends ActionMode
    implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private ActionMode.Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            this.mMenu = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.mMenu.setCallback(this);
        }

        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                boolean bl = this.mCallback.onCreateActionMode(this, this.mMenu);
                return bl;
            }
            finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override
        public void finish() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
                WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
                WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
            } else {
                this.mCallback.onDestroyActionMode(this);
            }
            this.mCallback = null;
            WindowDecorActionBar.this.animateToMode(false);
            WindowDecorActionBar.this.mContextView.closeMode();
            WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
            WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
            WindowDecorActionBar.this.mActionMode = null;
        }

        @Override
        public View getCustomView() {
            if (this.mCustomView != null) {
                return (View)this.mCustomView.get();
            }
            return null;
        }

        @Override
        public Menu getMenu() {
            return this.mMenu;
        }

        @Override
        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        @Override
        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        @Override
        public CharSequence getTitle() {
            return WindowDecorActionBar.this.mContextView.getTitle();
        }

        @Override
        public void invalidate() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            this.mMenu.stopDispatchingItemsChanged();
            try {
                this.mCallback.onPrepareActionMode(this, this.mMenu);
                return;
            }
            finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override
        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (this.mCallback != null) {
                return this.mCallback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return;
            }
            this.invalidate();
            WindowDecorActionBar.this.mContextView.showOverflowMenu();
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.mCallback == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        @Override
        public void setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new WeakReference<View>(view);
        }

        @Override
        public void setSubtitle(int n) {
            this.setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
        }

        @Override
        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setSubtitle(charSequence);
        }

        @Override
        public void setTitle(int n) {
            this.setTitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
        }

        @Override
        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setTitle(charSequence);
        }

        @Override
        public void setTitleOptionalHint(boolean bl) {
            super.setTitleOptionalHint(bl);
            WindowDecorActionBar.this.mContextView.setTitleOptional(bl);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public class TabImpl
    extends ActionBar.Tab {
        private ActionBar.TabListener mCallback;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;

        public ActionBar.TabListener getCallback() {
            return this.mCallback;
        }

        @Override
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        @Override
        public View getCustomView() {
            return this.mCustomView;
        }

        @Override
        public Drawable getIcon() {
            return this.mIcon;
        }

        @Override
        public int getPosition() {
            return this.mPosition;
        }

        @Override
        public Object getTag() {
            return this.mTag;
        }

        @Override
        public CharSequence getText() {
            return this.mText;
        }

        @Override
        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        @Override
        public ActionBar.Tab setContentDescription(int n) {
            return this.setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(n));
        }

        @Override
        public ActionBar.Tab setContentDescription(CharSequence charSequence) {
            this.mContentDesc = charSequence;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        @Override
        public ActionBar.Tab setCustomView(int n) {
            return this.setCustomView(LayoutInflater.from((Context)WindowDecorActionBar.this.getThemedContext()).inflate(n, null));
        }

        @Override
        public ActionBar.Tab setCustomView(View view) {
            this.mCustomView = view;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        @Override
        public ActionBar.Tab setIcon(int n) {
            return this.setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, n));
        }

        @Override
        public ActionBar.Tab setIcon(Drawable drawable) {
            this.mIcon = drawable;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public void setPosition(int n) {
            this.mPosition = n;
        }

        @Override
        public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
            this.mCallback = tabListener;
            return this;
        }

        @Override
        public ActionBar.Tab setTag(Object object) {
            this.mTag = object;
            return this;
        }

        @Override
        public ActionBar.Tab setText(int n) {
            return this.setText(WindowDecorActionBar.this.mContext.getResources().getText(n));
        }

        @Override
        public ActionBar.Tab setText(CharSequence charSequence) {
            this.mText = charSequence;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }
    }

}
