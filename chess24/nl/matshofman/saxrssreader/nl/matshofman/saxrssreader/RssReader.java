/*
 * Decompiled with CFR 0_134.
 */
package nl.matshofman.saxrssreader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssReader {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static RssFeed read(InputStream object) throws SAXException, IOException {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            RssHandler rssHandler = new RssHandler();
            object = new InputSource((InputStream)object);
            xMLReader.setContentHandler(rssHandler);
            xMLReader.parse((InputSource)object);
            return rssHandler.getResult();
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new SAXException();
        }
    }

    public static RssFeed read(String string) throws SAXException, IOException {
        return RssReader.read(new ByteArrayInputStream(string.getBytes()));
    }

    public static RssFeed read(URL uRL) throws SAXException, IOException {
        return RssReader.read(uRL.openConnection().getInputStream());
    }
}
