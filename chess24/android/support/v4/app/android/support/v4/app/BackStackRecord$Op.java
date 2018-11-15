/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;

static final class BackStackRecord.Op {
    int cmd;
    int enterAnim;
    int exitAnim;
    Fragment fragment;
    int popEnterAnim;
    int popExitAnim;

    BackStackRecord.Op() {
    }

    BackStackRecord.Op(int n, Fragment fragment) {
        this.cmd = n;
        this.fragment = fragment;
    }
}
