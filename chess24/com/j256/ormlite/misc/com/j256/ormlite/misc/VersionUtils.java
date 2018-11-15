/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.misc;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import java.io.Serializable;

public class VersionUtils {
    private static final String CORE_VERSION = "VERSION__4.48__";
    private static String coreVersion = "VERSION__4.48__";
    private static Logger logger;
    private static boolean thrownOnErrors = false;

    private VersionUtils() {
    }

    public static final void checkCoreVersusAndroidVersions(String string) {
        VersionUtils.logVersionWarnings("core", coreVersion, "android", string);
    }

    public static final void checkCoreVersusJdbcVersions(String string) {
        VersionUtils.logVersionWarnings("core", coreVersion, "jdbc", string);
    }

    public static String getCoreVersion() {
        return coreVersion;
    }

    private static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(VersionUtils.class);
        }
        return logger;
    }

    private static void logVersionWarnings(String string, String string2, String string3, String string4) {
        if (string2 == null) {
            if (string4 != null) {
                VersionUtils.warning(null, "Unknown version", " for {}, version for {} is '{}'", new Object[]{string, string3, string4});
                return;
            }
        } else {
            if (string4 == null) {
                VersionUtils.warning(null, "Unknown version", " for {}, version for {} is '{}'", new Object[]{string3, string, string2});
                return;
            }
            if (!string2.equals(string4)) {
                VersionUtils.warning(null, "Mismatched versions", ": {} is '{}', while {} is '{}'", new Object[]{string, string2, string3, string4});
            }
        }
    }

    static void setThrownOnErrors(boolean bl) {
        thrownOnErrors = bl;
    }

    private static void warning(Throwable serializable, String string, String string2, Object[] arrobject) {
        Logger logger = VersionUtils.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(string2);
        logger.warn((Throwable)serializable, stringBuilder.toString(), arrobject);
        if (thrownOnErrors) {
            serializable = new StringBuilder();
            serializable.append("See error log for details:");
            serializable.append(string);
            throw new IllegalStateException(serializable.toString());
        }
    }
}
