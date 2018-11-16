// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import de.cisha.android.board.util.TimeHelper;
import de.cisha.android.board.video.model.VideoSeries;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.view.CouchImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class VideoSeriesHeaderView extends LinearLayout
{
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
    
    public VideoSeriesHeaderView(final Context context) {
        super(context);
        this.initialize(context);
    }
    
    public VideoSeriesHeaderView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initialize(context);
    }
    
    private void initialize(final Context context) {
        final LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        this.setOrientation(1);
        layoutInflater.inflate(2131427580, (ViewGroup)this, true);
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
        return (View)this._price;
    }
    
    public void setVideoSeries(final VideoSeries videoSeries) {
        if (videoSeries.getTeaserCouchId() != null) {
            this._teaserImage.setCouchId(videoSeries.getTeaserCouchId());
        }
        final TextView numberOfEpisodes = this._numberOfEpisodes;
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getContext().getResources().getString(2131690401));
        sb.append(" ");
        sb.append(videoSeries.getVideoInformationList().size());
        numberOfEpisodes.setText((CharSequence)sb.toString());
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
    
    public void setViewPriceText(final String text) {
        this._price.setText((CharSequence)text);
    }
}
