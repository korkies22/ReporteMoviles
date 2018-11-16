// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.news.model.NewsItem;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.Language;

public interface INewsService
{
    void getNewsItems(final Language p0, final LoadCommandCallback<List<NewsItem>> p1);
}
