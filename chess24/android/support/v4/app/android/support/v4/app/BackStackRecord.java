/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransition;
import android.support.v4.util.LogWriter;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

final class BackStackRecord
extends FragmentTransaction
implements FragmentManager.BackStackEntry,
FragmentManagerImpl.OpGenerator {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_SHOW = 5;
    static final int OP_UNSET_PRIMARY_NAV = 9;
    static final String TAG = "FragmentManager";
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack = true;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    ArrayList<Runnable> mCommitRunnables;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    int mIndex = -1;
    final FragmentManagerImpl mManager;
    String mName;
    ArrayList<Op> mOps = new ArrayList();
    int mPopEnterAnim;
    int mPopExitAnim;
    boolean mReorderingAllowed = false;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        this.mManager = fragmentManagerImpl;
    }

    private void doAddOp(int n, Fragment object, String charSequence, int n2) {
        Serializable serializable = object.getClass();
        int n3 = serializable.getModifiers();
        if (!serializable.isAnonymousClass() && Modifier.isPublic(n3) && (!serializable.isMemberClass() || Modifier.isStatic(n3))) {
            object.mFragmentManager = this.mManager;
            if (charSequence != null) {
                if (object.mTag != null && !charSequence.equals(object.mTag)) {
                    serializable = new StringBuilder();
                    serializable.append("Can't change tag of fragment ");
                    serializable.append(object);
                    serializable.append(": was ");
                    serializable.append(object.mTag);
                    serializable.append(" now ");
                    serializable.append((String)charSequence);
                    throw new IllegalStateException(serializable.toString());
                }
                object.mTag = charSequence;
            }
            if (n != 0) {
                if (n == -1) {
                    serializable = new StringBuilder();
                    serializable.append("Can't add fragment ");
                    serializable.append(object);
                    serializable.append(" with tag ");
                    serializable.append((String)charSequence);
                    serializable.append(" to container view with no id");
                    throw new IllegalArgumentException(serializable.toString());
                }
                if (object.mFragmentId != 0 && object.mFragmentId != n) {
                    charSequence = new StringBuilder();
                    charSequence.append("Can't change container ID of fragment ");
                    charSequence.append(object);
                    charSequence.append(": was ");
                    charSequence.append(object.mFragmentId);
                    charSequence.append(" now ");
                    charSequence.append(n);
                    throw new IllegalStateException(charSequence.toString());
                }
                object.mFragmentId = n;
                object.mContainerId = n;
            }
            this.addOp(new Op(n2, (Fragment)object));
            return;
        }
        object = new StringBuilder();
        object.append("Fragment ");
        object.append(serializable.getCanonicalName());
        object.append(" must be a public static class to be  properly recreated from");
        object.append(" instance state.");
        throw new IllegalStateException(object.toString());
    }

    private static boolean isFragmentPostponed(Op object) {
        object = object.fragment;
        if (object != null && object.mAdded && object.mView != null && !object.mDetached && !object.mHidden && object.isPostponed()) {
            return true;
        }
        return false;
    }

    @Override
    public FragmentTransaction add(int n, Fragment fragment) {
        this.doAddOp(n, fragment, null, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(int n, Fragment fragment, String string) {
        this.doAddOp(n, fragment, string, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(Fragment fragment, String string) {
        this.doAddOp(0, fragment, string, 1);
        return this;
    }

    void addOp(Op op) {
        this.mOps.add(op);
        op.enterAnim = this.mEnterAnim;
        op.exitAnim = this.mExitAnim;
        op.popEnterAnim = this.mPopEnterAnim;
        op.popExitAnim = this.mPopExitAnim;
    }

    @Override
    public FragmentTransaction addSharedElement(View object, String charSequence) {
        if (FragmentTransition.supportsTransition()) {
            if ((object = ViewCompat.getTransitionName((View)object)) == null) {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
            if (this.mSharedElementSourceNames == null) {
                this.mSharedElementSourceNames = new ArrayList();
                this.mSharedElementTargetNames = new ArrayList();
            } else {
                if (this.mSharedElementTargetNames.contains(charSequence)) {
                    object = new StringBuilder();
                    object.append("A shared element with the target name '");
                    object.append((String)charSequence);
                    object.append("' has already been added to the transaction.");
                    throw new IllegalArgumentException(object.toString());
                }
                if (this.mSharedElementSourceNames.contains(object)) {
                    charSequence = new StringBuilder();
                    charSequence.append("A shared element with the source name '");
                    charSequence.append((String)object);
                    charSequence.append(" has already been added to the transaction.");
                    throw new IllegalArgumentException(charSequence.toString());
                }
            }
            this.mSharedElementSourceNames.add((String)object);
            this.mSharedElementTargetNames.add((String)charSequence);
        }
        return this;
    }

    @Override
    public FragmentTransaction addToBackStack(String string) {
        if (!this.mAllowAddToBackStack) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.mAddToBackStack = true;
        this.mName = string;
        return this;
    }

    @Override
    public FragmentTransaction attach(Fragment fragment) {
        this.addOp(new Op(7, fragment));
        return this;
    }

    void bumpBackStackNesting(int n) {
        Object object;
        if (!this.mAddToBackStack) {
            return;
        }
        if (FragmentManagerImpl.DEBUG) {
            object = new StringBuilder();
            object.append("Bump nesting in ");
            object.append(this);
            object.append(" by ");
            object.append(n);
            Log.v((String)TAG, (String)object.toString());
        }
        int n2 = this.mOps.size();
        for (int i = 0; i < n2; ++i) {
            object = this.mOps.get(i);
            if (object.fragment == null) continue;
            Object object2 = object.fragment;
            object2.mBackStackNesting += n;
            if (!FragmentManagerImpl.DEBUG) continue;
            object2 = new StringBuilder();
            object2.append("Bump nesting of ");
            object2.append(object.fragment);
            object2.append(" to ");
            object2.append(object.fragment.mBackStackNesting);
            Log.v((String)TAG, (String)object2.toString());
        }
    }

    @Override
    public int commit() {
        return this.commitInternal(false);
    }

    @Override
    public int commitAllowingStateLoss() {
        return this.commitInternal(true);
    }

    int commitInternal(boolean bl) {
        if (this.mCommitted) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.DEBUG) {
            Appendable appendable = new StringBuilder();
            appendable.append("Commit: ");
            appendable.append(this);
            Log.v((String)TAG, (String)appendable.toString());
            appendable = new PrintWriter(new LogWriter(TAG));
            this.dump("  ", null, (PrintWriter)appendable, null);
            appendable.close();
        }
        this.mCommitted = true;
        this.mIndex = this.mAddToBackStack ? this.mManager.allocBackStackIndex(this) : -1;
        this.mManager.enqueueAction(this, bl);
        return this.mIndex;
    }

    @Override
    public void commitNow() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    @Override
    public void commitNowAllowingStateLoss() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    @Override
    public FragmentTransaction detach(Fragment fragment) {
        this.addOp(new Op(6, fragment));
        return this;
    }

    @Override
    public FragmentTransaction disallowAddToBackStack() {
        if (this.mAddToBackStack) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.mAllowAddToBackStack = false;
        return this;
    }

    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.dump(string, printWriter, true);
    }

    public void dump(String string, PrintWriter printWriter, boolean bl) {
        if (bl) {
            printWriter.print(string);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.mCommitted);
            if (this.mTransition != 0) {
                printWriter.print(string);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.mTransition));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(string);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mEnterAnim));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(string);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mPopEnterAnim));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(string);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(string);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (!this.mOps.isEmpty()) {
            printWriter.print(string);
            printWriter.println("Operations:");
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string);
            charSequence.append("    ");
            ((StringBuilder)charSequence).toString();
            int n = this.mOps.size();
            for (int i = 0; i < n; ++i) {
                Op op = this.mOps.get(i);
                switch (op.cmd) {
                    default: {
                        charSequence = new StringBuilder();
                        charSequence.append("cmd=");
                        charSequence.append(op.cmd);
                        charSequence = ((StringBuilder)charSequence).toString();
                        break;
                    }
                    case 9: {
                        charSequence = "UNSET_PRIMARY_NAV";
                        break;
                    }
                    case 8: {
                        charSequence = "SET_PRIMARY_NAV";
                        break;
                    }
                    case 7: {
                        charSequence = "ATTACH";
                        break;
                    }
                    case 6: {
                        charSequence = "DETACH";
                        break;
                    }
                    case 5: {
                        charSequence = "SHOW";
                        break;
                    }
                    case 4: {
                        charSequence = "HIDE";
                        break;
                    }
                    case 3: {
                        charSequence = "REMOVE";
                        break;
                    }
                    case 2: {
                        charSequence = "REPLACE";
                        break;
                    }
                    case 1: {
                        charSequence = "ADD";
                        break;
                    }
                    case 0: {
                        charSequence = "NULL";
                    }
                }
                printWriter.print(string);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print((String)charSequence);
                printWriter.print(" ");
                printWriter.println(op.fragment);
                if (!bl) continue;
                if (op.enterAnim != 0 || op.exitAnim != 0) {
                    printWriter.print(string);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(op.enterAnim));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(op.exitAnim));
                }
                if (op.popEnterAnim == 0 && op.popExitAnim == 0) continue;
                printWriter.print(string);
                printWriter.print("popEnterAnim=#");
                printWriter.print(Integer.toHexString(op.popEnterAnim));
                printWriter.print(" popExitAnim=#");
                printWriter.println(Integer.toHexString(op.popExitAnim));
            }
        }
    }

    void executeOps() {
        int n = this.mOps.size();
        for (int i = 0; i < n; ++i) {
            int n2;
            Op op = this.mOps.get(i);
            Object object = op.fragment;
            if (object != null) {
                object.setNextTransition(this.mTransition, this.mTransitionStyle);
            }
            if ((n2 = op.cmd) != 1) {
                switch (n2) {
                    default: {
                        object = new StringBuilder();
                        object.append("Unknown cmd: ");
                        object.append(op.cmd);
                        throw new IllegalArgumentException(object.toString());
                    }
                    case 9: {
                        this.mManager.setPrimaryNavigationFragment(null);
                        break;
                    }
                    case 8: {
                        this.mManager.setPrimaryNavigationFragment((Fragment)object);
                        break;
                    }
                    case 7: {
                        object.setNextAnim(op.enterAnim);
                        this.mManager.attachFragment((Fragment)object);
                        break;
                    }
                    case 6: {
                        object.setNextAnim(op.exitAnim);
                        this.mManager.detachFragment((Fragment)object);
                        break;
                    }
                    case 5: {
                        object.setNextAnim(op.enterAnim);
                        this.mManager.showFragment((Fragment)object);
                        break;
                    }
                    case 4: {
                        object.setNextAnim(op.exitAnim);
                        this.mManager.hideFragment((Fragment)object);
                        break;
                    }
                    case 3: {
                        object.setNextAnim(op.exitAnim);
                        this.mManager.removeFragment((Fragment)object);
                        break;
                    }
                }
            } else {
                object.setNextAnim(op.enterAnim);
                this.mManager.addFragment((Fragment)object, false);
            }
            if (this.mReorderingAllowed || op.cmd == 1 || object == null) continue;
            this.mManager.moveFragmentToExpectedState((Fragment)object);
        }
        if (!this.mReorderingAllowed) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    void executePopOps(boolean bl) {
        for (int i = this.mOps.size() - 1; i >= 0; --i) {
            int n;
            Op op = this.mOps.get(i);
            Object object = op.fragment;
            if (object != null) {
                object.setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
            }
            if ((n = op.cmd) != 1) {
                switch (n) {
                    default: {
                        object = new StringBuilder();
                        object.append("Unknown cmd: ");
                        object.append(op.cmd);
                        throw new IllegalArgumentException(object.toString());
                    }
                    case 9: {
                        this.mManager.setPrimaryNavigationFragment((Fragment)object);
                        break;
                    }
                    case 8: {
                        this.mManager.setPrimaryNavigationFragment(null);
                        break;
                    }
                    case 7: {
                        object.setNextAnim(op.popExitAnim);
                        this.mManager.detachFragment((Fragment)object);
                        break;
                    }
                    case 6: {
                        object.setNextAnim(op.popEnterAnim);
                        this.mManager.attachFragment((Fragment)object);
                        break;
                    }
                    case 5: {
                        object.setNextAnim(op.popExitAnim);
                        this.mManager.hideFragment((Fragment)object);
                        break;
                    }
                    case 4: {
                        object.setNextAnim(op.popEnterAnim);
                        this.mManager.showFragment((Fragment)object);
                        break;
                    }
                    case 3: {
                        object.setNextAnim(op.popEnterAnim);
                        this.mManager.addFragment((Fragment)object, false);
                        break;
                    }
                }
            } else {
                object.setNextAnim(op.popExitAnim);
                this.mManager.removeFragment((Fragment)object);
            }
            if (this.mReorderingAllowed || op.cmd == 3 || object == null) continue;
            this.mManager.moveFragmentToExpectedState((Fragment)object);
        }
        if (!this.mReorderingAllowed && bl) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    Fragment expandOps(ArrayList<Fragment> arrayList, Fragment object) {
        int n = 0;
        Fragment fragment = object;
        while (n < this.mOps.size()) {
            int n2;
            Op op = this.mOps.get(n);
            switch (op.cmd) {
                default: {
                    object = fragment;
                    n2 = n;
                    break;
                }
                case 8: {
                    this.mOps.add(n, new Op(9, fragment));
                    n2 = n + 1;
                    object = op.fragment;
                    break;
                }
                case 3: 
                case 6: {
                    arrayList.remove(op.fragment);
                    object = fragment;
                    n2 = n;
                    if (op.fragment != fragment) break;
                    this.mOps.add(n, new Op(9, op.fragment));
                    n2 = n + 1;
                    object = null;
                    break;
                }
                case 2: {
                    Fragment fragment2 = op.fragment;
                    int n3 = fragment2.mContainerId;
                    object = fragment;
                    boolean bl = false;
                    for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                        Fragment fragment3 = arrayList.get(n2);
                        int n4 = n;
                        fragment = object;
                        boolean bl2 = bl;
                        if (fragment3.mContainerId == n3) {
                            if (fragment3 == fragment2) {
                                bl2 = true;
                                n4 = n;
                                fragment = object;
                            } else {
                                n4 = n;
                                fragment = object;
                                if (fragment3 == object) {
                                    this.mOps.add(n, new Op(9, fragment3));
                                    n4 = n + 1;
                                    fragment = null;
                                }
                                object = new Op(3, fragment3);
                                object.enterAnim = op.enterAnim;
                                object.popEnterAnim = op.popEnterAnim;
                                object.exitAnim = op.exitAnim;
                                object.popExitAnim = op.popExitAnim;
                                this.mOps.add(n4, (Op)object);
                                arrayList.remove(fragment3);
                                ++n4;
                                bl2 = bl;
                            }
                        }
                        n = n4;
                        object = fragment;
                        bl = bl2;
                    }
                    if (bl) {
                        this.mOps.remove(n);
                        --n;
                    } else {
                        op.cmd = 1;
                        arrayList.add(fragment2);
                    }
                    n2 = n;
                    break;
                }
                case 1: 
                case 7: {
                    arrayList.add(op.fragment);
                    n2 = n;
                    object = fragment;
                }
            }
            n = n2 + 1;
            fragment = object;
        }
        return fragment;
    }

    @Override
    public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (FragmentManagerImpl.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Run: ");
            stringBuilder.append(this);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
        }
        return true;
    }

    @Override
    public CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
        }
        return this.mBreadCrumbShortTitleText;
    }

    @Override
    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    @Override
    public CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
        }
        return this.mBreadCrumbTitleText;
    }

    @Override
    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    @Override
    public int getId() {
        return this.mIndex;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    public int getTransition() {
        return this.mTransition;
    }

    public int getTransitionStyle() {
        return this.mTransitionStyle;
    }

    @Override
    public FragmentTransaction hide(Fragment fragment) {
        this.addOp(new Op(4, fragment));
        return this;
    }

    boolean interactsWith(int n) {
        int n2 = this.mOps.size();
        for (int i = 0; i < n2; ++i) {
            Op op = this.mOps.get(i);
            int n3 = op.fragment != null ? op.fragment.mContainerId : 0;
            if (n3 == 0 || n3 != n) continue;
            return true;
        }
        return false;
    }

    boolean interactsWith(ArrayList<BackStackRecord> arrayList, int n, int n2) {
        if (n2 == n) {
            return false;
        }
        int n3 = this.mOps.size();
        int n4 = -1;
        for (int i = 0; i < n3; ++i) {
            Object object = this.mOps.get(i);
            int n5 = object.fragment != null ? object.fragment.mContainerId : 0;
            int n6 = n4;
            if (n5 != 0) {
                n6 = n4;
                if (n5 != n4) {
                    for (n4 = n; n4 < n2; ++n4) {
                        object = arrayList.get(n4);
                        int n7 = object.mOps.size();
                        for (n6 = 0; n6 < n7; ++n6) {
                            Op op = object.mOps.get(n6);
                            int n8 = op.fragment != null ? op.fragment.mContainerId : 0;
                            if (n8 != n5) continue;
                            return true;
                        }
                    }
                    n6 = n5;
                }
            }
            n4 = n6;
        }
        return false;
    }

    @Override
    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @Override
    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }

    boolean isPostponed() {
        for (int i = 0; i < this.mOps.size(); ++i) {
            if (!BackStackRecord.isFragmentPostponed(this.mOps.get(i))) continue;
            return true;
        }
        return false;
    }

    @Override
    public FragmentTransaction remove(Fragment fragment) {
        this.addOp(new Op(3, fragment));
        return this;
    }

    @Override
    public FragmentTransaction replace(int n, Fragment fragment) {
        return this.replace(n, fragment, null);
    }

    @Override
    public FragmentTransaction replace(int n, Fragment fragment, String string) {
        if (n == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        this.doAddOp(n, fragment, string, 2);
        return this;
    }

    @Override
    public FragmentTransaction runOnCommit(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("runnable cannot be null");
        }
        this.disallowAddToBackStack();
        if (this.mCommitRunnables == null) {
            this.mCommitRunnables = new ArrayList();
        }
        this.mCommitRunnables.add(runnable);
        return this;
    }

    public void runOnCommitRunnables() {
        if (this.mCommitRunnables != null) {
            int n = this.mCommitRunnables.size();
            for (int i = 0; i < n; ++i) {
                this.mCommitRunnables.get(i).run();
            }
            this.mCommitRunnables = null;
        }
    }

    @Override
    public FragmentTransaction setAllowOptimization(boolean bl) {
        return this.setReorderingAllowed(bl);
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(int n) {
        this.mBreadCrumbShortTitleRes = n;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(int n) {
        this.mBreadCrumbTitleRes = n;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n, int n2) {
        return this.setCustomAnimations(n, n2, 0, 0);
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n, int n2, int n3, int n4) {
        this.mEnterAnim = n;
        this.mExitAnim = n2;
        this.mPopEnterAnim = n3;
        this.mPopExitAnim = n4;
        return this;
    }

    void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        for (int i = 0; i < this.mOps.size(); ++i) {
            Op op = this.mOps.get(i);
            if (!BackStackRecord.isFragmentPostponed(op)) continue;
            op.fragment.setOnStartEnterTransitionListener(onStartEnterTransitionListener);
        }
    }

    @Override
    public FragmentTransaction setPrimaryNavigationFragment(Fragment fragment) {
        this.addOp(new Op(8, fragment));
        return this;
    }

    @Override
    public FragmentTransaction setReorderingAllowed(boolean bl) {
        this.mReorderingAllowed = bl;
        return this;
    }

    @Override
    public FragmentTransaction setTransition(int n) {
        this.mTransition = n;
        return this;
    }

    @Override
    public FragmentTransaction setTransitionStyle(int n) {
        this.mTransitionStyle = n;
        return this;
    }

    @Override
    public FragmentTransaction show(Fragment fragment) {
        this.addOp(new Op(5, fragment));
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mName != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mName);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    Fragment trackAddedFragmentsInPop(ArrayList<Fragment> var1_1, Fragment var2_2) {
        var3_3 = 0;
        while (var3_3 < this.mOps.size()) {
            block7 : {
                var5_5 = this.mOps.get(var3_3);
                var4_4 = var5_5.cmd;
                if (var4_4 == 1) break block7;
                if (var4_4 == 3) ** GOTO lbl-1000
                switch (var4_4) {
                    default: {
                        ** break;
                    }
                    case 9: {
                        var2_2 = var5_5.fragment;
                        ** break;
                    }
                    case 8: {
                        var2_2 = null;
                        ** break;
                    }
                    case 6: lbl-1000: // 2 sources:
                    {
                        var1_1.add(var5_5.fragment);
                        ** break;
                    }
                    case 7: 
                }
            }
            var1_1.remove(var5_5.fragment);
lbl22: // 5 sources:
            ++var3_3;
        }
        return var2_2;
    }

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        int popEnterAnim;
        int popExitAnim;

        Op() {
        }

        Op(int n, Fragment fragment) {
            this.cmd = n;
            this.fragment = fragment;
        }
    }

}
