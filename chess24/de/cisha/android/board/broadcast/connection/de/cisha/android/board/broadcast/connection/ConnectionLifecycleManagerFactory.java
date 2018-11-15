/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.connection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import de.cisha.android.board.broadcast.connection.ActivityLifecycle;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.FragmentConnectionLifecycleManager;

public class ConnectionLifecycleManagerFactory {
    public static ConnectionLifecycleManager createManagerForActivity(FragmentActivity fragmentActivity, ActivityLifecycle activityLifecycle, int n) {
        FragmentConnectionLifecycleManager fragmentConnectionLifecycleManager = new FragmentConnectionLifecycleManager();
        fragmentConnectionLifecycleManager.setConnectionLifecycle(activityLifecycle);
        fragmentConnectionLifecycleManager.setReconnectDelay(n);
        fragmentActivity.getSupportFragmentManager().beginTransaction().add(fragmentConnectionLifecycleManager, "connectionlifecyclemanageractivity").commit();
        return fragmentConnectionLifecycleManager;
    }

    public static ConnectionLifecycleManager createManagerForFragment(Fragment fragment, ActivityLifecycle activityLifecycle, int n) {
        FragmentConnectionLifecycleManager fragmentConnectionLifecycleManager = new FragmentConnectionLifecycleManager();
        fragmentConnectionLifecycleManager.setConnectionLifecycle(activityLifecycle);
        fragmentConnectionLifecycleManager.setReconnectDelay(n);
        fragment.getChildFragmentManager().beginTransaction().add(fragmentConnectionLifecycleManager, "connectionlifecyclemanagerfragment").commit();
        return fragmentConnectionLifecycleManager;
    }
}
