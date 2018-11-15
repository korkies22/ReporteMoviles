/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.util.TimeHelper;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.view.VideoAuthorAndEloRangeView;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;
import java.util.List;

public class VideoSeriesHeaderView
extends LinearLayout {
    private VideoAuthorAndEloRangeView _authorAndElo;
    private TextView _duration;
    private ImageView _flag;
    private TextView _goals;
    private TextView _introduction;
    private TextView _numberOfEpisodes;
    private View _playButton;
    private TextView _price;
    private CouchImageView _teaserImage;
    private TextView _title;

    public VideoSeriesHeaderView(Context context) {
        super(context);
        this.initialize(context);
    }

    public VideoSeriesHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize(context);
    }

    private void initialize(Context context) {
        context = (LayoutInflater)context.getSystemService("layout_inflater");
        this.setOrientation(1);
        context.inflate(2131427580, (ViewGroup)this, true);
        this._teaserImage = (CouchImageView)this.findViewById(2131297228);
        this._flag = (ImageView)this.findViewById(2131297231);
        this._numberOfEpisodes = (TextView)this.findViewById(2131297241);
        this._duration = (TextView)this.findViewById(2131297223);
        this._title = (TextView)this.findViewById(2131297244);
        this._price = (TextView)this.findViewById(2131297243);
        this._playButton = this.findViewById(2131297242);
        this._authorAndElo = (VideoAuthorAndEloRangeView)this.findViewById(2131297198);
        this._introduction = (TextView)this.findViewById(2131297220);
        this._goals = (TextView)this.findViewById(2131297219);
    }

    public View getViewPriceButton() {
        return this._price;
    }

    public void setVideoSeries(VideoSeries videoSeries) {
        if (videoSeries.getTeaserCouchId() != null) {
            this._teaserImage.setCouchId(videoSeries.getTeaserCouchId());
        }
        TextView textView = this._numberOfEpisodes;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getContext().getResources().getString(2131690401));
        stringBuilder.append(" ");
        stringBuilder.append(videoSeries.getVideoInformationList().size());
        textView.setText((CharSequence)stringBuilder.toString());
        this._duration.setText((CharSequence)TimeHelper.getDurationString(videoSeries.getDurationMillis()));
        this._title.setText((CharSequence)videoSeries.getTitle());
        this._authorAndElo.setAuthor(videoSeries.getAuthorWithTitle());
        this._authorAndElo.setEloRange(videoSeries.getEloRange());
        this._introduction.setText((CharSequence)videoSeries.getDescription());
        this._goals.setText((CharSequence)videoSeries.getGoals());
        this._flag.setImageResource(videoSeries.getLanguage().getImageResId());
        if (videoSeries.isAccessible()) {
            this._price.setVisibility(4);
        }
    }

    public void setViewPriceText(String string) {
        this._price.setText((CharSequence)string);
    }
}
