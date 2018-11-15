/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.Language;
import de.cisha.android.board.news.model.NewsItem;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

public interface INewsService {
    public void getNewsItems(Language var1, LoadCommandCallback<List<NewsItem>> var2);
}
