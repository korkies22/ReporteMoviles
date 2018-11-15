/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.handshake;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import org.java_websocket.handshake.HandshakeBuilder;

public class HandshakedataImpl1
implements HandshakeBuilder {
    private byte[] content;
    private TreeMap<String, String> map = new TreeMap(String.CASE_INSENSITIVE_ORDER);

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public String getFieldValue(String string) {
        if ((string = this.map.get(string)) == null) {
            return "";
        }
        return string;
    }

    @Override
    public boolean hasFieldValue(String string) {
        return this.map.containsKey(string);
    }

    @Override
    public Iterator<String> iterateHttpFields() {
        return Collections.unmodifiableSet(this.map.keySet()).iterator();
    }

    @Override
    public void put(String string, String string2) {
        this.map.put(string, string2);
    }

    @Override
    public void setContent(byte[] arrby) {
        this.content = arrby;
    }
}
