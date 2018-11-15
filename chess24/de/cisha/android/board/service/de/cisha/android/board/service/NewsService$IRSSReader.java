/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.NewsService;
import java.net.URL;
import nl.matshofman.saxrssreader.RssFeed;

public static interface NewsService.IRSSReader {
    public RssFeed read(URL var1);
}
