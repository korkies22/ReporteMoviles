// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.io.PrintStream;

public class SystemOutLogger extends Logger
{
    @Override
    public void debug(final String s) {
        final PrintStream out = System.out;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(s);
        out.println(sb.toString());
    }
    
    @Override
    public void debug(final String s, final String s2) {
        final PrintStream out = System.out;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(s);
        sb.append(": ");
        sb.append(s2);
        out.println(sb.toString());
    }
    
    @Override
    public void debug(final String s, final String s2, final Throwable t) {
        final PrintStream out = System.out;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(s);
        sb.append(": ");
        sb.append(s2);
        out.println(sb.toString());
        if (t != null) {
            t.printStackTrace(System.out);
        }
    }
    
    @Override
    public void error(final String s) {
        this.debug(s);
    }
    
    @Override
    public void error(final String s, final String s2) {
        this.debug(s, s2);
    }
    
    @Override
    public void error(final String s, final String s2, final Throwable t) {
        this.debug(s, s2, t);
    }
}
