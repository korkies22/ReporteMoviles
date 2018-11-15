/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.View
 *  android.view.View$OnClickListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesListFragment.SeriesListAdapter
implements View.OnClickListener {
    final /* synthetic */ int val$position;
    final /* synthetic */ VideoSeriesInformation val$series;

    VideoSeriesListFragment.SeriesListAdapter(VideoSeriesInformation videoSeriesInformation, int n) {
        this.val$series = videoSeriesInformation;
        this.val$position = n;
    }

    public void onClick(View view) {
        if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() != IMembershipService.MembershipLevel.MembershipLevelGuest) {
            SeriesListAdapter.this.this$0.showDialogWaiting(true, true, "", null);
            ServiceProvider.getInstance().getVideoService().purchaseVideoSeries(this.val$series.getVideoSeriesId(), this.val$series.getPriceCategoryId(), SeriesListAdapter.this.this$0.getActivity(), new LoadCommandCallback<Void>(){

                @Override
                public void loadingCancelled() {
                    SeriesListAdapter.this.this$0.hideDialogWaiting();
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    SeriesListAdapter.this.this$0.hideDialogWaiting();
                    SeriesListAdapter.this.this$0.showViewForErrorCode(aPIStatusCode, null);
                }

                @Override
                public void loadingSucceded(Void void_) {
                    SeriesListAdapter.this.this$0.hideDialogWaiting();
                    1.this.val$series.setIsPurchased(true);
                    1.this.val$series.setIsAccessible(true);
                    SeriesListAdapter.this.this$0._seriesListAdapter.notifyItemChanged(1.this.val$position);
                }
            });
            return;
        }
        SeriesListAdapter.this.this$0.getContentPresenter().showConversionDialog(null, ConversionContext.VIDEO_SERIES_OVERVIEW);
    }

}
