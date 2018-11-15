/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.service;

import android.os.AsyncTask;
import de.cisha.android.board.Language;
import de.cisha.android.board.news.model.NewsItem;
import de.cisha.android.board.service.INewsService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;

public class NewsService
implements INewsService {
    private IRSSReader _rssReader;
    private ILocalizedURLProvider _urlProvider;

    NewsService(IRSSReader iRSSReader, ILocalizedURLProvider iLocalizedURLProvider) {
        this._rssReader = iRSSReader;
        this._urlProvider = iLocalizedURLProvider;
    }

    private List<NewsItem> loadNewsItems(Language object) {
        object = this._urlProvider.getUrlFor((Language)((Object)object));
        LinkedList<NewsItem> linkedList = new LinkedList<NewsItem>();
        if (object != null) {
            for (RssItem rssItem : this._rssReader.read((URL)object).getRssItems()) {
                try {
                    object = new URL(rssItem.getLink());
                }
                catch (MalformedURLException malformedURLException) {
                    Logger.getInstance().debug(NewsService.class.getName(), MalformedURLException.class.getName(), malformedURLException);
                    object = null;
                }
                linkedList.add(new NewsItem(rssItem.getTitle(), rssItem.getDescription(), rssItem.getTeaserImageURL(), (URL)object, rssItem.getCategory(), rssItem.getPubDate(), rssItem.getAuthor()));
            }
        }
        return linkedList;
    }

    @Override
    public void getNewsItems(final Language language, final LoadCommandCallback<List<NewsItem>> loadCommandCallback) {
        new AsyncTask<Void, Integer, List<NewsItem>>(){

            protected /* varargs */ List<NewsItem> doInBackground(Void ... arrvoid) {
                return NewsService.this.loadNewsItems(language);
            }

            protected void onPostExecute(List<NewsItem> list) {
                loadCommandCallback.loadingSucceded(list);
            }
        }.execute((Object[])new Void[0]);
    }

    public static interface ILocalizedURLProvider {
        public URL getUrlFor(Language var1);
    }

    public static interface IRSSReader {
        public RssFeed read(URL var1);
    }

}
