/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package de.cisha.android.board.broadcast.video;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.cisha.android.board.broadcast.TournamentSubFragment;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import de.cisha.android.board.broadcast.video.VideoStreamFragment;
import de.cisha.chess.model.Country;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TournamentVideoFragment
extends TournamentSubFragment {
    public static final String TAGNAME = "TournamentVideo";
    private TournamentVideoInformation _currentVideo;
    private Map<TournamentVideoInformation, View> _languageButtons = new HashMap<TournamentVideoInformation, View>();
    private ViewGroup _languageSelection;
    private ViewGroup _mainView;
    private View _videoContainerView;
    private List<TournamentVideoInformation> _videos;

    private void deselectLanguageButtons() {
        Iterator<View> iterator = this._languageButtons.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setSelected(false);
        }
    }

    private void selectVideo(TournamentVideoInformation object) {
        if (this._currentVideo != object) {
            this._currentVideo = object;
            this.deselectLanguageButtons();
            Object object2 = this._languageButtons.get(object);
            if (object2 != null) {
                object2.setSelected(true);
            }
            object = VideoStreamFragment.createInstanceForStreamWithURI(object.getUri().toString(), true, true);
            object2 = this.getFragmentManager().findFragmentById(2131296411);
            FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
            if (object2 != null) {
                fragmentTransaction.remove((Fragment)object2);
            }
            fragmentTransaction.add(2131296411, (Fragment)object);
            fragmentTransaction.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater object, ViewGroup object22, Bundle bundle) {
        this._mainView = (ViewGroup)object.inflate(2131427396, (ViewGroup)object22, false);
        this._videoContainerView = this._mainView.findViewById(2131296411);
        this._videoContainerView.setVisibility(8);
        this._mainView.findViewById(2131296410).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        this._languageSelection = (ViewGroup)this._mainView.findViewById(2131296414);
        if (this.getTournamentHolder().hasTournament()) {
            this._videos = this.getTournamentHolder().getTournament().getVideos();
            if (this._videos == null) {
                this._videos = new LinkedList<TournamentVideoInformation>();
            }
            if (this._videos.size() > 0) {
                for (final TournamentVideoInformation tournamentVideoInformation : this._videos) {
                    Country country = tournamentVideoInformation.getLanguage();
                    ImageView imageView = new ImageView((Context)this.getActivity());
                    imageView.setBackgroundResource(2131230957);
                    int n = 2131231366;
                    if (country != null) {
                        n = country.getImageId();
                    }
                    imageView.setImageResource(n);
                    imageView.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            TournamentVideoFragment.this.selectVideo(tournamentVideoInformation);
                        }
                    });
                    n = (int)(32.0f * this.getActivity().getResources().getDisplayMetrics().density);
                    country = new LinearLayout.LayoutParams(n, n, 1.0f);
                    this._languageSelection.addView((View)imageView, (ViewGroup.LayoutParams)country);
                    this._languageButtons.put(tournamentVideoInformation, (View)imageView);
                }
                this.selectVideo(this._videos.get(0));
            }
            if (this._videos.size() == 1) {
                this._languageSelection.setVisibility(8);
            }
        }
        return this._mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getView().post(new Runnable(){

            @Override
            public void run() {
                TournamentVideoFragment.this._videoContainerView.setVisibility(0);
            }
        });
    }

}
