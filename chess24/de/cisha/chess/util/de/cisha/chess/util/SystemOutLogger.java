/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.util.Logger;
import java.io.PrintStream;

public class SystemOutLogger
extends Logger {
    @Override
    public void debug(String string) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        printStream.println(stringBuilder.toString());
    }

    @Override
    public void debug(String string, String string2) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        printStream.println(stringBuilder.toString());
    }

    @Override
    public void debug(String string, String string2, Throwable throwable) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        printStream.println(stringBuilder.toString());
        if (throwable != null) {
            throwable.printStackTrace(System.out);
        }
    }

    @Override
    public void error(String string) {
        this.debug(string);
    }

    @Override
    public void error(String string, String string2) {
        this.debug(string, string2);
    }

    @Override
    public void error(String string, String string2, Throwable throwable) {
        this.debug(string, string2, throwable);
    }
}
