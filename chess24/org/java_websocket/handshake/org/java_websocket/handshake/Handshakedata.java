/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.handshake;

import java.util.Iterator;

public interface Handshakedata {
    public byte[] getContent();

    public String getFieldValue(String var1);

    public boolean hasFieldValue(String var1);

    public Iterator<String> iterateHttpFields();
}
