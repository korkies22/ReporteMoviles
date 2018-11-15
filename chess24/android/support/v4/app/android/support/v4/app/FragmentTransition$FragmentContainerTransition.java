/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransition;

static class FragmentTransition.FragmentContainerTransition {
    public Fragment firstOut;
    public boolean firstOutIsPop;
    public BackStackRecord firstOutTransaction;
    public Fragment lastIn;
    public boolean lastInIsPop;
    public BackStackRecord lastInTransaction;

    FragmentTransition.FragmentContainerTransition() {
    }
}
