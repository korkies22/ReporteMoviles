/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.news.NewsFragment;
import de.cisha.android.board.news.model.NewsItem;
import java.util.List;

class NewsFragment
implements Runnable {
    final /* synthetic */ List val$result;

    NewsFragment(List list) {
        this.val$result = list;
    }

    @Override
    public void run() {
        3.this.this$0._refreshLayout.setRefreshing(false);
        3.this.this$0._newsListAdapter = new NewsFragment.NewsListAdapter(3.this.this$0, this.val$result);
        3.this.this$0._newsListView.setAdapter(3.this.this$0._newsListAdapter);
    }
}
