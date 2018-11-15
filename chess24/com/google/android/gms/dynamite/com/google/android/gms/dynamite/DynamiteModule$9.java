/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  dalvik.system.PathClassLoader
 */
package com.google.android.gms.dynamite;

import dalvik.system.PathClassLoader;

final class DynamiteModule
extends PathClassLoader {
    DynamiteModule(String string, ClassLoader classLoader) {
        super(string, classLoader);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
        if (string.startsWith("java.")) return super.loadClass(string, bl);
        if (string.startsWith("android.")) return super.loadClass(string, bl);
        try {
            return this.findClass(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return super.loadClass(string, bl);
        }
    }
}
