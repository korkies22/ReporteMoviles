/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesListFragment
extends LoadCommandCallbackWithTimeout<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> {
    VideoSeriesListFragment() {
    }

    @Override
    protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesListFragment.this.hideDialogWaiting();
                VideoSeriesListFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        VideoSeriesListFragment.this.loadSeriesList();
                    }
                });
            }

        });
        VideoSeriesListFragment.this.loadOfflineSeries();
    }

    @Override
    protected void succeded(final JSONVideoSeriesInfoListParser.VideoSeriesInfoList videoSeriesInfoList) {
        VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesListFragment.this.hideDialogWaiting();
                Object object = VideoSeriesListFragment.this._showOfflineOnly ? VideoSeriesListFragment.this.filterListOfflineOnly(videoSeriesInfoList.getList()) : videoSeriesInfoList.getList();
                VideoSeriesListFragment.this._seriesListAdapter = new VideoSeriesListFragment.SeriesListAdapter(VideoSeriesListFragment.this, (List<VideoSeriesInformation>)object);
                VideoSeriesListFragment.this._recyclerView.setAdapter(VideoSeriesListFragment.this._seriesListAdapter);
                object = VideoSeriesListFragment.this;
                boolean bl = videoSeriesInfoList.getSeriesCount() == 0;
                ((de.cisha.android.board.video.VideoSeriesListFragment)object).showNoResultsView(bl);
            }
        });
    }

}
