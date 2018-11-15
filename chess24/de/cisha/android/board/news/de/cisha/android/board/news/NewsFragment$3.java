/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.news.NewsFragment;
import de.cisha.android.board.news.model.NewsItem;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class NewsFragment
extends LoadCommandCallbackWithTimeout<List<NewsItem>> {
    NewsFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        NewsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                NewsFragment.this._refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void succeded(final List<NewsItem> list) {
        NewsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                NewsFragment.this._refreshLayout.setRefreshing(false);
                NewsFragment.this._newsListAdapter = new NewsFragment.NewsListAdapter(NewsFragment.this, list);
                NewsFragment.this._newsListView.setAdapter(NewsFragment.this._newsListAdapter);
            }
        });
    }

}
