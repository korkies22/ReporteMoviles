/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.trivialdrivesample.util.SkuDetails;
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
import de.cisha.android.board.util.TimeHelper;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.view.VideoAuthorAndEloRangeView;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

public class VideoSeriesListFragment.SeriesListAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private List<VideoSeriesInformation> _seriesList;

    public VideoSeriesListFragment.SeriesListAdapter(List<VideoSeriesInformation> list) {
        this._seriesList = list;
    }

    @Override
    public int getItemCount() {
        return this._seriesList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int n) {
        final VideoSeriesInformation videoSeriesInformation = this._seriesList.get(n);
        viewHolder._titleTextView.setText((CharSequence)videoSeriesInformation.getName());
        viewHolder._authorEloView.setAuthor(videoSeriesInformation.getAuthor());
        viewHolder._authorEloView.setEloRange(videoSeriesInformation.getEloRange());
        viewHolder._durationTextView.setText((CharSequence)TimeHelper.getDurationString(videoSeriesInformation.getDuration()));
        viewHolder._languageImageView.setImageResource(videoSeriesInformation.getLanguage().getImageResId());
        Object object = viewHolder._videoCountTextView;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(videoSeriesInformation.getVideoCount());
        charSequence.append("");
        object.setText((CharSequence)charSequence.toString());
        object = viewHolder._priceTextView;
        boolean bl = videoSeriesInformation.isAccessible();
        int n2 = 8;
        int n3 = 0;
        int n4 = bl ? 8 : 0;
        object.setVisibility(n4);
        object = (SkuDetails)VideoSeriesListFragment.this._productPriceMap.get(videoSeriesInformation.getPriceCategoryId());
        if (object != null) {
            viewHolder._priceTextView.setText((CharSequence)object.getPrice());
            viewHolder._priceTextView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() != IMembershipService.MembershipLevel.MembershipLevelGuest) {
                        VideoSeriesListFragment.this.showDialogWaiting(true, true, "", null);
                        ServiceProvider.getInstance().getVideoService().purchaseVideoSeries(videoSeriesInformation.getVideoSeriesId(), videoSeriesInformation.getPriceCategoryId(), VideoSeriesListFragment.this.getActivity(), new LoadCommandCallback<Void>(){

                            @Override
                            public void loadingCancelled() {
                                VideoSeriesListFragment.this.hideDialogWaiting();
                            }

                            @Override
                            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                                VideoSeriesListFragment.this.hideDialogWaiting();
                                VideoSeriesListFragment.this.showViewForErrorCode(aPIStatusCode, null);
                            }

                            @Override
                            public void loadingSucceded(Void void_) {
                                VideoSeriesListFragment.this.hideDialogWaiting();
                                videoSeriesInformation.setIsPurchased(true);
                                videoSeriesInformation.setIsAccessible(true);
                                VideoSeriesListFragment.this._seriesListAdapter.notifyItemChanged(n);
                            }
                        });
                        return;
                    }
                    VideoSeriesListFragment.this.getContentPresenter().showConversionDialog(null, ConversionContext.VIDEO_SERIES_OVERVIEW);
                }

            });
        } else {
            viewHolder._priceTextView.setText((CharSequence)"-");
            viewHolder._priceTextView.setOnClickListener(null);
            object = Logger.getInstance();
            charSequence = VideoSeriesListFragment.class.getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("product info is missing for price category ");
            stringBuilder.append(videoSeriesInformation.getPriceCategoryId());
            object.error((String)charSequence, stringBuilder.toString());
        }
        object = viewHolder._playButtonView;
        n = n2;
        if (videoSeriesInformation.isAccessible()) {
            n = 0;
        }
        object.setVisibility(n);
        viewHolder._teaserImageView.setImageResource(2131231877);
        viewHolder._seriesId = videoSeriesInformation.getVideoSeriesId();
        object = videoSeriesInformation.getThumbCouchId();
        if (object != null) {
            viewHolder._teaserImageView.setCouchId((CishaUUID)object);
        }
        n = VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode != null && VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode.contains(videoSeriesInformation.getVideoSeriesId()) ? 1 : 0;
        viewHolder = viewHolder._offlineModeView;
        n = n != 0 ? n3 : 4;
        viewHolder.setVisibility(n);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder((CardView)LayoutInflater.from((Context)viewGroup.getContext()).inflate(2131427582, viewGroup, false));
    }

    private class ViewHolder
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

        public ViewHolder(CardView cardView) {
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
                    VideoSeriesListFragment.this.videoSeriesSelected(ViewHolder.this._seriesId);
                }
            });
            this._playButtonView.setOnClickListener(new View.OnClickListener(SeriesListAdapter.this){
                final /* synthetic */ VideoSeriesListFragment.SeriesListAdapter val$this$1;
                {
                    this.val$this$1 = seriesListAdapter;
                }

                public void onClick(View view) {
                    VideoSeriesListFragment.this.videoSeriesSelected(ViewHolder.this._seriesId);
                }
            });
        }

    }

}
