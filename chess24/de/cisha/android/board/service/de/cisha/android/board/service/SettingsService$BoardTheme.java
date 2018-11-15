/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.SettingsService;

public static enum SettingsService.BoardTheme {
    WOOD1("wood"),
    SILVER1("silver"),
    BLUE1("blue");
    
    private final String _prefix;

    private SettingsService.BoardTheme(String string2) {
        this._prefix = string2;
    }

    public String getPrefix() {
        return this._prefix;
    }
}
