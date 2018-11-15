/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

public abstract class Crash {
    private static final String UNKNOWN_EXCEPTION = "<unknown>";
    private final String exceptionName;
    private final String sessionId;

    public Crash(String string) {
        this(string, UNKNOWN_EXCEPTION);
    }

    public Crash(String string, String string2) {
        this.sessionId = string;
        this.exceptionName = string2;
    }

    public String getExceptionName() {
        return this.exceptionName;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public static class FatalException
    extends Crash {
        public FatalException(String string) {
            super(string);
        }

        public FatalException(String string, String string2) {
            super(string, string2);
        }
    }

    public static class LoggedException
    extends Crash {
        public LoggedException(String string) {
            super(string);
        }

        public LoggedException(String string, String string2) {
            super(string, string2);
        }
    }

}
