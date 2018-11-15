/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.exceptions;

import org.java_websocket.exceptions.InvalidDataException;

public class InvalidHandshakeException
extends InvalidDataException {
    private static final long serialVersionUID = -1426533877490484964L;

    public InvalidHandshakeException() {
        super(1002);
    }

    public InvalidHandshakeException(String string) {
        super(1002, string);
    }

    public InvalidHandshakeException(String string, Throwable throwable) {
        super(1002, string, throwable);
    }

    public InvalidHandshakeException(Throwable throwable) {
        super(1002, throwable);
    }
}
