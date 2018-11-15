/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.news;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import de.cisha.android.board.news.NewsFragment;

class NewsFragment.NewsListAdapter
implements View.OnClickListener {
    final /* synthetic */ Uri val$linkUri;

    NewsFragment.NewsListAdapter(Uri uri) {
        this.val$linkUri = uri;
    }

    public void onClick(View view) {
        view = new Intent("android.intent.action.VIEW", this.val$linkUri);
        NewsListAdapter.this.this$0.startActivity((Intent)view);
    }
}
