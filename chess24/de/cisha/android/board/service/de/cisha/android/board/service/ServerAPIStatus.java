/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import java.util.Date;

public interface ServerAPIStatus {
    public Date getDeprecationDate();

    public boolean isDeprecated();

    public boolean isValid();
}
