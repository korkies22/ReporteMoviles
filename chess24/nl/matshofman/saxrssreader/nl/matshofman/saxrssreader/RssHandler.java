/*
 * Decompiled with CFR 0_134.
 */
package nl.matshofman.saxrssreader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler
extends DefaultHandler {
    private RssFeed rssFeed;
    private RssItem rssItem;
    private StringBuilder stringBuilder;

    @Override
    public void characters(char[] arrc, int n, int n2) {
        this.stringBuilder.append(arrc, n, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void endElement(String charSequence, String charSequence2, String string) {
        block5 : {
            if (this.rssFeed != null && this.rssItem == null) {
                if (string == null) return;
                if (string.length() <= 0) return;
                charSequence = new StringBuilder();
                charSequence.append("set");
                charSequence.append(string.substring(0, 1).toUpperCase());
                charSequence.append(string.substring(1));
                charSequence = charSequence.toString();
                this.rssFeed.getClass().getMethod((String)charSequence, String.class).invoke(this.rssFeed, this.stringBuilder.toString());
                return;
            }
            if (this.rssItem == null) return;
            charSequence = string;
            if (!string.equals("content:encoded")) break block5;
            charSequence = "content";
        }
        try {
            charSequence2 = new StringBuilder();
            charSequence2.append("set");
            charSequence2.append(charSequence.substring(0, 1).toUpperCase());
            charSequence2.append(charSequence.substring(1));
            charSequence = charSequence2.toString();
            this.rssItem.getClass().getMethod((String)charSequence, String.class).invoke(this.rssItem, this.stringBuilder.toString());
            return;
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException exception) {
            return;
        }
    }

    public RssFeed getResult() {
        return this.rssFeed;
    }

    @Override
    public void startDocument() {
        this.rssFeed = new RssFeed();
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) {
        this.stringBuilder = new StringBuilder();
        if (string3.equals("item") && this.rssFeed != null) {
            this.rssItem = new RssItem();
            this.rssItem.setFeed(this.rssFeed);
            this.rssFeed.addRssItem(this.rssItem);
        }
    }
}
