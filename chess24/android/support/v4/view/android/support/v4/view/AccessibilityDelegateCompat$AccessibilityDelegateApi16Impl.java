/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package android.support.v4.view;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

@RequiresApi(value=16)
static class AccessibilityDelegateCompat.AccessibilityDelegateApi16Impl
extends AccessibilityDelegateCompat.AccessibilityDelegateBaseImpl {
    AccessibilityDelegateCompat.AccessibilityDelegateApi16Impl() {
    }

    @Override
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View.AccessibilityDelegate accessibilityDelegate, View view) {
        if ((accessibilityDelegate = accessibilityDelegate.getAccessibilityNodeProvider(view)) != null) {
            return new AccessibilityNodeProviderCompat((Object)accessibilityDelegate);
        }
        return null;
    }

    @Override
    public View.AccessibilityDelegate newAccessibilityDelegateBridge(final AccessibilityDelegateCompat accessibilityDelegateCompat) {
        return new View.AccessibilityDelegate(){

            public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                return accessibilityDelegateCompat.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }

            public AccessibilityNodeProvider getAccessibilityNodeProvider(View object) {
                if ((object = accessibilityDelegateCompat.getAccessibilityNodeProvider((View)object)) != null) {
                    return (AccessibilityNodeProvider)object.getProvider();
                }
                return null;
            }

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                accessibilityDelegateCompat.onInitializeAccessibilityEvent(view, accessibilityEvent);
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                accessibilityDelegateCompat.onInitializeAccessibilityNodeInfo(view, AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo));
            }

            public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                accessibilityDelegateCompat.onPopulateAccessibilityEvent(view, accessibilityEvent);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                return accessibilityDelegateCompat.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }

            public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
                return accessibilityDelegateCompat.performAccessibilityAction(view, n, bundle);
            }

            public void sendAccessibilityEvent(View view, int n) {
                accessibilityDelegateCompat.sendAccessibilityEvent(view, n);
            }

            public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                accessibilityDelegateCompat.sendAccessibilityEventUnchecked(view, accessibilityEvent);
            }
        };
    }

    @Override
    public boolean performAccessibilityAction(View.AccessibilityDelegate accessibilityDelegate, View view, int n, Bundle bundle) {
        return accessibilityDelegate.performAccessibilityAction(view, n, bundle);
    }

}
