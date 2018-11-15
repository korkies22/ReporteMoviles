/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.support.annotation.WorkerThread;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class zza {
    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @WorkerThread
    public void zzu(String string) {
        String string2;
        StringBuilder stringBuilder;
        CharSequence charSequence;
        void var3_5;
        HttpURLConnection httpURLConnection;
        block7 : {
            httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
            int n = httpURLConnection.getResponseCode();
            if (n >= 200 && n < 300) break block7;
            charSequence = new StringBuilder(65 + String.valueOf(string).length());
            charSequence.append("Received non-success response code ");
            charSequence.append(n);
            charSequence.append(" from pinging URL: ");
            charSequence.append(string);
            Log.w((String)"HttpUrlPinger", (String)charSequence.toString());
            {
                catch (Throwable throwable) {
                    httpURLConnection.disconnect();
                    throw throwable;
                }
            }
        }
        try {
            httpURLConnection.disconnect();
            return;
        }
        catch (IOException | RuntimeException exception) {
            charSequence = String.valueOf(exception.getMessage());
            stringBuilder = new StringBuilder(27 + String.valueOf(string).length() + String.valueOf(charSequence).length());
            string2 = "Error while pinging URL: ";
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            charSequence = String.valueOf(indexOutOfBoundsException.getMessage());
            stringBuilder = new StringBuilder(32 + String.valueOf(string).length() + String.valueOf(charSequence).length());
            string2 = "Error while parsing ping URL: ";
        }
        stringBuilder.append(string2);
        stringBuilder.append(string);
        stringBuilder.append(". ");
        stringBuilder.append((String)charSequence);
        Log.w((String)"HttpUrlPinger", (String)stringBuilder.toString(), (Throwable)var3_5);
    }
}
