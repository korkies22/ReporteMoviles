/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.exceptions;

import org.java_websocket.exceptions.InvalidDataException;

public class LimitExedeedException
extends InvalidDataException {
    private static final long serialVersionUID = 6908339749836826785L;

    public LimitExedeedException() {
        super(1009);
    }

    public LimitExedeedException(String string) {
        super(1009, string);
    }
}
