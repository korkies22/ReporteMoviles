/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.crashlytics.android.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.beta.BuildProperties;
import com.crashlytics.android.beta.CheckForUpdatesRequest;
import com.crashlytics.android.beta.CheckForUpdatesResponse;
import com.crashlytics.android.beta.CheckForUpdatesResponseTransform;
import com.crashlytics.android.beta.UpdatesController;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class AbstractCheckForUpdatesController
implements UpdatesController {
    static final long LAST_UPDATE_CHECK_DEFAULT = 0L;
    static final String LAST_UPDATE_CHECK_KEY = "last_update_check";
    private static final long MILLIS_PER_SECOND = 1000L;
    private Beta beta;
    private BetaSettingsData betaSettings;
    private BuildProperties buildProps;
    private Context context;
    private CurrentTimeProvider currentTimeProvider;
    private final AtomicBoolean externallyReady;
    private HttpRequestFactory httpRequestFactory;
    private IdManager idManager;
    private final AtomicBoolean initialized = new AtomicBoolean();
    private long lastCheckTimeMillis = 0L;
    private PreferenceStore preferenceStore;

    public AbstractCheckForUpdatesController() {
        this(false);
    }

    public AbstractCheckForUpdatesController(boolean bl) {
        this.externallyReady = new AtomicBoolean(bl);
    }

    private void performUpdateCheck() {
        Fabric.getLogger().d("Beta", "Performing update check");
        String string = new ApiKey().getValue(this.context);
        String string2 = this.idManager.getDeviceIdentifiers().get((Object)IdManager.DeviceIdentifierType.FONT_TOKEN);
        new CheckForUpdatesRequest(this.beta, this.beta.getOverridenSpiEndpoint(), this.betaSettings.updateUrl, this.httpRequestFactory, new CheckForUpdatesResponseTransform()).invoke(string, string2, this.buildProps);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"CommitPrefEdits"})
    protected void checkForUpdates() {
        Object object = this.preferenceStore;
        synchronized (object) {
            if (this.preferenceStore.get().contains(LAST_UPDATE_CHECK_KEY)) {
                this.preferenceStore.save(this.preferenceStore.edit().remove(LAST_UPDATE_CHECK_KEY));
            }
        }
        long l = this.currentTimeProvider.getCurrentTimeMillis();
        long l2 = (long)this.betaSettings.updateSuspendDurationSeconds * 1000L;
        object = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Check for updates delay: ");
        stringBuilder.append(l2);
        object.d("Beta", stringBuilder.toString());
        object = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Check for updates last check time: ");
        stringBuilder.append(this.getLastCheckTimeMillis());
        object.d("Beta", stringBuilder.toString());
        l2 = this.getLastCheckTimeMillis() + l2;
        object = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Check for updates current time: ");
        stringBuilder.append(l);
        stringBuilder.append(", next check time: ");
        stringBuilder.append(l2);
        object.d("Beta", stringBuilder.toString());
        if (l < l2) {
            Fabric.getLogger().d("Beta", "Check for updates next check time was not passed");
            return;
        }
        try {
            this.performUpdateCheck();
            return;
        }
        finally {
            this.setLastCheckTimeMillis(l);
        }
    }

    long getLastCheckTimeMillis() {
        return this.lastCheckTimeMillis;
    }

    @Override
    public void initialize(Context context, Beta beta, IdManager idManager, BetaSettingsData betaSettingsData, BuildProperties buildProperties, PreferenceStore preferenceStore, CurrentTimeProvider currentTimeProvider, HttpRequestFactory httpRequestFactory) {
        this.context = context;
        this.beta = beta;
        this.idManager = idManager;
        this.betaSettings = betaSettingsData;
        this.buildProps = buildProperties;
        this.preferenceStore = preferenceStore;
        this.currentTimeProvider = currentTimeProvider;
        this.httpRequestFactory = httpRequestFactory;
        if (this.signalInitialized()) {
            this.checkForUpdates();
        }
    }

    void setLastCheckTimeMillis(long l) {
        this.lastCheckTimeMillis = l;
    }

    protected boolean signalExternallyReady() {
        this.externallyReady.set(true);
        return this.initialized.get();
    }

    boolean signalInitialized() {
        this.initialized.set(true);
        return this.externallyReady.get();
    }
}
