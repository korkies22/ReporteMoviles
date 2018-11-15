/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.beta;

import com.crashlytics.android.beta.AbstractCheckForUpdatesController;

class ImmediateCheckForUpdatesController
extends AbstractCheckForUpdatesController {
    public ImmediateCheckForUpdatesController() {
        super(true);
    }

    @Override
    public boolean isActivityLifecycleTriggered() {
        return false;
    }
}
