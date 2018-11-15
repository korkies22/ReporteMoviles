/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

public static abstract class FragmentManager.FragmentLifecycleCallbacks {
    public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
    }

    public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
    }

    public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
    }

    public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
    }

    public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
    }

    public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
    }

    public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
    }

    public void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
    }

    public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
    }

    public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
    }

    public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
    }

    public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
    }

    public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
    }

    public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
    }
}
