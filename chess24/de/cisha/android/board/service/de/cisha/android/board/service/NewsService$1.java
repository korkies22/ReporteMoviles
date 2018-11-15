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
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

class NewsService
extends AsyncTask<Void, Integer, List<NewsItem>> {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ Language val$language;

    NewsService(Language language, LoadCommandCallback loadCommandCallback) {
        this.val$language = language;
        this.val$callback = loadCommandCallback;
    }

    protected /* varargs */ List<NewsItem> doInBackground(Void ... arrvoid) {
        return NewsService.this.loadNewsItems(this.val$language);
    }

    protected void onPostExecute(List<NewsItem> list) {
        this.val$callback.loadingSucceded(list);
    }
}
