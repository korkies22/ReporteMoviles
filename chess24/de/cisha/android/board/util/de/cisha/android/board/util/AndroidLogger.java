/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package de.cisha.android.board.util;

import android.util.Log;
import de.cisha.chess.util.Logger;

public class AndroidLogger
extends Logger {
    @Override
    public void debug(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        Log.d((String)"Logger: ", (String)stringBuilder.toString());
    }

    @Override
    public void debug(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string2);
        Log.d((String)string, (String)stringBuilder.toString());
    }

    @Override
    public void debug(String string, String string2, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string2);
        Log.d((String)string, (String)stringBuilder.toString(), (Throwable)throwable);
    }

    @Override
    public void error(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        Log.e((String)"Logger: error:", (String)stringBuilder.toString());
    }

    @Override
    public void error(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string2);
        Log.e((String)string, (String)stringBuilder.toString());
    }

    @Override
    public void error(String string, String string2, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string2);
        Log.e((String)string, (String)stringBuilder.toString(), (Throwable)throwable);
    }
}
