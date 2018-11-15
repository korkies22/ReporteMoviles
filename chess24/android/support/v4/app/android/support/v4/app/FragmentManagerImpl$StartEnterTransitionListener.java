/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManagerImpl;
import java.util.ArrayList;

static class FragmentManagerImpl.StartEnterTransitionListener
implements Fragment.OnStartEnterTransitionListener {
    private final boolean mIsBack;
    private int mNumPostponed;
    private final BackStackRecord mRecord;

    FragmentManagerImpl.StartEnterTransitionListener(BackStackRecord backStackRecord, boolean bl) {
        this.mIsBack = bl;
        this.mRecord = backStackRecord;
    }

    static /* synthetic */ boolean access$300(FragmentManagerImpl.StartEnterTransitionListener startEnterTransitionListener) {
        return startEnterTransitionListener.mIsBack;
    }

    static /* synthetic */ BackStackRecord access$400(FragmentManagerImpl.StartEnterTransitionListener startEnterTransitionListener) {
        return startEnterTransitionListener.mRecord;
    }

    public void cancelTransaction() {
        this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }

    public void completeTransaction() {
        int n = this.mNumPostponed;
        n = n > 0 ? 1 : 0;
        FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
        int n2 = fragmentManagerImpl.mAdded.size();
        for (int i = 0; i < n2; ++i) {
            Fragment fragment = fragmentManagerImpl.mAdded.get(i);
            fragment.setOnStartEnterTransitionListener(null);
            if (n == 0 || !fragment.isPostponed()) continue;
            fragment.startPostponedEnterTransition();
        }
        this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, (boolean)(n ^ 1), true);
    }

    public boolean isReady() {
        if (this.mNumPostponed == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onStartEnterTransition() {
        --this.mNumPostponed;
        if (this.mNumPostponed != 0) {
            return;
        }
        this.mRecord.mManager.scheduleCommit();
    }

    @Override
    public void startListening() {
        ++this.mNumPostponed;
    }
}
