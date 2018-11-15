/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;

public static interface ConfigRepository.Config {
    public String getHostname();

    public int getPort();

    public String getUser();

    public String getValue(String var1);

    public String[] getValues(String var1);
}
