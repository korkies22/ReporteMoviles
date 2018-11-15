/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.Language;
import de.cisha.android.board.service.NewsService;
import java.net.URL;

public static interface NewsService.ILocalizedURLProvider {
    public URL getUrlFor(Language var1);
}
