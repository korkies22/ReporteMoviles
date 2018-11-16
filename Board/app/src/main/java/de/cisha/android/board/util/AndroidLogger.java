// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

import android.util.Log;
import de.cisha.chess.util.Logger;

public class AndroidLogger extends Logger
{
    @Override
    public void debug(final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(s);
        Log.d("Logger: ", sb.toString());
    }
    
    @Override
    public void debug(String string, final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(string);
        string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(s);
        Log.d(string, sb2.toString());
    }
    
    @Override
    public void debug(String string, final String s, final Throwable t) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(string);
        string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(s);
        Log.d(string, sb2.toString(), t);
    }
    
    @Override
    public void error(final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(s);
        Log.e("Logger: error:", sb.toString());
    }
    
    @Override
    public void error(String string, final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(string);
        string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(s);
        Log.e(string, sb2.toString());
    }
    
    @Override
    public void error(String string, final String s, final Throwable t) {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(string);
        string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(s);
        Log.e(string, sb2.toString(), t);
    }
}
