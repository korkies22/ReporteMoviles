/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.video;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.view.VideoAuthorAndEloRangeView;
import de.cisha.android.view.CouchImageView;

private class VideoSeriesListFragment.SeriesListAdapter.ViewHolder
extends RecyclerView.ViewHolder {
    private VideoAuthorAndEloRangeView _authorEloView;
    private TextView _durationTextView;
    private ImageView _languageImageView;
    private View _offlineModeView;
    private View _playButtonView;
    private TextView _priceTextView;
    private VideoSeriesId _seriesId;
    private CouchImageView _teaserImageView;
    private TextView _titleTextView;
    private TextView _videoCountTextView;

    public VideoSeriesListFragment.SeriesListAdapter.ViewHolder(CardView cardView) {
        super((View)cardView);
        this._titleTextView = (TextView)cardView.findViewById(2131297258);
        this._durationTextView = (TextView)cardView.findViewById(2131297252);
        this._languageImageView = (ImageView)cardView.findViewById(2131297254);
        this._priceTextView = (TextView)cardView.findViewById(2131297256);
        this._playButtonView = cardView.findViewById(2131297255);
        this._videoCountTextView = (TextView)cardView.findViewById(2131297259);
        this._teaserImageView = (CouchImageView)cardView.findViewById(2131297257);
        this._authorEloView = (VideoAuthorAndEloRangeView)cardView.findViewById(2131297198);
        this._offlineModeView = cardView.findViewById(2131297260);
        cardView.setOnClickListener(new View.OnClickListener(SeriesListAdapter.this){
            final /* synthetic */ VideoSeriesListFragment.SeriesListAdapter val$this$1;
            {
                this.val$this$1 = seriesListAdapter;
            }

            public void onClick(View view) {
                SeriesListAdapter.this.this$0.videoSeriesSelected(ViewHolder.this._seriesId);
            }
        });
        this._playButtonView.setOnClickListener(new View.OnClickListener(SeriesListAdapter.this){
            final /* synthetic */ VideoSeriesListFragment.SeriesListAdapter val$this$1;
            {
                this.val$this$1 = seriesListAdapter;
            }

            public void onClick(View view) {
                SeriesListAdapter.this.this$0.videoSeriesSelected(ViewHolder.this._seriesId);
            }
        });
    }

    static /* synthetic */ VideoSeriesId access$2602(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder, VideoSeriesId videoSeriesId) {
        viewHolder._seriesId = videoSeriesId;
        return videoSeriesId;
    }

    static /* synthetic */ TextView access$2700(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._titleTextView;
    }

    static /* synthetic */ VideoAuthorAndEloRangeView access$2800(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._authorEloView;
    }

    static /* synthetic */ TextView access$2900(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._durationTextView;
    }

    static /* synthetic */ ImageView access$3000(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._languageImageView;
    }

    static /* synthetic */ TextView access$3100(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._videoCountTextView;
    }

    static /* synthetic */ TextView access$3200(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._priceTextView;
    }

    static /* synthetic */ View access$3600(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._playButtonView;
    }

    static /* synthetic */ CouchImageView access$3700(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._teaserImageView;
    }

    static /* synthetic */ View access$3800(VideoSeriesListFragment.SeriesListAdapter.ViewHolder viewHolder) {
        return viewHolder._offlineModeView;
    }

}
