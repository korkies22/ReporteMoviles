/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;

static final class ConfigRepository
implements com.jcraft.jsch.ConfigRepository {
    ConfigRepository() {
    }

    @Override
    public ConfigRepository.Config getConfig(String string) {
        return defaultConfig;
    }
}
