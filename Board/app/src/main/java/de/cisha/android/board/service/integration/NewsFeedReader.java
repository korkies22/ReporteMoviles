// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.integration;

import org.xml.sax.SAXException;
import java.io.IOException;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.util.Logger;
import nl.matshofman.saxrssreader.RssReader;
import nl.matshofman.saxrssreader.RssFeed;
import java.net.URL;
import de.cisha.android.board.service.NewsService;

public class NewsFeedReader implements IRSSReader
{
    @Override
    public RssFeed read(final URL url) {
        try {
            return RssReader.read(url);
        }
        catch (IOException ex) {
            Logger.getInstance().error(ServiceProvider.class.getName(), IOException.class.getName(), ex);
        }
        catch (SAXException ex2) {
            Logger.getInstance().error(ServiceProvider.class.getName(), SAXException.class.getName(), ex2);
        }
        return new RssFeed();
    }
}
