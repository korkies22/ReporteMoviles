/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.TabActivity
 *  android.content.Context
 *  android.os.Bundle
 */
package com.j256.ormlite.android.apptools;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public abstract class OrmLiteBaseTabActivity<H extends OrmLiteSqliteOpenHelper>
extends TabActivity {
    private volatile boolean created = false;
    private volatile boolean destroyed = false;
    private volatile H helper;

    public ConnectionSource getConnectionSource() {
        return this.getHelper().getConnectionSource();
    }

    public H getHelper() {
        if (this.helper == null) {
            if (!this.created) {
                throw new IllegalStateException("A call has not been made to onCreate() yet so the helper is null");
            }
            if (this.destroyed) {
                throw new IllegalStateException("A call to onDestroy has already been made and the helper cannot be used after that point");
            }
            throw new IllegalStateException("Helper is null for some unknown reason");
        }
        return this.helper;
    }

    protected H getHelperInternal(Context context) {
        return (H)((Object)OpenHelperManager.getHelper(context));
    }

    protected void onCreate(Bundle bundle) {
        if (this.helper == null) {
            this.helper = this.getHelperInternal((Context)this);
            this.created = true;
        }
        super.onCreate(bundle);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.releaseHelper(this.helper);
        this.destroyed = true;
    }

    protected void releaseHelper(H h) {
        OpenHelperManager.releaseHelper();
        this.helper = null;
    }
}
