// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import com.google.android.gms.internal.zztg;
import de.cisha.android.board.user.User;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.chess.util.Logger;
import android.content.SharedPreferences;
import com.google.android.gms.analytics.HitBuilders;
import java.util.Collection;
import com.google.android.gms.analytics.StandardExceptionParser;
import java.util.LinkedList;
import java.util.Map;
import android.content.SharedPreferences.Editor;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ExceptionParser;
import android.content.Context;

public class GoogleAnalyticsTrackingService implements ITrackingService, IUserDataChangedListener
{
    private static final String CASHED_EXCEPTION_DESCRIPTION_KEY = "CASHED_EXCEPTION";
    private static final String CASHED_EXCEPTION_SCREENNAME_KEY = "CASHED_EXCEPTION_SCREEN";
    private static final String ENABLE_TRACKING_PREFERENCES_KEY = "TRACK_GOOGLE_ANALYTICS";
    private static final String LOG_TAG = "GoogleAnalyticsTracker";
    private static final String TRACKING_PREFERENCES_KEY = "TRACKING_PREFS";
    private static GoogleAnalyticsTrackingService _instance;
    protected static final char[] hexArray;
    private Context _context;
    private String _currentScreenname;
    private ExceptionParser _exceptionParser;
    private IMembershipService _membershipService;
    private Thread.UncaughtExceptionHandler _systemExceptionHandler;
    private String _trackMemberShipLevel;
    private Tracker _tracker;
    
    static {
        hexArray = "0123456789abcdef".toCharArray();
    }
    
    private GoogleAnalyticsTrackingService(final Context context, final IMembershipService membershipService) {
        this._currentScreenname = null;
        this._context = context;
        this._membershipService = membershipService;
        final boolean trackingEnabled = this.isTrackingEnabled();
        this._tracker = GoogleAnalytics.getInstance(context).newTracker("UA-49208411-2");
        ServiceProvider.getInstance().getProfileDataService().addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
        GoogleAnalytics.getInstance(context).setAppOptOut(trackingEnabled ^ true);
        this.trackCachedException();
        this.setupExceptionHandling(context);
    }
    
    public static String bytesToHex(final byte[] array) {
        int i = 0;
        final char[] array2 = new char[array.length * 2];
        while (i < array.length) {
            final int n = array[i] & 0xFF;
            final int n2 = i * 2;
            array2[n2] = GoogleAnalyticsTrackingService.hexArray[n >>> 4];
            array2[n2 + 1] = GoogleAnalyticsTrackingService.hexArray[n & 0xF];
            ++i;
        }
        return new String(array2);
    }
    
    private void cacheException(String description, final Throwable t) {
        description = this._exceptionParser.getDescription(description, t);
        final SharedPreferences.Editor edit = this._context.getSharedPreferences("TRACKING_PREFS", 0).edit();
        edit.putString("CASHED_EXCEPTION", description);
        if (this._currentScreenname != null) {
            edit.putString("CASHED_EXCEPTION_SCREEN", this._currentScreenname);
        }
        edit.commit();
    }
    
    public static GoogleAnalyticsTrackingService getInstance(final Context context, final IMembershipService membershipService) {
        synchronized (GoogleAnalyticsTrackingService.class) {
            if (GoogleAnalyticsTrackingService._instance == null) {
                GoogleAnalyticsTrackingService._instance = new GoogleAnalyticsTrackingService(context, membershipService);
            }
            return GoogleAnalyticsTrackingService._instance;
        }
    }
    
    private void send(final Map<String, String> map) {
        if (this._tracker != null) {
            this._tracker.send(map);
        }
    }
    
    private void setUserTrackingOn(final boolean b) {
        if (this._context != null) {
            GoogleAnalytics.getInstance(this._context).setAppOptOut(b ^ true);
            final SharedPreferences.Editor edit = this._context.getSharedPreferences("TRACKING_PREFS", 0).edit();
            edit.putBoolean("TRACK_GOOGLE_ANALYTICS", b);
            edit.commit();
        }
    }
    
    private void setupExceptionHandling(final Context context) {
        this._systemExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        final LinkedList<String> list = new LinkedList<String>();
        list.add("de.cisha");
        this._exceptionParser = new StandardExceptionParser(context, list);
        Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread thread, final Throwable t) {
                GoogleAnalyticsTrackingService.this.cacheException(thread.getName(), t);
                if (GoogleAnalyticsTrackingService.this._systemExceptionHandler != null) {
                    GoogleAnalyticsTrackingService.this._systemExceptionHandler.uncaughtException(thread, t);
                }
            }
        });
    }
    
    private void trackCachedException() {
        final SharedPreferences sharedPreferences = this._context.getSharedPreferences("TRACKING_PREFS", 0);
        final String string = sharedPreferences.getString("CASHED_EXCEPTION", (String)null);
        if (string != null) {
            final String string2 = sharedPreferences.getString("CASHED_EXCEPTION_SCREEN", (String)null);
            if (string2 != null) {
                this._tracker.setScreenName(string2);
            }
            this.send(new HitBuilders.ExceptionBuilder().setDescription(string).setFatal(true).build());
            this._tracker.setScreenName(this._currentScreenname);
        }
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove("CASHED_EXCEPTION");
        edit.remove("CASHED_EXCEPTION_SCREEN");
        edit.commit();
    }
    
    private void trackException(final String s, final Throwable t, final boolean fatal) {
        if (this._exceptionParser != null) {
            this.send(new HitBuilders.ExceptionBuilder().setDescription(this._exceptionParser.getDescription(s, t)).setFatal(fatal).build());
            return;
        }
        Logger.getInstance().error("Exceptionparser was null - exception not tracked");
    }
    
    @Override
    public void disableUserTracking() {
        this.trackEvent(TrackingCategory.USER, "Toggled behaviour tracking", "off");
        this.setUserTrackingOn(false);
    }
    
    @Override
    public void enableUserTracking() {
        this.setUserTrackingOn(true);
        this.trackEvent(TrackingCategory.USER, "Toggled behaviour tracking", "on");
    }
    
    @Override
    public boolean isTrackingEnabled() {
        return this._context.getSharedPreferences("TRACKING_PREFS", 0).getBoolean("TRACK_GOOGLE_ANALYTICS", true);
    }
    
    @Override
    public void setDebugMode(final boolean dryRun) {
        GoogleAnalytics.getInstance(this._context).setDryRun(dryRun);
    }
    
    @Override
    public void trackEvent(final TrackingCategory trackingCategory, final String s, final String label) {
        if (trackingCategory != null && s != null) {
            final HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder(trackingCategory.getName(), s);
            if (label != null) {
                eventBuilder.setLabel(label);
            }
            this.send(eventBuilder.build());
        }
    }
    
    @Override
    public void trackException(final String s, final Throwable t) {
        this.trackException(s, t, false);
    }
    
    @Override
    public void trackFatalException(final String s, final Throwable t) {
        this.trackException(s, t, true);
    }
    
    @Override
    public void trackScreenOpen(final AbstractContentFragment abstractContentFragment) {
        if (abstractContentFragment != null) {
            this.trackScreenOpen(abstractContentFragment.getTrackingName());
        }
    }
    
    @Override
    public void trackScreenOpen(final String s) {
        if (this._tracker != null && s != null && !"".equals(s.trim()) && !s.equals(this._currentScreenname)) {
            this._tracker.setScreenName(s);
            final HitBuilders.AppViewBuilder appViewBuilder = new HitBuilders.AppViewBuilder();
            if (this._trackMemberShipLevel != null) {
                appViewBuilder.setCustomDimension(2, this._trackMemberShipLevel);
            }
            this.send(appViewBuilder.build());
        }
        this._currentScreenname = s;
    }
    
    @Override
    public void trackUserLogin(CishaUUID uuid, final String label) {
        if (this._tracker != null && uuid != null) {
            uuid = (CishaUUID)uuid.getUuid();
            if (uuid != null) {
                try {
                    this._tracker.set("&uid", bytesToHex(MessageDigest.getInstance("MD5").digest(((String)uuid).getBytes("UTF-8"))));
                }
                catch (Exception ex) {
                    Logger.getInstance().debug(GoogleAnalyticsTrackingService.class.getName(), NoSuchAlgorithmException.class.getName(), ex);
                    final Tracker tracker = this._tracker;
                    final StringBuilder sb = new StringBuilder();
                    sb.append("3");
                    sb.append((String)uuid);
                    sb.append("65");
                    tracker.set("&uid", sb.toString());
                }
                final HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder("UX", "User Sign In");
                if (label != null) {
                    eventBuilder.setLabel(label);
                }
                this.send(eventBuilder.build());
            }
        }
    }
    
    @Override
    public void userDataChanged(final User user) {
        if (user != null) {
            final IMembershipService.MembershipLevel membershipLevel = this._membershipService.getMembershipLevel();
            String trackingName;
            if (membershipLevel == null) {
                trackingName = "Logged out";
            }
            else {
                trackingName = membershipLevel.getTrackingName();
            }
            this._trackMemberShipLevel = trackingName;
        }
    }
}
