/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ListAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

protected static final class AppCompatDelegateImplV9.PanelFeatureState {
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

    AppCompatDelegateImplV9.PanelFeatureState(int n) {
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
        parcelable = (SavedState)parcelable;
        this.featureId = parcelable.featureId;
        this.wasLastOpen = parcelable.isOpen;
        this.frozenMenuState = parcelable.menuState;
        this.shownPanelView = null;
        this.decorView = null;
    }

    Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.featureId = this.featureId;
        savedState.isOpen = this.isOpen;
        if (this.menu != null) {
            savedState.menuState = new Bundle();
            this.menu.savePresenterStates(savedState.menuState);
        }
        return savedState;
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

    private static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return SavedState.readFromParcel(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return SavedState.readFromParcel(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int featureId;
        boolean isOpen;
        Bundle menuState;

        SavedState() {
        }

        static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
            SavedState savedState = new SavedState();
            savedState.featureId = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            savedState.isOpen = bl;
            if (savedState.isOpen) {
                savedState.menuState = parcel.readBundle(classLoader);
            }
            return savedState;
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

}
