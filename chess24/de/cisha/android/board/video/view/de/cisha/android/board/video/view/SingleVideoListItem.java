/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.board.util.TimeHelper;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;

public class SingleVideoListItem
extends RelativeLayout
implements LocalVideoInfo.LocalVideoStateChangeListener {
    private TextView _description;
    private View _downloadImage;
    private TextView _downloadProgress;
    private TextView _duration;
    private LocalVideoInfo _localVideoInfo;
    private ImageView _playImage;
    private TextView _title;
    private CouchImageView _videoImage;

    public SingleVideoListItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize(context);
    }

    public SingleVideoListItem(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initialize(context);
    }

    public SingleVideoListItem(Context context, VideoInformation videoInformation) {
        super(context);
        this.initialize(context);
        this.setVideoInformation(videoInformation);
    }

    private void initialize(Context context) {
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427578, (ViewGroup)this, true);
        this._videoImage = (CouchImageView)this.findViewById(2131297249);
        this._title = (TextView)this.findViewById(2131297250);
        this._description = (TextView)this.findViewById(2131297199);
        this._duration = (TextView)this.findViewById(2131297202);
        this._downloadProgress = (TextView)this.findViewById(2131297200);
        this._downloadImage = this.findViewById(2131297201);
        this._playImage = (ImageView)this.findViewById(2131297222);
        this.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (SingleVideoListItem.this._description.getVisibility() == 8) {
                    SingleVideoListItem.this._description.setVisibility(0);
                    return;
                }
                SingleVideoListItem.this._description.setVisibility(8);
            }
        });
    }

    private void setDownloadProgress(float f) {
        TextView textView = this._downloadProgress;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((int)(f * 100.0f));
        stringBuilder.append(" %");
        textView.setText((CharSequence)stringBuilder.toString());
    }

    @Override
    public void onLocalVideoStateChanged(LocalVideoInfo localVideoInfo, boolean bl, final float f, Uri uri, final LocalVideoInfo.LocalVideoServiceDownloadState localVideoServiceDownloadState) {
        this.post(new Runnable(){

            @Override
            public void run() {
                if (localVideoServiceDownloadState == LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING) {
                    SingleVideoListItem.this._downloadProgress.setVisibility(0);
                    SingleVideoListItem.this._downloadProgress.setTextColor(SingleVideoListItem.this.getResources().getColor(2131099702));
                    SingleVideoListItem.this._downloadImage.setVisibility(0);
                    SingleVideoListItem.this._playImage.setAlpha(100);
                    SingleVideoListItem.this._videoImage.setAlpha(100);
                    SingleVideoListItem.this.setDownloadProgress(f);
                    return;
                }
                if (localVideoServiceDownloadState == LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE) {
                    SingleVideoListItem.this._downloadProgress.setText(2131690395);
                    SingleVideoListItem.this._downloadProgress.setTextColor(-65536);
                    return;
                }
                if (localVideoServiceDownloadState == LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED) {
                    SingleVideoListItem.this._downloadProgress.setText((CharSequence)"");
                    SingleVideoListItem.this._playImage.setAlpha(255);
                    SingleVideoListItem.this._videoImage.setAlpha(255);
                    return;
                }
                LocalVideoInfo.LocalVideoServiceDownloadState localVideoServiceDownloadState2 = localVideoServiceDownloadState;
                localVideoServiceDownloadState2 = LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_WIRELESS_LAN;
            }
        });
    }

    public void setLocalVideoInfo(LocalVideoInfo localVideoInfo) {
        if (localVideoInfo != null) {
            this.onLocalVideoStateChanged(localVideoInfo, localVideoInfo.isVideoObjectAvailable(), localVideoInfo.getDownloadProgress(), localVideoInfo.getVideoFile(), localVideoInfo.getState());
            localVideoInfo.addListener(this);
        } else {
            if (this._localVideoInfo != null) {
                this._localVideoInfo.removeListener(this);
            }
            this.post(new Runnable(){

                @Override
                public void run() {
                    SingleVideoListItem.this.setBackgroundResource(0);
                    SingleVideoListItem.this.setDownloadProgress(0.0f);
                    SingleVideoListItem.this._downloadProgress.setVisibility(8);
                    SingleVideoListItem.this._downloadImage.setVisibility(8);
                    SingleVideoListItem.this._playImage.setAlpha(255);
                    SingleVideoListItem.this._videoImage.setAlpha(255);
                }
            });
        }
        this._localVideoInfo = localVideoInfo;
    }

    public void setOnVideoClickListener(View.OnClickListener onClickListener) {
        this._videoImage.setOnClickListener(onClickListener);
        this._playImage.setOnClickListener(onClickListener);
    }

    public void setVideoInformation(VideoInformation videoInformation) {
        this._videoImage.setCouchId(videoInformation.getTeaserCouchId());
        this._title.setText((CharSequence)videoInformation.getTitle());
        this._description.setText((CharSequence)videoInformation.getDescription());
        this._duration.setText((CharSequence)TimeHelper.getDurationString(videoInformation.getDuration()));
        if (videoInformation.isAccessible()) {
            if (!videoInformation.isInProgress()) {
                this._playImage.setImageResource(2131231893);
                return;
            }
            this._playImage.setImageResource(2131231879);
            return;
        }
        this._playImage.setImageResource(2131231882);
    }

}
