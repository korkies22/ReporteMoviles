/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.jce.HMAC;

public class HMACSHA1
extends HMAC {
    public HMACSHA1() {
        this.name = "hmac-sha1";
        this.bsize = 20;
        this.algorithm = "HmacSHA1";
    }
}
