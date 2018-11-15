/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

public interface Log {
    public boolean isLevelEnabled(Level var1);

    public void log(Level var1, String var2);

    public void log(Level var1, String var2, Throwable var3);

    public static enum Level {
        TRACE(1),
        DEBUG(2),
        INFO(3),
        WARNING(4),
        ERROR(5),
        FATAL(6);
        
        private int level;

        private Level(int n2) {
            this.level = n2;
        }

        public boolean isEnabled(Level level) {
            if (this.level <= level.level) {
                return true;
            }
            return false;
        }
    }

}
