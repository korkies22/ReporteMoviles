/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.integration;

import de.cisha.android.board.service.NewsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.net.URL;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssReader;
import org.xml.sax.SAXException;

public class NewsFeedReader
implements NewsService.IRSSReader {
    @Override
    public RssFeed read(URL object) {
        try {
            object = RssReader.read((URL)object);
            return object;
        }
        catch (IOException iOException) {
            Logger.getInstance().error(ServiceProvider.class.getName(), IOException.class.getName(), iOException);
        }
        catch (SAXException sAXException) {
            Logger.getInstance().error(ServiceProvider.class.getName(), SAXException.class.getName(), sAXException);
        }
        return new RssFeed();
    }
}
