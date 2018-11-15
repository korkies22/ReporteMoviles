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
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.media.AudioManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow
 *  android.widget.TextView
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.app.AppCompatViewInflater;
import android.support.v7.app.ToolbarActionBar;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.StandaloneActionMode;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.FitWindowsViewGroup;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.support.v7.widget.ViewStubCompat;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;

@RequiresApi(value=14)
class AppCompatDelegateImplV9
extends AppCompatDelegateImplBase
implements MenuBuilder.Callback,
LayoutInflater.Factory2 {
    private static final boolean IS_PRE_LOLLIPOP;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private AppCompatViewInflater mAppCompatViewInflater;
    private boolean mClosingActionMenu;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim = null;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable(){

        @Override
        public void run() {
            if ((AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures & 1) != 0) {
                AppCompatDelegateImplV9.this.doInvalidatePanelMenu(0);
            }
            if ((AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures & 4096) != 0) {
                AppCompatDelegateImplV9.this.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImplV9.this.mInvalidatePanelMenuPosted = false;
            AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures = 0;
        }
    };
    private boolean mLongPressBackDown;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private TextView mTitleView;

    static {
        boolean bl = Build.VERSION.SDK_INT < 21;
        IS_PRE_LOLLIPOP = bl;
    }

    AppCompatDelegateImplV9(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
        View view = this.mWindow.getDecorView();
        contentFrameLayout.setDecorPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        view = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
        view.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        view.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        view.recycle();
        contentFrameLayout.requestLayout();
    }

    private ViewGroup createSubDecor() {
        ContentFrameLayout contentFrameLayout;
        Object object = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!object.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            object.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (object.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
            this.requestWindowFeature(1);
        } else if (object.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
            this.requestWindowFeature(108);
        }
        if (object.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            this.requestWindowFeature(109);
        }
        if (object.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            this.requestWindowFeature(10);
        }
        this.mIsFloating = object.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
        object.recycle();
        this.mWindow.getDecorView();
        object = LayoutInflater.from((Context)this.mContext);
        if (!this.mWindowNoTitle) {
            if (this.mIsFloating) {
                object = (ViewGroup)object.inflate(R.layout.abc_dialog_title_material, null);
                this.mOverlayActionBar = false;
                this.mHasActionBar = false;
            } else if (this.mHasActionBar) {
                object = new TypedValue();
                this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, (TypedValue)object, true);
                object = object.resourceId != 0 ? new ContextThemeWrapper(this.mContext, object.resourceId) : this.mContext;
                contentFrameLayout = (ViewGroup)LayoutInflater.from((Context)object).inflate(R.layout.abc_screen_toolbar, null);
                this.mDecorContentParent = (DecorContentParent)contentFrameLayout.findViewById(R.id.decor_content_parent);
                this.mDecorContentParent.setWindowCallback(this.getWindowCallback());
                if (this.mOverlayActionBar) {
                    this.mDecorContentParent.initFeature(109);
                }
                if (this.mFeatureProgress) {
                    this.mDecorContentParent.initFeature(2);
                }
                object = contentFrameLayout;
                if (this.mFeatureIndeterminateProgress) {
                    this.mDecorContentParent.initFeature(5);
                    object = contentFrameLayout;
                }
            } else {
                object = null;
            }
        } else {
            object = this.mOverlayActionMode ? (ViewGroup)object.inflate(R.layout.abc_screen_simple_overlay_action_mode, null) : (ViewGroup)object.inflate(R.layout.abc_screen_simple, null);
            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.setOnApplyWindowInsetsListener((View)object, new OnApplyWindowInsetsListener(){

                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        int n = windowInsetsCompat.getSystemWindowInsetTop();
                        int n2 = AppCompatDelegateImplV9.this.updateStatusGuard(n);
                        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
                        if (n != n2) {
                            windowInsetsCompat2 = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), n2, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                        }
                        return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat2);
                    }
                });
            } else {
                ((FitWindowsViewGroup)object).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener(){

                    @Override
                    public void onFitSystemWindows(Rect rect) {
                        rect.top = AppCompatDelegateImplV9.this.updateStatusGuard(rect.top);
                    }
                });
            }
        }
        if (object == null) {
            object = new StringBuilder();
            object.append("AppCompat does not support the current theme features: { windowActionBar: ");
            object.append(this.mHasActionBar);
            object.append(", windowActionBarOverlay: ");
            object.append(this.mOverlayActionBar);
            object.append(", android:windowIsFloating: ");
            object.append(this.mIsFloating);
            object.append(", windowActionModeOverlay: ");
            object.append(this.mOverlayActionMode);
            object.append(", windowNoTitle: ");
            object.append(this.mWindowNoTitle);
            object.append(" }");
            throw new IllegalArgumentException(object.toString());
        }
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView)object.findViewById(R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows((View)object);
        contentFrameLayout = (ContentFrameLayout)object.findViewById(R.id.action_bar_activity_content);
        ViewGroup viewGroup = (ViewGroup)this.mWindow.findViewById(16908290);
        if (viewGroup != null) {
            while (viewGroup.getChildCount() > 0) {
                View view = viewGroup.getChildAt(0);
                viewGroup.removeViewAt(0);
                contentFrameLayout.addView(view);
            }
            viewGroup.setId(-1);
            contentFrameLayout.setId(16908290);
            if (viewGroup instanceof FrameLayout) {
                ((FrameLayout)viewGroup).setForeground(null);
            }
        }
        this.mWindow.setContentView((View)object);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener(){

            @Override
            public void onAttachedFromWindow() {
            }

            @Override
            public void onDetachedFromWindow() {
                AppCompatDelegateImplV9.this.dismissPopups();
            }
        });
        return object;
    }

    private void ensureSubDecor() {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = this.createSubDecor();
            Object object = this.getTitle();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                this.onTitleChanged((CharSequence)object);
            }
            this.applyFixedSizeWindow();
            this.onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            object = this.getPanelState(0, false);
            if (!(this.isDestroyed() || object != null && object.menu != null)) {
                this.invalidatePanelMenu(108);
            }
        }
    }

    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        if (panelFeatureState.createdPanelView != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView;
            return true;
        }
        if (panelFeatureState.menu == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        panelFeatureState.shownPanelView = (View)panelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback);
        if (panelFeatureState.shownPanelView != null) {
            return true;
        }
        return false;
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.setStyle(this.getActionBarThemedContext());
        panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
        panelFeatureState.gravity = 81;
        return true;
    }

    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Object object;
        block10 : {
            Context context;
            block9 : {
                context = this.mContext;
                if (panelFeatureState.featureId == 0) break block9;
                object = context;
                if (panelFeatureState.featureId != 108) break block10;
            }
            object = context;
            if (this.mDecorContentParent != null) {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = context.getTheme();
                theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                object = null;
                if (typedValue.resourceId != 0) {
                    object = context.getResources().newTheme();
                    object.setTo(theme);
                    object.applyStyle(typedValue.resourceId, true);
                    object.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                } else {
                    theme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                }
                Object object2 = object;
                if (typedValue.resourceId != 0) {
                    object2 = object;
                    if (object == null) {
                        object2 = context.getResources().newTheme();
                        object2.setTo(theme);
                    }
                    object2.applyStyle(typedValue.resourceId, true);
                }
                object = context;
                if (object2 != null) {
                    object = new ContextThemeWrapper(context, 0);
                    object.getTheme().setTo((Resources.Theme)object2);
                }
            }
        }
        object = new MenuBuilder((Context)object);
        object.setCallback(this);
        panelFeatureState.setMenu((MenuBuilder)object);
        return true;
    }

    private void invalidatePanelMenu(int n) {
        this.mInvalidatePanelMenuFeatures = 1 << n | this.mInvalidatePanelMenuFeatures;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    private boolean onKeyDownPanel(int n, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            PanelFeatureState panelFeatureState = this.getPanelState(n, true);
            if (!panelFeatureState.isOpen) {
                return this.preparePanel(panelFeatureState, keyEvent);
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean onKeyUpPanel(int n, KeyEvent keyEvent) {
        boolean bl;
        if (this.mActionMode != null) {
            return false;
        }
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (n == 0 && this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && !ViewConfiguration.get((Context)this.mContext).hasPermanentMenuKey()) {
            if (!this.mDecorContentParent.isOverflowMenuShowing()) {
                if (this.isDestroyed()) return false;
                if (!this.preparePanel(panelFeatureState, keyEvent)) return false;
                bl = this.mDecorContentParent.showOverflowMenu();
            } else {
                bl = this.mDecorContentParent.hideOverflowMenu();
            }
        } else if (!panelFeatureState.isOpen && !panelFeatureState.isHandled) {
            if (!panelFeatureState.isPrepared) return false;
            if (panelFeatureState.refreshMenuContent) {
                panelFeatureState.isPrepared = false;
                bl = this.preparePanel(panelFeatureState, keyEvent);
            } else {
                bl = true;
            }
            if (!bl) return false;
            this.openPanel(panelFeatureState, keyEvent);
            bl = true;
        } else {
            bl = panelFeatureState.isOpen;
            this.closePanel(panelFeatureState, true);
        }
        if (!bl) return bl;
        keyEvent = (AudioManager)this.mContext.getSystemService("audio");
        if (keyEvent != null) {
            keyEvent.playSoundEffect(0);
            return bl;
        }
        Log.w((String)"AppCompatDelegate", (String)"Couldn't get audio manager");
        return bl;
    }

    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        block18 : {
            block22 : {
                int n;
                WindowManager windowManager;
                block21 : {
                    block20 : {
                        Window.Callback callback;
                        block19 : {
                            if (panelFeatureState.isOpen) break block18;
                            if (this.isDestroyed()) {
                                return;
                            }
                            if (panelFeatureState.featureId == 0) {
                                n = (this.mContext.getResources().getConfiguration().screenLayout & 15) == 4 ? 1 : 0;
                                if (n != 0) {
                                    return;
                                }
                            }
                            if ((callback = this.getWindowCallback()) != null && !callback.onMenuOpened(panelFeatureState.featureId, (Menu)panelFeatureState.menu)) {
                                this.closePanel(panelFeatureState, true);
                                return;
                            }
                            windowManager = (WindowManager)this.mContext.getSystemService("window");
                            if (windowManager == null) {
                                return;
                            }
                            if (!this.preparePanel(panelFeatureState, keyEvent)) {
                                return;
                            }
                            if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) break block19;
                            if (panelFeatureState.createdPanelView == null || (keyEvent = panelFeatureState.createdPanelView.getLayoutParams()) == null || keyEvent.width != -1) break block20;
                            n = -1;
                            break block21;
                        }
                        if (panelFeatureState.decorView == null) {
                            if (!this.initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) {
                                return;
                            }
                        } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                            panelFeatureState.decorView.removeAllViews();
                        }
                        if (!this.initializePanelContent(panelFeatureState)) break block22;
                        if (!panelFeatureState.hasPanelItems()) {
                            return;
                        }
                        callback = panelFeatureState.shownPanelView.getLayoutParams();
                        keyEvent = callback;
                        if (callback == null) {
                            keyEvent = new ViewGroup.LayoutParams(-2, -2);
                        }
                        n = panelFeatureState.background;
                        panelFeatureState.decorView.setBackgroundResource(n);
                        callback = panelFeatureState.shownPanelView.getParent();
                        if (callback != null && callback instanceof ViewGroup) {
                            ((ViewGroup)callback).removeView(panelFeatureState.shownPanelView);
                        }
                        panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, (ViewGroup.LayoutParams)keyEvent);
                        if (!panelFeatureState.shownPanelView.hasFocus()) {
                            panelFeatureState.shownPanelView.requestFocus();
                        }
                    }
                    n = -2;
                }
                panelFeatureState.isHandled = false;
                keyEvent = new WindowManager.LayoutParams(n, -2, panelFeatureState.x, panelFeatureState.y, 1002, 8519680, -3);
                keyEvent.gravity = panelFeatureState.gravity;
                keyEvent.windowAnimations = panelFeatureState.windowAnimations;
                windowManager.addView((View)panelFeatureState.decorView, (ViewGroup.LayoutParams)keyEvent);
                panelFeatureState.isOpen = true;
                return;
            }
            return;
        }
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int n, KeyEvent keyEvent, int n2) {
        boolean bl;
        block7 : {
            boolean bl2;
            block6 : {
                bl = keyEvent.isSystem();
                bl2 = false;
                if (bl) {
                    return false;
                }
                if (panelFeatureState.isPrepared) break block6;
                bl = bl2;
                if (!this.preparePanel(panelFeatureState, keyEvent)) break block7;
            }
            bl = bl2;
            if (panelFeatureState.menu != null) {
                bl = panelFeatureState.menu.performShortcut(n, keyEvent, n2);
            }
        }
        if (bl && (n2 & 1) == 0 && this.mDecorContentParent == null) {
            this.closePanel(panelFeatureState, true);
        }
        return bl;
    }

    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        Window.Callback callback;
        if (this.isDestroyed()) {
            return false;
        }
        if (panelFeatureState.isPrepared) {
            return true;
        }
        if (this.mPreparedPanel != null && this.mPreparedPanel != panelFeatureState) {
            this.closePanel(this.mPreparedPanel, false);
        }
        if ((callback = this.getWindowCallback()) != null) {
            panelFeatureState.createdPanelView = callback.onCreatePanelView(panelFeatureState.featureId);
        }
        int n = panelFeatureState.featureId != 0 && panelFeatureState.featureId != 108 ? 0 : 1;
        if (n != 0 && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if (!(panelFeatureState.createdPanelView != null || n != 0 && this.peekSupportActionBar() instanceof ToolbarActionBar)) {
            if (panelFeatureState.menu == null || panelFeatureState.refreshMenuContent) {
                if (!(panelFeatureState.menu != null || this.initializePanelMenu(panelFeatureState) && panelFeatureState.menu != null)) {
                    return false;
                }
                if (n != 0 && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (!callback.onCreatePanelMenu(panelFeatureState.featureId, (Menu)panelFeatureState.menu)) {
                    panelFeatureState.setMenu(null);
                    if (n != 0 && this.mDecorContentParent != null) {
                        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            if (panelFeatureState.frozenActionViewState != null) {
                panelFeatureState.menu.restoreActionViewStates(panelFeatureState.frozenActionViewState);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!callback.onPreparePanel(0, panelFeatureState.createdPanelView, (Menu)panelFeatureState.menu)) {
                if (n != 0 && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            n = keyEvent != null ? keyEvent.getDeviceId() : -1;
            boolean bl = KeyCharacterMap.load((int)n).getKeyboardType() != 1;
            panelFeatureState.qwertyMode = bl;
            panelFeatureState.menu.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    private void reopenMenu(MenuBuilder object, boolean bl) {
        if (this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && (!ViewConfiguration.get((Context)this.mContext).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
            object = this.getWindowCallback();
            if (this.mDecorContentParent.isOverflowMenuShowing() && bl) {
                this.mDecorContentParent.hideOverflowMenu();
                if (!this.isDestroyed()) {
                    object.onPanelClosed(108, (Menu)this.getPanelState((int)0, (boolean)true).menu);
                    return;
                }
            } else if (object != null && !this.isDestroyed()) {
                if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                    this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                PanelFeatureState panelFeatureState = this.getPanelState(0, true);
                if (panelFeatureState.menu != null && !panelFeatureState.refreshMenuContent && object.onPreparePanel(0, panelFeatureState.createdPanelView, (Menu)panelFeatureState.menu)) {
                    object.onMenuOpened(108, (Menu)panelFeatureState.menu);
                    this.mDecorContentParent.showOverflowMenu();
                }
            }
            return;
        }
        object = this.getPanelState(0, true);
        object.refreshDecorView = true;
        this.closePanel((PanelFeatureState)object, false);
        this.openPanel((PanelFeatureState)object, null);
    }

    private int sanitizeWindowFeatureId(int n) {
        if (n == 8) {
            Log.i((String)"AppCompatDelegate", (String)"You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        if (n == 9) {
            Log.i((String)"AppCompatDelegate", (String)"You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
        return n;
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View view = this.mWindow.getDecorView();
        do {
            if (viewParent == null) {
                return true;
            }
            if (viewParent == view || !(viewParent instanceof View)) break;
            if (ViewCompat.isAttachedToWindow((View)viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        } while (true);
        return false;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureSubDecor();
        ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(view, layoutParams);
        this.mOriginalWindowCallback.onContentChanged();
    }

    View callActivityOnCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        if (this.mOriginalWindowCallback instanceof LayoutInflater.Factory && (view = ((LayoutInflater.Factory)this.mOriginalWindowCallback).onCreateView(string, context, attributeSet)) != null) {
            return view;
        }
        return null;
    }

    void callOnPanelClosed(int n, PanelFeatureState panelFeatureState, Menu menu) {
        PanelFeatureState panelFeatureState2 = panelFeatureState;
        Menu menu2 = menu;
        if (menu == null) {
            PanelFeatureState panelFeatureState3 = panelFeatureState;
            if (panelFeatureState == null) {
                panelFeatureState3 = panelFeatureState;
                if (n >= 0) {
                    panelFeatureState3 = panelFeatureState;
                    if (n < this.mPanels.length) {
                        panelFeatureState3 = this.mPanels[n];
                    }
                }
            }
            panelFeatureState2 = panelFeatureState3;
            menu2 = menu;
            if (panelFeatureState3 != null) {
                menu2 = panelFeatureState3.menu;
                panelFeatureState2 = panelFeatureState3;
            }
        }
        if (panelFeatureState2 != null && !panelFeatureState2.isOpen) {
            return;
        }
        if (!this.isDestroyed()) {
            this.mOriginalWindowCallback.onPanelClosed(n, menu2);
        }
    }

    void checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback callback = this.getWindowCallback();
        if (callback != null && !this.isDestroyed()) {
            callback.onPanelClosed(108, (Menu)menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    void closePanel(int n) {
        this.closePanel(this.getPanelState(n, true), true);
    }

    void closePanel(PanelFeatureState panelFeatureState, boolean bl) {
        if (bl && panelFeatureState.featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            this.checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        WindowManager windowManager = (WindowManager)this.mContext.getSystemService("window");
        if (windowManager != null && panelFeatureState.isOpen && panelFeatureState.decorView != null) {
            windowManager.removeView((View)panelFeatureState.decorView);
            if (bl) {
                this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        panelFeatureState.refreshDecorView = true;
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null;
        }
    }

    @Override
    public View createView(View view, String string, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        Object object = this.mAppCompatViewInflater;
        boolean bl = false;
        if (object == null) {
            object = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (object != null && !AppCompatViewInflater.class.getName().equals(object)) {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater)Class.forName((String)object).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Throwable throwable) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to instantiate custom view inflater ");
                    stringBuilder.append((String)object);
                    stringBuilder.append(". Falling back to default.");
                    Log.i((String)"AppCompatDelegate", (String)stringBuilder.toString(), (Throwable)throwable);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            } else {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            }
        }
        boolean bl2 = bl;
        if (IS_PRE_LOLLIPOP) {
            if (attributeSet instanceof XmlPullParser) {
                bl2 = bl;
                if (((XmlPullParser)attributeSet).getDepth() > 1) {
                    bl2 = true;
                }
            } else {
                bl2 = this.shouldInheritContext((ViewParent)view);
            }
        }
        return this.mAppCompatViewInflater.createView(view, string, context, attributeSet, bl2, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dismissPopups() {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                }
                catch (IllegalArgumentException illegalArgumentException) {}
            }
            this.mActionModePopup = null;
        }
        this.endOnGoingFadeAnimation();
        PanelFeatureState panelFeatureState = this.getPanelState(0, false);
        if (panelFeatureState != null && panelFeatureState.menu != null) {
            panelFeatureState.menu.close();
        }
    }

    @Override
    boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        boolean bl = true;
        if (n == 82 && this.mOriginalWindowCallback.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        n = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            bl = false;
        }
        if (bl) {
            return this.onKeyDown(n, keyEvent);
        }
        return this.onKeyUp(n, keyEvent);
    }

    void doInvalidatePanelMenu(int n) {
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (panelFeatureState.menu != null) {
            Bundle bundle = new Bundle();
            panelFeatureState.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelFeatureState.frozenActionViewState = bundle;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            panelFeatureState.menu.clear();
        }
        panelFeatureState.refreshMenuContent = true;
        panelFeatureState.refreshDecorView = true;
        if ((n == 108 || n == 0) && this.mDecorContentParent != null && (panelFeatureState = this.getPanelState(0, false)) != null) {
            panelFeatureState.isPrepared = false;
            this.preparePanel(panelFeatureState, null);
        }
    }

    void endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.cancel();
        }
    }

    PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] arrpanelFeatureState = this.mPanels;
        int n = arrpanelFeatureState != null ? arrpanelFeatureState.length : 0;
        for (int i = 0; i < n; ++i) {
            PanelFeatureState panelFeatureState = arrpanelFeatureState[i];
            if (panelFeatureState == null || panelFeatureState.menu != menu) continue;
            return panelFeatureState;
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends View> T findViewById(@IdRes int n) {
        this.ensureSubDecor();
        return (T)this.mWindow.findViewById(n);
    }

    protected PanelFeatureState getPanelState(int n, boolean bl) {
        Object object;
        PanelFeatureState[] arrpanelFeatureState;
        block6 : {
            block5 : {
                object = this.mPanels;
                if (object == null) break block5;
                arrpanelFeatureState = object;
                if (((PanelFeatureState[])object).length > n) break block6;
            }
            arrpanelFeatureState = new PanelFeatureState[n + 1];
            if (object != null) {
                System.arraycopy(object, 0, arrpanelFeatureState, 0, ((PanelFeatureState[])object).length);
            }
            this.mPanels = arrpanelFeatureState;
        }
        PanelFeatureState panelFeatureState = arrpanelFeatureState[n];
        object = panelFeatureState;
        if (panelFeatureState == null) {
            arrpanelFeatureState[n] = object = new PanelFeatureState(n);
        }
        return object;
    }

    ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    @Override
    public boolean hasWindowFeature(int n) {
        switch (this.sanitizeWindowFeatureId(n)) {
            default: {
                return false;
            }
            case 109: {
                return this.mOverlayActionBar;
            }
            case 108: {
                return this.mHasActionBar;
            }
            case 10: {
                return this.mOverlayActionMode;
            }
            case 5: {
                return this.mFeatureIndeterminateProgress;
            }
            case 2: {
                return this.mFeatureProgress;
            }
            case 1: 
        }
        return this.mWindowNoTitle;
    }

    @Override
    public void initWindowDecorActionBar() {
        this.ensureSubDecor();
        if (this.mHasActionBar) {
            if (this.mActionBar != null) {
                return;
            }
            if (this.mOriginalWindowCallback instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity)this.mOriginalWindowCallback, this.mOverlayActionBar);
            } else if (this.mOriginalWindowCallback instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog)this.mOriginalWindowCallback);
            }
            if (this.mActionBar != null) {
                this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
            return;
        }
    }

    @Override
    public void installViewFactory() {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.mContext);
        if (layoutInflater.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, this);
            return;
        }
        if (!(layoutInflater.getFactory2() instanceof AppCompatDelegateImplV9)) {
            Log.i((String)"AppCompatDelegate", (String)"The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override
    public void invalidateOptionsMenu() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null && actionBar.invalidateOptionsMenu()) {
            return;
        }
        this.invalidatePanelMenu(0);
    }

    boolean onBackPressed() {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
            return true;
        }
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null && actionBar.collapseActionView()) {
            return true;
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        ActionBar actionBar;
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBar = this.getSupportActionBar()) != null) {
            actionBar.onConfigurationChanged(configuration);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        this.applyDayNight();
    }

    @Override
    public void onCreate(Bundle object) {
        if (this.mOriginalWindowCallback instanceof Activity && NavUtils.getParentActivityName((Activity)this.mOriginalWindowCallback) != null) {
            object = this.peekSupportActionBar();
            if (object == null) {
                this.mEnableDefaultActionBarUp = true;
                return;
            }
            object.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    public final View onCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        View view2 = this.callActivityOnCreateView(view, string, context, attributeSet);
        if (view2 != null) {
            return view2;
        }
        return this.createView(view, string, context, attributeSet);
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string, context, attributeSet);
    }

    @Override
    public void onDestroy() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        super.onDestroy();
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy();
        }
    }

    boolean onKeyDown(int n, KeyEvent keyEvent) {
        boolean bl = true;
        if (n != 4) {
            if (n != 82) {
                return false;
            }
            this.onKeyDownPanel(0, keyEvent);
            return true;
        }
        if ((keyEvent.getFlags() & 128) == 0) {
            bl = false;
        }
        this.mLongPressBackDown = bl;
        return false;
    }

    @Override
    boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        Object object = this.getSupportActionBar();
        if (object != null && object.onKeyShortcut(n, keyEvent)) {
            return true;
        }
        if (this.mPreparedPanel != null && this.performPanelShortcut(this.mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.mPreparedPanel != null) {
                this.mPreparedPanel.isHandled = true;
            }
            return true;
        }
        if (this.mPreparedPanel == null) {
            object = this.getPanelState(0, true);
            this.preparePanel((PanelFeatureState)object, keyEvent);
            boolean bl = this.performPanelShortcut((PanelFeatureState)object, keyEvent.getKeyCode(), keyEvent, 1);
            object.isPrepared = false;
            if (bl) {
                return true;
            }
        }
        return false;
    }

    boolean onKeyUp(int n, KeyEvent object) {
        if (n != 4) {
            if (n != 82) {
                return false;
            }
            this.onKeyUpPanel(0, (KeyEvent)object);
            return true;
        }
        boolean bl = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        object = this.getPanelState(0, false);
        if (object != null && object.isOpen) {
            if (!bl) {
                this.closePanel((PanelFeatureState)object, true);
            }
            return true;
        }
        if (this.onBackPressed()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder object, MenuItem menuItem) {
        Window.Callback callback = this.getWindowCallback();
        if (callback != null && !this.isDestroyed() && (object = this.findMenuPanel(object.getRootMenu())) != null) {
            return callback.onMenuItemSelected(object.featureId, menuItem);
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        this.reopenMenu(menuBuilder, true);
    }

    @Override
    boolean onMenuOpened(int n, Menu object) {
        if (n == 108) {
            object = this.getSupportActionBar();
            if (object != null) {
                object.dispatchMenuVisibilityChanged(true);
            }
            return true;
        }
        return false;
    }

    @Override
    void onPanelClosed(int n, Menu object) {
        if (n == 108) {
            object = this.getSupportActionBar();
            if (object != null) {
                object.dispatchMenuVisibilityChanged(false);
                return;
            }
        } else if (n == 0) {
            object = this.getPanelState(n, true);
            if (object.isOpen) {
                this.closePanel((PanelFeatureState)object, false);
            }
        }
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        this.ensureSubDecor();
    }

    @Override
    public void onPostResume() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override
    public void onStop() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
    }

    void onSubDecorInstalled(ViewGroup viewGroup) {
    }

    @Override
    void onTitleChanged(CharSequence charSequence) {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(charSequence);
            return;
        }
        if (this.peekSupportActionBar() != null) {
            this.peekSupportActionBar().setWindowTitle(charSequence);
            return;
        }
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence);
        }
    }

    @Override
    public boolean requestWindowFeature(int n) {
        n = this.sanitizeWindowFeatureId(n);
        if (this.mWindowNoTitle && n == 108) {
            return false;
        }
        if (this.mHasActionBar && n == 1) {
            this.mHasActionBar = false;
        }
        switch (n) {
            default: {
                return this.mWindow.requestFeature(n);
            }
            case 109: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionBar = true;
                return true;
            }
            case 108: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mHasActionBar = true;
                return true;
            }
            case 10: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionMode = true;
                return true;
            }
            case 5: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            }
            case 2: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            }
            case 1: 
        }
        this.throwFeatureRequestIfSubDecorInstalled();
        this.mWindowNoTitle = true;
        return true;
    }

    @Override
    public void setContentView(int n) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from((Context)this.mContext).inflate(n, viewGroup);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override
    public void setSupportActionBar(Toolbar object) {
        if (!(this.mOriginalWindowCallback instanceof Activity)) {
            return;
        }
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar instanceof WindowDecorActionBar) {
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
        this.mMenuInflater = null;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        if (object != null) {
            object = new ToolbarActionBar((Toolbar)((Object)object), ((Activity)this.mOriginalWindowCallback).getTitle(), this.mAppCompatWindowCallback);
            this.mActionBar = object;
            this.mWindow.setCallback(object.getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback(this.mAppCompatWindowCallback);
        }
        this.invalidateOptionsMenu();
    }

    final boolean shouldAnimateActionModeView() {
        if (this.mSubDecorInstalled && this.mSubDecor != null && ViewCompat.isLaidOut((View)this.mSubDecor)) {
            return true;
        }
        return false;
    }

    @Override
    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        callback = new ActionModeCallbackWrapperV9(callback);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            this.mActionMode = actionBar.startActionMode(callback);
            if (this.mActionMode != null && this.mAppCompatCallback != null) {
                this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
            }
        }
        if (this.mActionMode == null) {
            this.mActionMode = this.startSupportActionModeFromWindow(callback);
        }
        return this.mActionMode;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    ActionMode startSupportActionModeFromWindow(@NonNull ActionMode.Callback object) {
        void var1_5;
        Object object2;
        block22 : {
            this.endOnGoingFadeAnimation();
            if (this.mActionMode != null) {
                this.mActionMode.finish();
            }
            object2 = object;
            if (!(object instanceof ActionModeCallbackWrapperV9)) {
                object2 = new ActionModeCallbackWrapperV9((ActionMode.Callback)object);
            }
            if (this.mAppCompatCallback != null && !this.isDestroyed()) {
                try {
                    ActionMode actionMode = this.mAppCompatCallback.onWindowStartingSupportActionMode((ActionMode.Callback)object2);
                    break block22;
                }
                catch (AbstractMethodError abstractMethodError) {}
            }
            Object var1_4 = null;
        }
        if (var1_5 != null) {
            this.mActionMode = var1_5;
        } else {
            ActionBarContextView actionBarContextView = this.mActionModeView;
            boolean bl = true;
            if (actionBarContextView == null) {
                if (this.mIsFloating) {
                    void var1_10;
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = this.mContext.getTheme();
                    theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme theme2 = this.mContext.getResources().newTheme();
                        theme2.setTo(theme);
                        theme2.applyStyle(typedValue.resourceId, true);
                        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, 0);
                        contextThemeWrapper.getTheme().setTo(theme2);
                    } else {
                        Context context = this.mContext;
                    }
                    this.mActionModeView = new ActionBarContextView((Context)var1_10);
                    this.mActionModePopup = new PopupWindow((Context)var1_10, null, R.attr.actionModePopupWindowStyle);
                    PopupWindowCompat.setWindowLayoutType(this.mActionModePopup, 2);
                    this.mActionModePopup.setContentView((View)this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    var1_10.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
                    int n = TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)var1_10.getResources().getDisplayMetrics());
                    this.mActionModeView.setContentHeight(n);
                    this.mActionModePopup.setHeight(-2);
                    this.mShowActionModePopup = new Runnable(){

                        @Override
                        public void run() {
                            AppCompatDelegateImplV9.this.mActionModePopup.showAtLocation((View)AppCompatDelegateImplV9.this.mActionModeView, 55, 0, 0);
                            AppCompatDelegateImplV9.this.endOnGoingFadeAnimation();
                            if (AppCompatDelegateImplV9.this.shouldAnimateActionModeView()) {
                                AppCompatDelegateImplV9.this.mActionModeView.setAlpha(0.0f);
                                AppCompatDelegateImplV9.this.mFadeAnim = ViewCompat.animate((View)AppCompatDelegateImplV9.this.mActionModeView).alpha(1.0f);
                                AppCompatDelegateImplV9.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                                    @Override
                                    public void onAnimationEnd(View view) {
                                        AppCompatDelegateImplV9.this.mActionModeView.setAlpha(1.0f);
                                        AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
                                        AppCompatDelegateImplV9.this.mFadeAnim = null;
                                    }

                                    @Override
                                    public void onAnimationStart(View view) {
                                        AppCompatDelegateImplV9.this.mActionModeView.setVisibility(0);
                                    }
                                });
                                return;
                            }
                            AppCompatDelegateImplV9.this.mActionModeView.setAlpha(1.0f);
                            AppCompatDelegateImplV9.this.mActionModeView.setVisibility(0);
                        }

                    };
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat)this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from((Context)this.getActionBarThemedContext()));
                        this.mActionModeView = (ActionBarContextView)viewStubCompat.inflate();
                    }
                }
            }
            if (this.mActionModeView != null) {
                this.endOnGoingFadeAnimation();
                this.mActionModeView.killMode();
                Context context = this.mActionModeView.getContext();
                ActionBarContextView actionBarContextView2 = this.mActionModeView;
                if (this.mActionModePopup != null) {
                    bl = false;
                }
                StandaloneActionMode standaloneActionMode = new StandaloneActionMode(context, actionBarContextView2, (ActionMode.Callback)object2, bl);
                if (object2.onCreateActionMode(standaloneActionMode, ((ActionMode)standaloneActionMode).getMenu())) {
                    ((ActionMode)standaloneActionMode).invalidate();
                    this.mActionModeView.initForMode(standaloneActionMode);
                    this.mActionMode = standaloneActionMode;
                    if (this.shouldAnimateActionModeView()) {
                        this.mActionModeView.setAlpha(0.0f);
                        this.mFadeAnim = ViewCompat.animate((View)this.mActionModeView).alpha(1.0f);
                        this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                            @Override
                            public void onAnimationEnd(View view) {
                                AppCompatDelegateImplV9.this.mActionModeView.setAlpha(1.0f);
                                AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
                                AppCompatDelegateImplV9.this.mFadeAnim = null;
                            }

                            @Override
                            public void onAnimationStart(View view) {
                                AppCompatDelegateImplV9.this.mActionModeView.setVisibility(0);
                                AppCompatDelegateImplV9.this.mActionModeView.sendAccessibilityEvent(32);
                                if (AppCompatDelegateImplV9.this.mActionModeView.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV9.this.mActionModeView.getParent());
                                }
                            }
                        });
                    } else {
                        this.mActionModeView.setAlpha(1.0f);
                        this.mActionModeView.setVisibility(0);
                        this.mActionModeView.sendAccessibilityEvent(32);
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View)this.mActionModeView.getParent());
                        }
                    }
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                } else {
                    this.mActionMode = null;
                }
            }
        }
        if (this.mActionMode != null && this.mAppCompatCallback != null) {
            this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
        }
        return this.mActionMode;
    }

    int updateStatusGuard(int n) {
        int n2;
        int n3;
        ActionBarContextView actionBarContextView = this.mActionModeView;
        int n4 = 0;
        if (actionBarContextView != null && this.mActionModeView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            int n5;
            int n6;
            actionBarContextView = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
            boolean bl = this.mActionModeView.isShown();
            int n7 = 1;
            if (bl) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect rect = this.mTempRect1;
                Rect rect2 = this.mTempRect2;
                rect.set(0, n, 0, 0);
                ViewUtils.computeFitSystemWindows((View)this.mSubDecor, rect, rect2);
                n2 = rect2.top == 0 ? n : 0;
                if (actionBarContextView.topMargin != n2) {
                    actionBarContextView.topMargin = n;
                    if (this.mStatusGuard == null) {
                        this.mStatusGuard = new View(this.mContext);
                        this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(R.color.abc_input_method_navigation_guard));
                        this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, n));
                    } else {
                        rect = this.mStatusGuard.getLayoutParams();
                        if (rect.height != n) {
                            rect.height = n;
                            this.mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)rect);
                        }
                    }
                    n3 = 1;
                } else {
                    n3 = 0;
                }
                if (this.mStatusGuard == null) {
                    n7 = 0;
                }
                n5 = n3;
                n2 = n7;
                n6 = n;
                if (!this.mOverlayActionMode) {
                    n5 = n3;
                    n2 = n7;
                    n6 = n;
                    if (n7 != 0) {
                        n6 = 0;
                        n5 = n3;
                        n2 = n7;
                    }
                }
            } else if (actionBarContextView.topMargin != 0) {
                actionBarContextView.topMargin = 0;
                n5 = 1;
                n2 = 0;
                n6 = n;
            } else {
                n2 = n5 = 0;
                n6 = n;
            }
            n3 = n2;
            n = n6;
            if (n5 != 0) {
                this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)actionBarContextView);
                n3 = n2;
                n = n6;
            }
        } else {
            n3 = 0;
        }
        if (this.mStatusGuard != null) {
            actionBarContextView = this.mStatusGuard;
            n2 = n3 != 0 ? n4 : 8;
            actionBarContextView.setVisibility(n2);
        }
        return n;
    }

    private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            AppCompatDelegateImplV9.this.checkCloseActionMenu(menuBuilder);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = AppCompatDelegateImplV9.this.getWindowCallback();
            if (callback != null) {
                callback.onMenuOpened(108, (Menu)menuBuilder);
            }
            return true;
        }
    }

    class ActionModeCallbackWrapperV9
    implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            if (AppCompatDelegateImplV9.this.mActionModePopup != null) {
                AppCompatDelegateImplV9.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV9.this.mShowActionModePopup);
            }
            if (AppCompatDelegateImplV9.this.mActionModeView != null) {
                AppCompatDelegateImplV9.this.endOnGoingFadeAnimation();
                AppCompatDelegateImplV9.this.mFadeAnim = ViewCompat.animate((View)AppCompatDelegateImplV9.this.mActionModeView).alpha(0.0f);
                AppCompatDelegateImplV9.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImplV9.this.mActionModeView.setVisibility(8);
                        if (AppCompatDelegateImplV9.this.mActionModePopup != null) {
                            AppCompatDelegateImplV9.this.mActionModePopup.dismiss();
                        } else if (AppCompatDelegateImplV9.this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV9.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImplV9.this.mActionModeView.removeAllViews();
                        AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
                        AppCompatDelegateImplV9.this.mFadeAnim = null;
                    }
                });
            }
            if (AppCompatDelegateImplV9.this.mAppCompatCallback != null) {
                AppCompatDelegateImplV9.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImplV9.this.mActionMode);
            }
            AppCompatDelegateImplV9.this.mActionMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

    }

    private class ListMenuDecorView
    extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        private boolean isOutOfBounds(int n, int n2) {
            if (n >= -5 && n2 >= -5 && n <= this.getWidth() + 5 && n2 <= this.getHeight() + 5) {
                return false;
            }
            return true;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (!AppCompatDelegateImplV9.this.dispatchKeyEvent(keyEvent) && !super.dispatchKeyEvent(keyEvent)) {
                return false;
            }
            return true;
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && this.isOutOfBounds((int)motionEvent.getX(), (int)motionEvent.getY())) {
                AppCompatDelegateImplV9.this.closePanel(0);
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public void setBackgroundResource(int n) {
            this.setBackgroundDrawable(AppCompatResources.getDrawable(this.getContext(), n));
        }
    }

    protected static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int n) {
            this.featureId = n;
            this.refreshDecorView = false;
        }

        void applyFrozenState() {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }

        public void clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        MenuView getListMenuView(MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
                this.listMenuPresenter.setCallback(callback);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        public boolean hasPanelItems() {
            View view = this.shownPanelView;
            boolean bl = false;
            if (view == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            if (this.listMenuPresenter.getAdapter().getCount() > 0) {
                bl = true;
            }
            return bl;
        }

        void onRestoreInstanceState(Parcelable parcelable) {
            parcelable = (PanelFeatureState$SavedState)parcelable;
            this.featureId = parcelable.featureId;
            this.wasLastOpen = parcelable.isOpen;
            this.frozenMenuState = parcelable.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        Parcelable onSaveInstanceState() {
            PanelFeatureState$SavedState panelFeatureState$SavedState = new PanelFeatureState$SavedState();
            panelFeatureState$SavedState.featureId = this.featureId;
            panelFeatureState$SavedState.isOpen = this.isOpen;
            if (this.menu != null) {
                panelFeatureState$SavedState.menuState = new Bundle();
                this.menu.savePresenterStates(panelFeatureState$SavedState.menuState);
            }
            return panelFeatureState$SavedState;
        }

        void setMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == this.menu) {
                return;
            }
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menuBuilder;
            if (menuBuilder != null && this.listMenuPresenter != null) {
                menuBuilder.addMenuPresenter(this.listMenuPresenter);
            }
        }

        void setStyle(Context object) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = object.getResources().newTheme();
            theme.setTo(object.getTheme());
            theme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme.applyStyle(typedValue.resourceId, true);
            }
            theme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme.applyStyle(typedValue.resourceId, true);
            } else {
                theme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
            }
            object = new ContextThemeWrapper((Context)object, 0);
            object.getTheme().setTo(theme);
            this.listPresenterContext = object;
            object = object.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.background = object.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = object.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            object.recycle();
        }
    }

    private static class PanelFeatureState$SavedState
    implements Parcelable {
        public static final Parcelable.Creator<PanelFeatureState$SavedState> CREATOR = new Parcelable.ClassLoaderCreator<PanelFeatureState$SavedState>(){

            public PanelFeatureState$SavedState createFromParcel(Parcel parcel) {
                return PanelFeatureState$SavedState.readFromParcel(parcel, null);
            }

            public PanelFeatureState$SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return PanelFeatureState$SavedState.readFromParcel(parcel, classLoader);
            }

            public PanelFeatureState$SavedState[] newArray(int n) {
                return new PanelFeatureState$SavedState[n];
            }
        };
        int featureId;
        boolean isOpen;
        Bundle menuState;

        PanelFeatureState$SavedState() {
        }

        static PanelFeatureState$SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
            PanelFeatureState$SavedState panelFeatureState$SavedState = new PanelFeatureState$SavedState();
            panelFeatureState$SavedState.featureId = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            panelFeatureState$SavedState.isOpen = bl;
            if (panelFeatureState$SavedState.isOpen) {
                panelFeatureState$SavedState.menuState = parcel.readBundle(classLoader);
            }
            return panelFeatureState$SavedState;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

    private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder object, boolean bl) {
            MenuBuilder menuBuilder = object.getRootMenu();
            boolean bl2 = menuBuilder != object;
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
            if (bl2) {
                object = menuBuilder;
            }
            if ((object = appCompatDelegateImplV9.findMenuPanel((Menu)object)) != null) {
                if (bl2) {
                    AppCompatDelegateImplV9.this.callOnPanelClosed(object.featureId, (PanelFeatureState)object, menuBuilder);
                    AppCompatDelegateImplV9.this.closePanel((PanelFeatureState)object, true);
                    return;
                }
                AppCompatDelegateImplV9.this.closePanel((PanelFeatureState)object, bl);
            }
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback;
            if (menuBuilder == null && AppCompatDelegateImplV9.this.mHasActionBar && (callback = AppCompatDelegateImplV9.this.getWindowCallback()) != null && !AppCompatDelegateImplV9.this.isDestroyed()) {
                callback.onMenuOpened(108, (Menu)menuBuilder);
            }
            return true;
        }
    }

}
