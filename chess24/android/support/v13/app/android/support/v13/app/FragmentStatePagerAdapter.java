/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.Fragment$SavedState
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Set;

@Deprecated
public abstract class FragmentStatePagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragStatePagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList();
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList();

    @Deprecated
    public FragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Deprecated
    @Override
    public void destroyItem(ViewGroup object, int n, Object object2) {
        object2 = (Fragment)object2;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= n) {
            this.mSavedState.add(null);
        }
        ArrayList<Fragment.SavedState> arrayList = this.mSavedState;
        object = object2.isAdded() ? this.mFragmentManager.saveFragmentInstanceState((Fragment)object2) : null;
        arrayList.set(n, (Fragment.SavedState)object);
        this.mFragments.set(n, null);
        this.mCurTransaction.remove((Fragment)object2);
    }

    @Deprecated
    @Override
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
    }

    @Deprecated
    public abstract Fragment getItem(int var1);

    @Deprecated
    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        Fragment.SavedState savedState;
        Fragment fragment;
        if (this.mFragments.size() > n && (fragment = this.mFragments.get(n)) != null) {
            return fragment;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        fragment = this.getItem(n);
        if (this.mSavedState.size() > n && (savedState = this.mSavedState.get(n)) != null) {
            fragment.setInitialSavedState(savedState);
        }
        while (this.mFragments.size() <= n) {
            this.mFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        FragmentCompat.setUserVisibleHint(fragment, false);
        this.mFragments.set(n, fragment);
        this.mCurTransaction.add(viewGroup.getId(), fragment);
        return fragment;
    }

    @Deprecated
    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (((Fragment)object).getView() == view) {
            return true;
        }
        return false;
    }

    @Deprecated
    @Override
    public void restoreState(Parcelable parcelable, ClassLoader object) {
        if (parcelable != null) {
            int n;
            parcelable = (Bundle)parcelable;
            parcelable.setClassLoader((ClassLoader)object);
            object = parcelable.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (object != null) {
                for (n = 0; n < ((Parcelable[])object).length; ++n) {
                    this.mSavedState.add((Fragment.SavedState)object[n]);
                }
            }
            for (String string : parcelable.keySet()) {
                if (!string.startsWith("f")) continue;
                n = Integer.parseInt(string.substring(1));
                Object object2 = this.mFragmentManager.getFragment((Bundle)parcelable, string);
                if (object2 != null) {
                    while (this.mFragments.size() <= n) {
                        this.mFragments.add(null);
                    }
                    FragmentCompat.setMenuVisibility((Fragment)object2, false);
                    this.mFragments.set(n, (Fragment)object2);
                    continue;
                }
                object2 = new StringBuilder();
                object2.append("Bad fragment at key ");
                object2.append(string);
                Log.w((String)"FragStatePagerAdapter", (String)object2.toString());
            }
        }
    }

    @Deprecated
    @Override
    public Parcelable saveState() {
        Object object;
        Bundle bundle;
        if (this.mSavedState.size() > 0) {
            object = new Bundle();
            bundle = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray((T[])bundle);
            object.putParcelableArray("states", (Parcelable[])bundle);
        } else {
            object = null;
        }
        for (int i = 0; i < this.mFragments.size(); ++i) {
            Fragment fragment = this.mFragments.get(i);
            bundle = object;
            if (fragment != null) {
                bundle = object;
                if (fragment.isAdded()) {
                    bundle = object;
                    if (object == null) {
                        bundle = new Bundle();
                    }
                    object = new StringBuilder();
                    object.append("f");
                    object.append(i);
                    object = object.toString();
                    this.mFragmentManager.putFragment(bundle, (String)object, fragment);
                }
            }
            object = bundle;
        }
        return object;
    }

    @Deprecated
    @Override
    public void setPrimaryItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup = (Fragment)object;
        if (viewGroup != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                FragmentCompat.setUserVisibleHint(this.mCurrentPrimaryItem, false);
            }
            if (viewGroup != null) {
                viewGroup.setMenuVisibility(true);
                FragmentCompat.setUserVisibleHint((Fragment)viewGroup, true);
            }
            this.mCurrentPrimaryItem = viewGroup;
        }
    }

    @Deprecated
    @Override
    public void startUpdate(ViewGroup object) {
        if (object.getId() == -1) {
            object = new StringBuilder();
            object.append("ViewPager with adapter ");
            object.append(this);
            object.append(" requires a view id");
            throw new IllegalStateException(object.toString());
        }
    }
}
