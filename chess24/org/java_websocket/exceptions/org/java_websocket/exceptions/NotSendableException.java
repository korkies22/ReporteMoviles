/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.exceptions;

public class NotSendableException
extends RuntimeException {
    private static final long serialVersionUID = -6468967874576651628L;

    public NotSendableException() {
    }

    public NotSendableException(String string) {
        super(string);
    }

    public NotSendableException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public NotSendableException(Throwable throwable) {
        super(throwable);
    }
}
