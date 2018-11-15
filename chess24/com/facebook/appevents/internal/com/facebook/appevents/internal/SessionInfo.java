/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.preference.PreferenceManager
 */
package com.facebook.appevents.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.SourceApplicationInfo;
import java.util.UUID;

class SessionInfo {
    private static final String INTERRUPTION_COUNT_KEY = "com.facebook.appevents.SessionInfo.interruptionCount";
    private static final String LAST_SESSION_INFO_END_KEY = "com.facebook.appevents.SessionInfo.sessionEndTime";
    private static final String LAST_SESSION_INFO_START_KEY = "com.facebook.appevents.SessionInfo.sessionStartTime";
    private static final String SESSION_ID_KEY = "com.facebook.appevents.SessionInfo.sessionId";
    private Long diskRestoreTime;
    private int interruptionCount;
    private UUID sessionId;
    private Long sessionLastEventTime;
    private Long sessionStartTime;
    private SourceApplicationInfo sourceApplicationInfo;

    public SessionInfo(Long l, Long l2) {
        this(l, l2, UUID.randomUUID());
    }

    public SessionInfo(Long l, Long l2, UUID uUID) {
        this.sessionStartTime = l;
        this.sessionLastEventTime = l2;
        this.sessionId = uUID;
    }

    public static void clearSavedSessionFromDisk() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).edit();
        editor.remove(LAST_SESSION_INFO_START_KEY);
        editor.remove(LAST_SESSION_INFO_END_KEY);
        editor.remove(INTERRUPTION_COUNT_KEY);
        editor.remove(SESSION_ID_KEY);
        editor.apply();
        SourceApplicationInfo.clearSavedSourceApplicationInfoFromDisk();
    }

    public static SessionInfo getStoredSessionInfo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext());
        long l = sharedPreferences.getLong(LAST_SESSION_INFO_START_KEY, 0L);
        long l2 = sharedPreferences.getLong(LAST_SESSION_INFO_END_KEY, 0L);
        String string = sharedPreferences.getString(SESSION_ID_KEY, null);
        if (l != 0L && l2 != 0L) {
            if (string == null) {
                return null;
            }
            SessionInfo sessionInfo = new SessionInfo(l, l2);
            sessionInfo.interruptionCount = sharedPreferences.getInt(INTERRUPTION_COUNT_KEY, 0);
            sessionInfo.sourceApplicationInfo = SourceApplicationInfo.getStoredSourceApplicatioInfo();
            sessionInfo.diskRestoreTime = System.currentTimeMillis();
            sessionInfo.sessionId = UUID.fromString(string);
            return sessionInfo;
        }
        return null;
    }

    public long getDiskRestoreTime() {
        if (this.diskRestoreTime == null) {
            return 0L;
        }
        return this.diskRestoreTime;
    }

    public int getInterruptionCount() {
        return this.interruptionCount;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public Long getSessionLastEventTime() {
        return this.sessionLastEventTime;
    }

    public long getSessionLength() {
        if (this.sessionStartTime != null && this.sessionLastEventTime != null) {
            return this.sessionLastEventTime - this.sessionStartTime;
        }
        return 0L;
    }

    public Long getSessionStartTime() {
        return this.sessionStartTime;
    }

    public SourceApplicationInfo getSourceApplicationInfo() {
        return this.sourceApplicationInfo;
    }

    public void incrementInterruptionCount() {
        ++this.interruptionCount;
    }

    public void setSessionLastEventTime(Long l) {
        this.sessionLastEventTime = l;
    }

    public void setSourceApplicationInfo(SourceApplicationInfo sourceApplicationInfo) {
        this.sourceApplicationInfo = sourceApplicationInfo;
    }

    public void writeSessionToDisk() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).edit();
        editor.putLong(LAST_SESSION_INFO_START_KEY, this.sessionStartTime.longValue());
        editor.putLong(LAST_SESSION_INFO_END_KEY, this.sessionLastEventTime.longValue());
        editor.putInt(INTERRUPTION_COUNT_KEY, this.interruptionCount);
        editor.putString(SESSION_ID_KEY, this.sessionId.toString());
        editor.apply();
        if (this.sourceApplicationInfo != null) {
            this.sourceApplicationInfo.writeSourceApplicationInfoToDisk();
        }
    }
}
