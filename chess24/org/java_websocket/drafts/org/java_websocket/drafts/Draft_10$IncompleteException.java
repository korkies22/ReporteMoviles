/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.drafts;

import org.java_websocket.drafts.Draft_10;

private class Draft_10.IncompleteException
extends Throwable {
    private static final long serialVersionUID = 7330519489840500997L;
    private int preferedsize;

    public Draft_10.IncompleteException(int n) {
        this.preferedsize = n;
    }

    public int getPreferedSize() {
        return this.preferedsize;
    }
}
