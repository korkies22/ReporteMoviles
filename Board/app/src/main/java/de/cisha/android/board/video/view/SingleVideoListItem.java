// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import de.cisha.android.board.util.TimeHelper;
import android.net.Uri;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.video.model.VideoInformation;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.view.CouchImageView;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import android.widget.RelativeLayout;

public class SingleVideoListItem extends RelativeLayout implements LocalVideoStateChangeListener
{
    private TextView _description;
    private View _downloadImage;
    private TextView _downloadProgress;
    private TextView _duration;
    private LocalVideoInfo _localVideoInfo;
    private ImageView _playImage;
    private TextView _title;
    private CouchImageView _videoImage;
    
    public SingleVideoListItem(final Context context, final AttributeSet set) {
        super(context, set);
        this.initialize(context);
    }
    
    public SingleVideoListItem(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.initialize(context);
    }
    
    public SingleVideoListItem(final Context context, final VideoInformation videoInformation) {
        super(context);
        this.initialize(context);
        this.setVideoInformation(videoInformation);
    }
    
    private void initialize(final Context context) {
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427578, (ViewGroup)this, true);
        this._videoImage = (CouchImageView)this.findViewById(2131297249);
        this._title = (TextView)this.findViewById(2131297250);
        this._description = (TextView)this.findViewById(2131297199);
        this._duration = (TextView)this.findViewById(2131297202);
        this._downloadProgress = (TextView)this.findViewById(2131297200);
        this._downloadImage = this.findViewById(2131297201);
        this._playImage = (ImageView)this.findViewById(2131297222);
        this.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (SingleVideoListItem.this._description.getVisibility() == 8) {
                    SingleVideoListItem.this._description.setVisibility(0);
                    return;
                }
                SingleVideoListItem.this._description.setVisibility(8);
            }
        });
    }
    
    private void setDownloadProgress(final float n) {
        final TextView downloadProgress = this._downloadProgress;
        final StringBuilder sb = new StringBuilder();
        sb.append((int)(n * 100.0f));
        sb.append(" %");
        downloadProgress.setText((CharSequence)sb.toString());
    }
    
    public void onLocalVideoStateChanged(final LocalVideoInfo localVideoInfo, final boolean b, final float n, final Uri uri, final LocalVideoServiceDownloadState localVideoServiceDownloadState) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (localVideoServiceDownloadState == LocalVideoServiceDownloadState.DOWNLOADING) {
                    SingleVideoListItem.this._downloadProgress.setVisibility(0);
                    SingleVideoListItem.this._downloadProgress.setTextColor(SingleVideoListItem.this.getResources().getColor(2131099702));
                    SingleVideoListItem.this._downloadImage.setVisibility(0);
                    SingleVideoListItem.this._playImage.setAlpha(100);
                    SingleVideoListItem.this._videoImage.setAlpha(100);
                    SingleVideoListItem.this.setDownloadProgress(n);
                    return;
                }
                if (localVideoServiceDownloadState == LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE) {
                    SingleVideoListItem.this._downloadProgress.setText(2131690395);
                    SingleVideoListItem.this._downloadProgress.setTextColor(-65536);
                    return;
                }
                if (localVideoServiceDownloadState == LocalVideoServiceDownloadState.COMPLETED) {
                    SingleVideoListItem.this._downloadProgress.setText((CharSequence)"");
                    SingleVideoListItem.this._playImage.setAlpha(255);
                    SingleVideoListItem.this._videoImage.setAlpha(255);
                    return;
                }
                final LocalVideoServiceDownloadState val.state = localVideoServiceDownloadState;
                final LocalVideoInfo.LocalVideoServiceDownloadState waiting_FOR_WIRELESS_LAN = LocalVideoServiceDownloadState.WAITING_FOR_WIRELESS_LAN;
            }
        });
    }
    
    public void setLocalVideoInfo(final LocalVideoInfo localVideoInfo) {
        if (localVideoInfo != null) {
            this.onLocalVideoStateChanged(localVideoInfo, localVideoInfo.isVideoObjectAvailable(), localVideoInfo.getDownloadProgress(), localVideoInfo.getVideoFile(), localVideoInfo.getState());
            localVideoInfo.addListener((LocalVideoInfo.LocalVideoStateChangeListener)this);
        }
        else {
            if (this._localVideoInfo != null) {
                this._localVideoInfo.removeListener((LocalVideoInfo.LocalVideoStateChangeListener)this);
            }
            this.post((Runnable)new Runnable() {
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
    
    public void setOnVideoClickListener(final View.OnClickListener view.OnClickListener) {
        this._videoImage.setOnClickListener(view.OnClickListener);
        this._playImage.setOnClickListener(view.OnClickListener);
    }
    
    public void setVideoInformation(final VideoInformation videoInformation) {
        this._videoImage.setCouchId(videoInformation.getTeaserCouchId());
        this._title.setText((CharSequence)videoInformation.getTitle());
        this._description.setText((CharSequence)videoInformation.getDescription());
        this._duration.setText((CharSequence)TimeHelper.getDurationString(videoInformation.getDuration()));
        if (!videoInformation.isAccessible()) {
            this._playImage.setImageResource(2131231882);
            return;
        }
        if (!videoInformation.isInProgress()) {
            this._playImage.setImageResource(2131231893);
            return;
        }
        this._playImage.setImageResource(2131231879);
    }
}
