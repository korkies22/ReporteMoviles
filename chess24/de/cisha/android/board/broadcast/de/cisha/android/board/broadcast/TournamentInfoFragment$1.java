/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.Html
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast;

import android.text.Html;
import android.widget.TextView;
import de.cisha.android.view.WebImageView;
import java.net.URL;

class TournamentInfoFragment
implements Runnable {
    TournamentInfoFragment() {
    }

    @Override
    public void run() {
        TournamentInfoFragment.this._currentRoundTextView.setText((CharSequence)TournamentInfoFragment.this._currentRoundString);
        TextView textView = TournamentInfoFragment.this._ongoingGamesTextView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TournamentInfoFragment.this._numberOfOngoingGames);
        stringBuilder.append("");
        textView.setText((CharSequence)stringBuilder.toString());
        textView = TournamentInfoFragment.this._finishedGamesTextView;
        stringBuilder = new StringBuilder();
        stringBuilder.append(TournamentInfoFragment.this._numberOfFinishedGames);
        stringBuilder.append("");
        textView.setText((CharSequence)stringBuilder.toString());
        TournamentInfoFragment.this._descriptionTextView.setText((CharSequence)Html.fromHtml((String)TournamentInfoFragment.this._descriptionString));
        TournamentInfoFragment.this._logoView.setImageWebUrl(TournamentInfoFragment.this._logoUrl);
        TournamentInfoFragment.this._titleTextView.setText((CharSequence)TournamentInfoFragment.this._titleText);
    }
}
