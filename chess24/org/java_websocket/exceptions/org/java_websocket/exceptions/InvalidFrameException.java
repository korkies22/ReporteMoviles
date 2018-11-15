/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.exceptions;

import org.java_websocket.exceptions.InvalidDataException;

public class InvalidFrameException
extends InvalidDataException {
    private static final long serialVersionUID = -9016496369828887591L;

    public InvalidFrameException() {
        super(1002);
    }

    public InvalidFrameException(String string) {
        super(1002, string);
    }

    public InvalidFrameException(String string, Throwable throwable) {
        super(1002, string, throwable);
    }

    public InvalidFrameException(Throwable throwable) {
        super(1002, throwable);
    }
}
