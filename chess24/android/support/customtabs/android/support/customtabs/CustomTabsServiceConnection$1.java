/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package android.support.customtabs;

import android.content.ComponentName;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.ICustomTabsService;

class CustomTabsServiceConnection
extends CustomTabsClient {
    CustomTabsServiceConnection(ICustomTabsService iCustomTabsService, ComponentName componentName) {
        super(iCustomTabsService, componentName);
    }
}
