/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TabHost
 *  android.widget.TabHost$OnTabChangeListener
 *  android.widget.TabHost$TabContentFactory
 *  android.widget.TabHost$TabSpec
 *  android.widget.TabWidget
 */
package android.support.v4.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import java.util.ArrayList;

public class FragmentTabHost
extends TabHost
implements TabHost.OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs = new ArrayList();

    public FragmentTabHost(Context context) {
        super(context, null);
        this.initFragmentTabHost(context, null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initFragmentTabHost(context, attributeSet);
    }

    @Nullable
    private FragmentTransaction doTabChanged(@Nullable String object, @Nullable FragmentTransaction fragmentTransaction) {
        TabInfo tabInfo = this.getTabInfoForTag((String)object);
        object = fragmentTransaction;
        if (this.mLastTab != tabInfo) {
            object = fragmentTransaction;
            if (fragmentTransaction == null) {
                object = this.mFragmentManager.beginTransaction();
            }
            if (this.mLastTab != null && this.mLastTab.fragment != null) {
                object.detach(this.mLastTab.fragment);
            }
            if (tabInfo != null) {
                if (tabInfo.fragment == null) {
                    tabInfo.fragment = Fragment.instantiate(this.mContext, tabInfo.clss.getName(), tabInfo.args);
                    object.add(this.mContainerId, tabInfo.fragment, tabInfo.tag);
                } else {
                    object.attach(tabInfo.fragment);
                }
            }
            this.mLastTab = tabInfo;
        }
        return object;
    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout)this.findViewById(this.mContainerId);
            if (this.mRealTabContent == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No tab content FrameLayout found for id ");
                stringBuilder.append(this.mContainerId);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    private void ensureHierarchy(Context context) {
        if (this.findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            this.addView((View)linearLayout, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2, 0.0f));
            tabWidget = new FrameLayout(context);
            tabWidget.setId(16908305);
            linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, 0, 0.0f));
            context = new FrameLayout(context);
            this.mRealTabContent = context;
            this.mRealTabContent.setId(this.mContainerId);
            linearLayout.addView((View)context, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Nullable
    private TabInfo getTabInfoForTag(String string) {
        int n = this.mTabs.size();
        for (int i = 0; i < n; ++i) {
            TabInfo tabInfo = this.mTabs.get(i);
            if (!tabInfo.tag.equals(string)) continue;
            return tabInfo;
        }
        return null;
    }

    private void initFragmentTabHost(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.mContainerId = context.getResourceId(0, 0);
        context.recycle();
        super.setOnTabChangedListener((TabHost.OnTabChangeListener)this);
    }

    public void addTab(@NonNull TabHost.TabSpec tabSpec, @NonNull Class<?> object, @Nullable Bundle object2) {
        tabSpec.setContent((TabHost.TabContentFactory)new DummyTabFactory(this.mContext));
        String string = tabSpec.getTag();
        object = new TabInfo(string, (Class<?>)object, (Bundle)object2);
        if (this.mAttached) {
            object.fragment = this.mFragmentManager.findFragmentByTag(string);
            if (object.fragment != null && !object.fragment.isDetached()) {
                object2 = this.mFragmentManager.beginTransaction();
                object2.detach(object.fragment);
                object2.commit();
            }
        }
        this.mTabs.add((TabInfo)object);
        this.addTab(tabSpec);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String string = this.getCurrentTabTag();
        int n = this.mTabs.size();
        FragmentTransaction fragmentTransaction = null;
        for (int i = 0; i < n; ++i) {
            TabInfo tabInfo = this.mTabs.get(i);
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tabInfo.tag);
            FragmentTransaction fragmentTransaction2 = fragmentTransaction;
            if (tabInfo.fragment != null) {
                fragmentTransaction2 = fragmentTransaction;
                if (!tabInfo.fragment.isDetached()) {
                    if (tabInfo.tag.equals(string)) {
                        this.mLastTab = tabInfo;
                        fragmentTransaction2 = fragmentTransaction;
                    } else {
                        fragmentTransaction2 = fragmentTransaction;
                        if (fragmentTransaction == null) {
                            fragmentTransaction2 = this.mFragmentManager.beginTransaction();
                        }
                        fragmentTransaction2.detach(tabInfo.fragment);
                    }
                }
            }
            fragmentTransaction = fragmentTransaction2;
        }
        this.mAttached = true;
        if ((fragmentTransaction = this.doTabChanged(string, fragmentTransaction)) != null) {
            fragmentTransaction.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setCurrentTabByTag(object.curTab);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.curTab = this.getCurrentTabTag();
        return savedState;
    }

    public void onTabChanged(String string) {
        FragmentTransaction fragmentTransaction;
        if (this.mAttached && (fragmentTransaction = this.doTabChanged(string, null)) != null) {
            fragmentTransaction.commit();
        }
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(string);
        }
    }

    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager fragmentManager) {
        this.ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.ensureContent();
    }

    public void setup(Context context, FragmentManager fragmentManager, int n) {
        this.ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mContainerId = n;
        this.ensureContent();
        this.mRealTabContent.setId(n);
        if (this.getId() == -1) {
            this.setId(16908306);
        }
    }

    static class DummyTabFactory
    implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        public View createTabContent(String string) {
            string = new View(this.mContext);
            string.setMinimumWidth(0);
            string.setMinimumHeight(0);
            return string;
        }
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        String curTab;

        SavedState(Parcel parcel) {
            super(parcel);
            this.curTab = parcel.readString();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FragmentTabHost.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
            stringBuilder.append(" curTab=");
            stringBuilder.append(this.curTab);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeString(this.curTab);
        }

    }

    static final class TabInfo {
        @Nullable
        final Bundle args;
        @NonNull
        final Class<?> clss;
        Fragment fragment;
        @NonNull
        final String tag;

        TabInfo(@NonNull String string, @NonNull Class<?> class_, @Nullable Bundle bundle) {
            this.tag = string;
            this.clss = class_;
            this.args = bundle;
        }
    }

}
