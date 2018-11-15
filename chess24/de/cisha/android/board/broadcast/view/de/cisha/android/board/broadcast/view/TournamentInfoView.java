/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Html
 *  android.text.format.DateFormat
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.view.WebImageView;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TournamentInfoView
extends LinearLayout {
    private static final long TIME_DAY = 86400000L;
    private static final long TIME_HOUR = 3600000L;
    private static final long TIME_MINUTE = 60000L;
    private TextView _detailText;
    private ImageView _liveImage;
    private TextView _title;
    private WebImageView _tournamentImage;

    public TournamentInfoView(Context context) {
        super(context);
        this.init();
    }

    public TournamentInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public static CharSequence getInfoSubtext(TournamentInfo object, Context context) {
        Object object2 = DateFormat.getMediumDateFormat((Context)context);
        StringBuffer stringBuffer = new StringBuffer();
        if (object.getState() == TournamentState.ONGOING) {
            stringBuffer.append(context.getString(2131690275));
            stringBuffer.append(" <b>");
            stringBuffer.append(object.getCurrentRound().getRoundString());
            stringBuffer.append("</b> ");
            stringBuffer.append(context.getString(2131690096));
            stringBuffer.append(" ");
            stringBuffer.append(object.getNumberOfRounds());
            stringBuffer.append(" ");
            stringBuffer.append(" | ");
            stringBuffer.append(context.getString(2131690013));
            stringBuffer.append(" <b>");
            stringBuffer.append(object.getNumberOfOngoingGames());
            stringBuffer.append("</b> | ");
            stringBuffer.append(context.getString(2131689985));
            stringBuffer.append(" <b>");
            stringBuffer.append(object.getNumberOfFinishedGames());
            stringBuffer.append("</b>");
        } else if (object.getState() == TournamentState.UPCOMING) {
            object = new SimpleDateFormat(context.getString(2131690343), context.getResources().getConfiguration().locale).format(object.getStartDate());
            stringBuffer.append(context.getString(2131690299));
            stringBuffer.append(" <b>");
            stringBuffer.append((String)object);
            stringBuffer.append("</b>");
        } else if (object.getState() == TournamentState.PAUSED) {
            object2 = new SimpleDateFormat(context.getString(2131690343), context.getResources().getConfiguration().locale).format(object.getStartDate());
            stringBuffer.append(context.getString(2131690275));
            stringBuffer.append(" <b>");
            stringBuffer.append(object.getCurrentRound().getRoundString());
            stringBuffer.append("</b> ");
            stringBuffer.append(context.getString(2131690096));
            stringBuffer.append(" ");
            stringBuffer.append(object.getNumberOfRounds());
            stringBuffer.append(" ");
            stringBuffer.append(" | ");
            stringBuffer.append(context.getString(2131690094));
            stringBuffer.append(" <b>");
            stringBuffer.append((String)object2);
            stringBuffer.append("</b> | ");
            stringBuffer.append(context.getString(2131689985));
            stringBuffer.append(" <b>");
            stringBuffer.append(object.getNumberOfFinishedGames());
            stringBuffer.append("</b>");
        } else {
            stringBuffer.append(context.getString(2131689986));
            stringBuffer.append(" <b>");
            stringBuffer.append(object2.format(object.getStartDate()));
            stringBuffer.append("</b>");
        }
        return Html.fromHtml((String)stringBuffer.toString());
    }

    private void init() {
        this.setHardwareLayerType();
        this.setBackgroundResource(2131230991);
        this.setOrientation(1);
        TournamentInfoView.inflate((Context)this.getContext(), (int)2131427389, (ViewGroup)this);
        this._title = (TextView)this.findViewById(2131297177);
        this._detailText = (TextView)this.findViewById(2131297174);
        this._tournamentImage = (WebImageView)this.findViewById(2131297175);
        this._liveImage = (ImageView)this.findViewById(2131297176);
        this._liveImage.setVisibility(8);
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, null);
        }
    }

    private void setLive(boolean bl, boolean bl2) {
        int n = bl2 ? 2131230992 : 2131230993;
        this._liveImage.setImageResource(n);
        ImageView imageView = this._liveImage;
        n = bl ? 0 : 8;
        imageView.setVisibility(n);
    }

    private void setTitle(String string) {
        this._title.setText((CharSequence)string);
    }

    private void setTournamentImageUrl(String string) {
        this._tournamentImage.setImageWebUrlString(string);
    }

    public void setTournamentInfo(TournamentInfo object) {
        CharSequence charSequence = TournamentInfoView.getInfoSubtext((TournamentInfo)object, this.getContext());
        this._detailText.setText(charSequence);
        this.setTitle(object.getTitle());
        boolean bl = object.getNumberOfOngoingGames() > 0;
        this.setLive(bl, object.hasLiveVideo());
        object = object.getIconImageURL() == null ? "" : object.getIconImageURL().toExternalForm();
        this.setTournamentImageUrl((String)object);
    }
}
