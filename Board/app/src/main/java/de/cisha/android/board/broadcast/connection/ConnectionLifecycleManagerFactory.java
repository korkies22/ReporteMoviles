// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.connection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ConnectionLifecycleManagerFactory
{
    public static ConnectionLifecycleManager createManagerForActivity(final FragmentActivity fragmentActivity, final ActivityLifecycle connectionLifecycle, final int reconnectDelay) {
        final FragmentConnectionLifecycleManager fragmentConnectionLifecycleManager = new FragmentConnectionLifecycleManager();
        fragmentConnectionLifecycleManager.setConnectionLifecycle(connectionLifecycle);
        fragmentConnectionLifecycleManager.setReconnectDelay(reconnectDelay);
        fragmentActivity.getSupportFragmentManager().beginTransaction().add(fragmentConnectionLifecycleManager, "connectionlifecyclemanageractivity").commit();
        return fragmentConnectionLifecycleManager;
    }
    
    public static ConnectionLifecycleManager createManagerForFragment(final Fragment fragment, final ActivityLifecycle connectionLifecycle, final int reconnectDelay) {
        final FragmentConnectionLifecycleManager fragmentConnectionLifecycleManager = new FragmentConnectionLifecycleManager();
        fragmentConnectionLifecycleManager.setConnectionLifecycle(connectionLifecycle);
        fragmentConnectionLifecycleManager.setReconnectDelay(reconnectDelay);
        fragment.getChildFragmentManager().beginTransaction().add(fragmentConnectionLifecycleManager, "connectionlifecyclemanagerfragment").commit();
        return fragmentConnectionLifecycleManager;
    }
}
