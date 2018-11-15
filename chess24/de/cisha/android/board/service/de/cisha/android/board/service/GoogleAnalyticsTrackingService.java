/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class GoogleAnalyticsTrackingService
implements ITrackingService,
IProfileDataService.IUserDataChangedListener {
    private static final String CASHED_EXCEPTION_DESCRIPTION_KEY = "CASHED_EXCEPTION";
    private static final String CASHED_EXCEPTION_SCREENNAME_KEY = "CASHED_EXCEPTION_SCREEN";
    private static final String ENABLE_TRACKING_PREFERENCES_KEY = "TRACK_GOOGLE_ANALYTICS";
    private static final String LOG_TAG = "GoogleAnalyticsTracker";
    private static final String TRACKING_PREFERENCES_KEY = "TRACKING_PREFS";
    private static GoogleAnalyticsTrackingService _instance;
    protected static final char[] hexArray;
    private Context _context;
    private String _currentScreenname = null;
    private ExceptionParser _exceptionParser;
    private IMembershipService _membershipService;
    private Thread.UncaughtExceptionHandler _systemExceptionHandler;
    private String _trackMemberShipLevel;
    private Tracker _tracker;

    static {
        hexArray = "0123456789abcdef".toCharArray();
    }

    private GoogleAnalyticsTrackingService(Context context, IMembershipService iMembershipService) {
        this._context = context;
        this._membershipService = iMembershipService;
        boolean bl = this.isTrackingEnabled();
        this._tracker = GoogleAnalytics.getInstance(context).newTracker("UA-49208411-2");
        ServiceProvider.getInstance().getProfileDataService().addUserDataChangedListener(this);
        GoogleAnalytics.getInstance(context).setAppOptOut(bl ^ true);
        this.trackCachedException();
        this.setupExceptionHandling(context);
    }

    public static String bytesToHex(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 255;
            int n2 = i * 2;
            arrc[n2] = hexArray[n >>> 4];
            arrc[n2 + 1] = hexArray[n & 15];
        }
        return new String(arrc);
    }

    private void cacheException(String string, Throwable throwable) {
        string = this._exceptionParser.getDescription(string, throwable);
        throwable = this._context.getSharedPreferences(TRACKING_PREFERENCES_KEY, 0).edit();
        throwable.putString(CASHED_EXCEPTION_DESCRIPTION_KEY, string);
        if (this._currentScreenname != null) {
            throwable.putString(CASHED_EXCEPTION_SCREENNAME_KEY, this._currentScreenname);
        }
        throwable.commit();
    }

    public static GoogleAnalyticsTrackingService getInstance(Context object, IMembershipService iMembershipService) {
        synchronized (GoogleAnalyticsTrackingService.class) {
            if (_instance == null) {
                _instance = new GoogleAnalyticsTrackingService((Context)object, iMembershipService);
            }
            object = _instance;
            return object;
        }
    }

    private void send(Map<String, String> map) {
        if (this._tracker != null) {
            this._tracker.send(map);
        }
    }

    private void setUserTrackingOn(boolean bl) {
        if (this._context != null) {
            GoogleAnalytics.getInstance(this._context).setAppOptOut(bl ^ true);
            SharedPreferences.Editor editor = this._context.getSharedPreferences(TRACKING_PREFERENCES_KEY, 0).edit();
            editor.putBoolean(ENABLE_TRACKING_PREFERENCES_KEY, bl);
            editor.commit();
        }
    }

    private void setupExceptionHandling(Context context) {
        this._systemExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("de.cisha");
        this._exceptionParser = new StandardExceptionParser(context, linkedList);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                GoogleAnalyticsTrackingService.this.cacheException(thread.getName(), throwable);
                if (GoogleAnalyticsTrackingService.this._systemExceptionHandler != null) {
                    GoogleAnalyticsTrackingService.this._systemExceptionHandler.uncaughtException(thread, throwable);
                }
            }
        });
    }

    private void trackCachedException() {
        SharedPreferences sharedPreferences = this._context.getSharedPreferences(TRACKING_PREFERENCES_KEY, 0);
        String string = sharedPreferences.getString(CASHED_EXCEPTION_DESCRIPTION_KEY, null);
        if (string != null) {
            String string2 = sharedPreferences.getString(CASHED_EXCEPTION_SCREENNAME_KEY, null);
            if (string2 != null) {
                this._tracker.setScreenName(string2);
            }
            this.send(new HitBuilders.ExceptionBuilder().setDescription(string).setFatal(true).build());
            this._tracker.setScreenName(this._currentScreenname);
        }
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.remove(CASHED_EXCEPTION_DESCRIPTION_KEY);
        sharedPreferences.remove(CASHED_EXCEPTION_SCREENNAME_KEY);
        sharedPreferences.commit();
    }

    private void trackException(String string, Throwable throwable, boolean bl) {
        if (this._exceptionParser != null) {
            this.send(new HitBuilders.ExceptionBuilder().setDescription(this._exceptionParser.getDescription(string, throwable)).setFatal(bl).build());
            return;
        }
        Logger.getInstance().error("Exceptionparser was null - exception not tracked");
    }

    @Override
    public void disableUserTracking() {
        this.trackEvent(ITrackingService.TrackingCategory.USER, "Toggled behaviour tracking", "off");
        this.setUserTrackingOn(false);
    }

    @Override
    public void enableUserTracking() {
        this.setUserTrackingOn(true);
        this.trackEvent(ITrackingService.TrackingCategory.USER, "Toggled behaviour tracking", "on");
    }

    @Override
    public boolean isTrackingEnabled() {
        return this._context.getSharedPreferences(TRACKING_PREFERENCES_KEY, 0).getBoolean(ENABLE_TRACKING_PREFERENCES_KEY, true);
    }

    @Override
    public void setDebugMode(boolean bl) {
        GoogleAnalytics.getInstance(this._context).setDryRun(bl);
    }

    @Override
    public void trackEvent(ITrackingService.TrackingCategory object, String string, String string2) {
        if (object != null && string != null) {
            object = new HitBuilders.EventBuilder(object.getName(), string);
            if (string2 != null) {
                object.setLabel(string2);
            }
            this.send(object.build());
        }
    }

    @Override
    public void trackException(String string, Throwable throwable) {
        this.trackException(string, throwable, false);
    }

    @Override
    public void trackFatalException(String string, Throwable throwable) {
        this.trackException(string, throwable, true);
    }

    @Override
    public void trackScreenOpen(AbstractContentFragment abstractContentFragment) {
        if (abstractContentFragment != null) {
            this.trackScreenOpen(abstractContentFragment.getTrackingName());
        }
    }

    @Override
    public void trackScreenOpen(String string) {
        if (this._tracker != null && string != null && !"".equals(string.trim()) && !string.equals(this._currentScreenname)) {
            this._tracker.setScreenName(string);
            HitBuilders.AppViewBuilder appViewBuilder = new HitBuilders.AppViewBuilder();
            if (this._trackMemberShipLevel != null) {
                appViewBuilder.setCustomDimension(2, this._trackMemberShipLevel);
            }
            this.send(appViewBuilder.build());
        }
        this._currentScreenname = string;
    }

    @Override
    public void trackUserLogin(CishaUUID object, String string) {
        if (this._tracker != null && object != null && (object = object.getUuid()) != null) {
            try {
                String string2 = GoogleAnalyticsTrackingService.bytesToHex(MessageDigest.getInstance("MD5").digest(object.getBytes("UTF-8")));
                this._tracker.set("&uid", string2);
            }
            catch (Exception exception) {
                Logger.getInstance().debug(GoogleAnalyticsTrackingService.class.getName(), NoSuchAlgorithmException.class.getName(), exception);
                Tracker tracker = this._tracker;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("3");
                stringBuilder.append((String)object);
                stringBuilder.append("65");
                tracker.set("&uid", stringBuilder.toString());
            }
            object = new HitBuilders.EventBuilder("UX", "User Sign In");
            if (string != null) {
                object.setLabel(string);
            }
            this.send(object.build());
        }
    }

    @Override
    public void userDataChanged(User object) {
        if (object != null) {
            object = this._membershipService.getMembershipLevel();
            object = object == null ? "Logged out" : object.getTrackingName();
            this._trackMemberShipLevel = object;
        }
    }

}
