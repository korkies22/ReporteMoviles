/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.zzsw;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ExceptionReporter
implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    private final Thread.UncaughtExceptionHandler zzaaB;
    private final Tracker zzaaC;
    private ExceptionParser zzaaD;
    private GoogleAnalytics zzaaE;

    public ExceptionReporter(Tracker object, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (object == null) {
            throw new NullPointerException("tracker cannot be null");
        }
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.zzaaB = uncaughtExceptionHandler;
        this.zzaaC = object;
        this.zzaaD = new StandardExceptionParser(context, new ArrayList<String>());
        this.mContext = context.getApplicationContext();
        object = uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName();
        object = String.valueOf(object);
        object = object.length() != 0 ? "ExceptionReporter created, original handler is ".concat((String)object) : new String("ExceptionReporter created, original handler is ");
        zzsw.v((String)object);
    }

    public ExceptionParser getExceptionParser() {
        return this.zzaaD;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.zzaaD = exceptionParser;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String string;
        Object object = "UncaughtException";
        if (this.zzaaD != null) {
            object = thread != null ? thread.getName() : null;
            object = this.zzaaD.getDescription((String)object, throwable);
        }
        string = (string = String.valueOf(object)).length() != 0 ? "Reporting uncaught exception: ".concat(string) : new String("Reporting uncaught exception: ");
        zzsw.v(string);
        this.zzaaC.send(new HitBuilders.ExceptionBuilder().setDescription((String)object).setFatal(true).build());
        object = this.zzlT();
        object.dispatchLocalHits();
        object.zzlY();
        if (this.zzaaB != null) {
            zzsw.v("Passing exception to the original handler");
            this.zzaaB.uncaughtException(thread, throwable);
        }
    }

    GoogleAnalytics zzlT() {
        if (this.zzaaE == null) {
            this.zzaaE = GoogleAnalytics.getInstance(this.mContext);
        }
        return this.zzaaE;
    }

    Thread.UncaughtExceptionHandler zzlU() {
        return this.zzaaB;
    }
}
