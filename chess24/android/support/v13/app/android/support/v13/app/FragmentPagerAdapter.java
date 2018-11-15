/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

@Deprecated
public abstract class FragmentPagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentPagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;

    @Deprecated
    public FragmentPagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    private static String makeFragmentName(int n, long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android:switcher:");
        stringBuilder.append(n);
        stringBuilder.append(":");
        stringBuilder.append(l);
        return stringBuilder.toString();
    }

    @Deprecated
    @Override
    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment)object);
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
    public long getItemId(int n) {
        return n;
    }

    @Deprecated
    @Override
    public Object instantiateItem(ViewGroup object, int n) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long l = this.getItemId(n);
        String string = FragmentPagerAdapter.makeFragmentName(object.getId(), l);
        if ((string = this.mFragmentManager.findFragmentByTag(string)) != null) {
            this.mCurTransaction.attach((Fragment)string);
            object = string;
        } else {
            string = this.getItem(n);
            this.mCurTransaction.add(object.getId(), (Fragment)string, FragmentPagerAdapter.makeFragmentName(object.getId(), l));
            object = string;
        }
        if (object != this.mCurrentPrimaryItem) {
            object.setMenuVisibility(false);
            FragmentCompat.setUserVisibleHint((Fragment)object, false);
        }
        return object;
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
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Deprecated
    @Override
    public Parcelable saveState() {
        return null;
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
