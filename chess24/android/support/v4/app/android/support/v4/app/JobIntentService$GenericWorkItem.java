/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package android.support.v4.app;

import android.content.Intent;
import android.support.v4.app.JobIntentService;

static interface JobIntentService.GenericWorkItem {
    public void complete();

    public Intent getIntent();
}
