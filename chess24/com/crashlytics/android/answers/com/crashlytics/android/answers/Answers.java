/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.crashlytics.android.answers.AddToCartEvent;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.InviteEvent;
import com.crashlytics.android.answers.LevelEndEvent;
import com.crashlytics.android.answers.LevelStartEvent;
import com.crashlytics.android.answers.LoginEvent;
import com.crashlytics.android.answers.PredefinedEvent;
import com.crashlytics.android.answers.PurchaseEvent;
import com.crashlytics.android.answers.RatingEvent;
import com.crashlytics.android.answers.SearchEvent;
import com.crashlytics.android.answers.SessionAnalyticsManager;
import com.crashlytics.android.answers.ShareEvent;
import com.crashlytics.android.answers.SignUpEvent;
import com.crashlytics.android.answers.StartCheckoutEvent;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash;
import io.fabric.sdk.android.services.common.FirebaseInfo;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;

public class Answers
extends Kit<Boolean> {
    static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    public static final String TAG = "Answers";
    SessionAnalyticsManager analyticsManager;
    boolean firebaseEnabled = false;

    public static Answers getInstance() {
        return Fabric.getKit(Answers.class);
    }

    private void logFirebaseModeEnabledWarning(String string) {
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Method ");
        stringBuilder.append(string);
        stringBuilder.append(" is not supported when using Crashlytics through Firebase.");
        logger.w(TAG, stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Boolean doInBackground() {
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                Fabric.getLogger().e(TAG, "Failed to retrieve settings");
                return false;
            }
            if (settingsData.featuresData.collectAnalytics) {
                Fabric.getLogger().d(TAG, "Analytics collection enabled");
                this.analyticsManager.setAnalyticsSettingsData(settingsData.analyticsSettingsData, this.getOverridenSpiEndpoint());
                return true;
            }
            Fabric.getLogger().d(TAG, "Analytics collection disabled");
            this.analyticsManager.disable();
        }
        catch (Exception exception) {
            Fabric.getLogger().e(TAG, "Error dealing with settings", exception);
            return false;
        }
        return false;
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android:answers";
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(this.getContext(), CRASHLYTICS_API_ENDPOINT);
    }

    @Override
    public String getVersion() {
        return "1.4.1.19";
    }

    public void logAddToCart(AddToCartEvent addToCartEvent) {
        if (addToCartEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logAddToCart");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(addToCartEvent);
        }
    }

    public void logContentView(ContentViewEvent contentViewEvent) {
        if (contentViewEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logContentView");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(contentViewEvent);
        }
    }

    public void logCustom(CustomEvent customEvent) {
        if (customEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logCustom");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onCustom(customEvent);
        }
    }

    public void logInvite(InviteEvent inviteEvent) {
        if (inviteEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logInvite");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(inviteEvent);
        }
    }

    public void logLevelEnd(LevelEndEvent levelEndEvent) {
        if (levelEndEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logLevelEnd");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(levelEndEvent);
        }
    }

    public void logLevelStart(LevelStartEvent levelStartEvent) {
        if (levelStartEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logLevelStart");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(levelStartEvent);
        }
    }

    public void logLogin(LoginEvent loginEvent) {
        if (loginEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logLogin");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(loginEvent);
        }
    }

    public void logPurchase(PurchaseEvent purchaseEvent) {
        if (purchaseEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logPurchase");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(purchaseEvent);
        }
    }

    public void logRating(RatingEvent ratingEvent) {
        if (ratingEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logRating");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(ratingEvent);
        }
    }

    public void logSearch(SearchEvent searchEvent) {
        if (searchEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logSearch");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(searchEvent);
        }
    }

    public void logShare(ShareEvent shareEvent) {
        if (shareEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logShare");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(shareEvent);
        }
    }

    public void logSignUp(SignUpEvent signUpEvent) {
        if (signUpEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logSignUp");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(signUpEvent);
        }
    }

    public void logStartCheckout(StartCheckoutEvent startCheckoutEvent) {
        if (startCheckoutEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.firebaseEnabled) {
            this.logFirebaseModeEnabledWarning("logStartCheckout");
            return;
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onPredefined(startCheckoutEvent);
        }
    }

    public void onException(Crash.FatalException fatalException) {
        if (this.analyticsManager != null) {
            this.analyticsManager.onCrash(fatalException.getSessionId(), fatalException.getExceptionName());
        }
    }

    public void onException(Crash.LoggedException loggedException) {
        if (this.analyticsManager != null) {
            this.analyticsManager.onError(loggedException.getSessionId());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"NewApi"})
    @Override
    protected boolean onPreExecute() {
        try {
            Context context = this.getContext();
            PackageManager packageManager = context.getPackageManager();
            String string = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(string, 0);
            String string2 = Integer.toString(packageInfo.versionCode);
            String string3 = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
            long l = Build.VERSION.SDK_INT >= 9 ? packageInfo.firstInstallTime : new File(packageManager.getApplicationInfo((String)string, (int)0).sourceDir).lastModified();
            this.analyticsManager = SessionAnalyticsManager.build(this, context, this.getIdManager(), string2, string3, l);
            this.analyticsManager.enable();
            this.firebaseEnabled = new FirebaseInfo().isFirebaseCrashlyticsEnabled(context);
            return true;
        }
        catch (Exception exception) {
            Fabric.getLogger().e(TAG, "Error retrieving app properties", exception);
            return false;
        }
    }
}
