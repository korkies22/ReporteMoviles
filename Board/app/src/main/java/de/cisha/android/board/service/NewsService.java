// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import nl.matshofman.saxrssreader.RssFeed;
import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Iterator;
import java.net.MalformedURLException;
import de.cisha.chess.util.Logger;
import java.net.URL;
import nl.matshofman.saxrssreader.RssItem;
import java.util.LinkedList;
import de.cisha.android.board.news.model.NewsItem;
import java.util.List;
import de.cisha.android.board.Language;

public class NewsService implements INewsService
{
    private IRSSReader _rssReader;
    private ILocalizedURLProvider _urlProvider;
    
    NewsService(final IRSSReader rssReader, final ILocalizedURLProvider urlProvider) {
        this._rssReader = rssReader;
        this._urlProvider = urlProvider;
    }
    
    private List<NewsItem> loadNewsItems(final Language language) {
        final URL url = this._urlProvider.getUrlFor(language);
        final LinkedList<NewsItem> list = new LinkedList<NewsItem>();
        if (url != null) {
            for (final RssItem rssItem : this._rssReader.read(url).getRssItems()) {
                URL url2;
                try {
                    url2 = new URL(rssItem.getLink());
                }
                catch (MalformedURLException ex) {
                    Logger.getInstance().debug(NewsService.class.getName(), MalformedURLException.class.getName(), ex);
                    url2 = null;
                }
                list.add(new NewsItem(rssItem.getTitle(), rssItem.getDescription(), rssItem.getTeaserImageURL(), url2, rssItem.getCategory(), rssItem.getPubDate(), rssItem.getAuthor()));
            }
        }
        return list;
    }
    
    @Override
    public void getNewsItems(final Language language, final LoadCommandCallback<List<NewsItem>> loadCommandCallback) {
        new AsyncTask<Void, Integer, List<NewsItem>>() {
            protected List<NewsItem> doInBackground(final Void... array) {
                return NewsService.this.loadNewsItems(language);
            }
            
            protected void onPostExecute(final List<NewsItem> list) {
                loadCommandCallback.loadingSucceded(list);
            }
        }.execute((Object[])new Void[0]);
    }
    
    public interface ILocalizedURLProvider
    {
        URL getUrlFor(final Language p0);
    }
    
    public interface IRSSReader
    {
        RssFeed read(final URL p0);
    }
}
