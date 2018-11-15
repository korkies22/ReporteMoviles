/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.ECDH;
import com.jcraft.jsch.jce.ECDHN;

public class ECDH521
extends ECDHN
implements ECDH {
    public void init() throws Exception {
        super.init(521);
    }
}
