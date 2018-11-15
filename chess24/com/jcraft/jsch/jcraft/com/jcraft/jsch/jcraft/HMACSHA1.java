/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import com.jcraft.jsch.jcraft.HMAC;
import java.io.PrintStream;
import java.security.MessageDigest;

public class HMACSHA1
extends HMAC
implements MAC {
    private static final String name = "hmac-sha1";

    public HMACSHA1() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
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
