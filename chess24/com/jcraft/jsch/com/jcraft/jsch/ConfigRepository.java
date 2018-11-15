/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface ConfigRepository {
    public static final Config defaultConfig = new Config(){

        @Override
        public String getHostname() {
            return null;
        }

        @Override
        public int getPort() {
            return -1;
        }

        @Override
        public String getUser() {
            return null;
        }

        @Override
        public String getValue(String string) {
            return null;
        }

        @Override
        public String[] getValues(String string) {
            return null;
        }
    };
    public static final ConfigRepository nullConfig = new ConfigRepository(){

        @Override
        public Config getConfig(String string) {
            return defaultConfig;
        }
    };

    public Config getConfig(String var1);

    public static interface Config {
        public String getHostname();

        public int getPort();

        public String getUser();

        public String getValue(String var1);

        public String[] getValues(String var1);
    }

}
