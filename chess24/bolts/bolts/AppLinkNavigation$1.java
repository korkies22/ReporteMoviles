/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package bolts;

import android.content.Context;
import bolts.AppLink;
import bolts.AppLinkNavigation;
import bolts.Continuation;
import bolts.Task;

static final class AppLinkNavigation
implements Continuation<AppLink, AppLinkNavigation.NavigationResult> {
    final /* synthetic */ Context val$context;

    AppLinkNavigation(Context context) {
        this.val$context = context;
    }

    @Override
    public AppLinkNavigation.NavigationResult then(Task<AppLink> task) throws Exception {
        return bolts.AppLinkNavigation.navigate(this.val$context, task.getResult());
    }
}
