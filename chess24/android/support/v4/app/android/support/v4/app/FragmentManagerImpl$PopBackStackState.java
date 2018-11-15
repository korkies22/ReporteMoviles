/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import java.util.ArrayList;

private class FragmentManagerImpl.PopBackStackState
implements FragmentManagerImpl.OpGenerator {
    final int mFlags;
    final int mId;
    final String mName;

    FragmentManagerImpl.PopBackStackState(String string, int n, int n2) {
        this.mName = string;
        this.mId = n;
        this.mFlags = n2;
    }

    @Override
    public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        FragmentManager fragmentManager;
        if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && (fragmentManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager()) != null && fragmentManager.popBackStackImmediate()) {
            return false;
        }
        return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
    }
}
