/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;

static class TaskStackBuilder.TaskStackBuilderBaseImpl {
    TaskStackBuilder.TaskStackBuilderBaseImpl() {
    }

    public PendingIntent getPendingIntent(Context context, Intent[] arrintent, int n, int n2, Bundle bundle) {
        arrintent[0] = new Intent(arrintent[0]).addFlags(268484608);
        return PendingIntent.getActivities((Context)context, (int)n, (Intent[])arrintent, (int)n2);
    }
}
