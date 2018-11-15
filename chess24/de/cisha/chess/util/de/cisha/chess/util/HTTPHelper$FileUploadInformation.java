/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.util.HTTPHelper;
import java.io.InputStream;

public static interface HTTPHelper.FileUploadInformation {
    public String getMimeType();

    public String getName();

    public InputStream getStream();
}
