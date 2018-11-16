// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.ViewGroup;
import android.text.Html;
import java.text.SimpleDateFormat;
import de.cisha.android.board.broadcast.model.TournamentState;
import android.text.format.DateFormat;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.view.WebImageView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class TournamentInfoView extends LinearLayout
{
    private static final long TIME_DAY = 86400000L;
    private static final long TIME_HOUR = 3600000L;
    private static final long TIME_MINUTE = 60000L;
    private TextView _detailText;
    private ImageView _liveImage;
    private TextView _title;
    private WebImageView _tournamentImage;
    
    public TournamentInfoView(final Context context) {
        super(context);
        this.init();
    }
    
    public TournamentInfoView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    public static CharSequence getInfoSubtext(final TournamentInfo tournamentInfo, final Context context) {
        final java.text.DateFormat mediumDateFormat = DateFormat.getMediumDateFormat(context);
        final StringBuffer sb = new StringBuffer();
        if (tournamentInfo.getState() == TournamentState.ONGOING) {
            sb.append(context.getString(2131690275));
            sb.append(" <b>");
            sb.append(tournamentInfo.getCurrentRound().getRoundString());
            sb.append("</b> ");
            sb.append(context.getString(2131690096));
            sb.append(" ");
            sb.append(tournamentInfo.getNumberOfRounds());
            sb.append(" ");
            sb.append(" | ");
            sb.append(context.getString(2131690013));
            sb.append(" <b>");
            sb.append(tournamentInfo.getNumberOfOngoingGames());
            sb.append("</b> | ");
            sb.append(context.getString(2131689985));
            sb.append(" <b>");
            sb.append(tournamentInfo.getNumberOfFinishedGames());
            sb.append("</b>");
        }
        else if (tournamentInfo.getState() == TournamentState.UPCOMING) {
            final String format = new SimpleDateFormat(context.getString(2131690343), context.getResources().getConfiguration().locale).format(tournamentInfo.getStartDate());
            sb.append(context.getString(2131690299));
            sb.append(" <b>");
            sb.append(format);
            sb.append("</b>");
        }
        else if (tournamentInfo.getState() == TournamentState.PAUSED) {
            final String format2 = new SimpleDateFormat(context.getString(2131690343), context.getResources().getConfiguration().locale).format(tournamentInfo.getStartDate());
            sb.append(context.getString(2131690275));
            sb.append(" <b>");
            sb.append(tournamentInfo.getCurrentRound().getRoundString());
            sb.append("</b> ");
            sb.append(context.getString(2131690096));
            sb.append(" ");
            sb.append(tournamentInfo.getNumberOfRounds());
            sb.append(" ");
            sb.append(" | ");
            sb.append(context.getString(2131690094));
            sb.append(" <b>");
            sb.append(format2);
            sb.append("</b> | ");
            sb.append(context.getString(2131689985));
            sb.append(" <b>");
            sb.append(tournamentInfo.getNumberOfFinishedGames());
            sb.append("</b>");
        }
        else {
            sb.append(context.getString(2131689986));
            sb.append(" <b>");
            sb.append(mediumDateFormat.format(tournamentInfo.getStartDate()));
            sb.append("</b>");
        }
        return (CharSequence)Html.fromHtml(sb.toString());
    }
    
    private void init() {
        this.setHardwareLayerType();
        this.setBackgroundResource(2131230991);
        this.setOrientation(1);
        inflate(this.getContext(), 2131427389, (ViewGroup)this);
        this._title = (TextView)this.findViewById(2131297177);
        this._detailText = (TextView)this.findViewById(2131297174);
        this._tournamentImage = (WebImageView)this.findViewById(2131297175);
        (this._liveImage = (ImageView)this.findViewById(2131297176)).setVisibility(8);
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, (Paint)null);
        }
    }
    
    private void setLive(final boolean b, final boolean b2) {
        int imageResource;
        if (b2) {
            imageResource = 2131230992;
        }
        else {
            imageResource = 2131230993;
        }
        this._liveImage.setImageResource(imageResource);
        final ImageView liveImage = this._liveImage;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        liveImage.setVisibility(visibility);
    }
    
    private void setTitle(final String text) {
        this._title.setText((CharSequence)text);
    }
    
    private void setTournamentImageUrl(final String imageWebUrlString) {
        this._tournamentImage.setImageWebUrlString(imageWebUrlString);
    }
    
    public void setTournamentInfo(final TournamentInfo tournamentInfo) {
        this._detailText.setText(getInfoSubtext(tournamentInfo, this.getContext()));
        this.setTitle(tournamentInfo.getTitle());
        this.setLive(tournamentInfo.getNumberOfOngoingGames() > 0, tournamentInfo.hasLiveVideo());
        String externalForm;
        if (tournamentInfo.getIconImageURL() == null) {
            externalForm = "";
        }
        else {
            externalForm = tournamentInfo.getIconImageURL().toExternalForm();
        }
        this.setTournamentImageUrl(externalForm);
    }
}
