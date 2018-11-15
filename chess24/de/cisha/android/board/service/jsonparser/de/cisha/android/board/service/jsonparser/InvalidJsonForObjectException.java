/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.jsonparser;

public class InvalidJsonForObjectException
extends Exception {
    private static final long serialVersionUID = -4928966488411790780L;

    public InvalidJsonForObjectException() {
    }

    public InvalidJsonForObjectException(String string) {
        super(string);
    }

    public InvalidJsonForObjectException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidJsonForObjectException(Throwable throwable) {
        super(throwable);
    }
}
