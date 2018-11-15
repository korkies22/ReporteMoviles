/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import com.jcraft.jsch.jcraft.HMAC;
import java.io.PrintStream;
import java.security.MessageDigest;

public class HMACMD5
extends HMAC
implements MAC {
    private static final String name = "hmac-md5";

    public HMACMD5() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        }
        catch (Exception exception) {
            System.err.println(exception);
            messageDigest = null;
        }
        this.setH(messageDigest);
    }

    @Override
    public String getName() {
        return name;
    }
}
