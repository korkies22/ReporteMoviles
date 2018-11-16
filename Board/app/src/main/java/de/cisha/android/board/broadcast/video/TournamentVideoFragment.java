// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.video;

import de.cisha.chess.model.Country;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.content.Context;
import android.widget.ImageView;
import java.util.LinkedList;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import android.view.ViewGroup;
import android.view.View;
import java.util.Map;
import de.cisha.android.board.broadcast.TournamentSubFragment;

public class TournamentVideoFragment extends TournamentSubFragment
{
    public static final String TAGNAME = "TournamentVideo";
    private TournamentVideoInformation _currentVideo;
    private Map<TournamentVideoInformation, View> _languageButtons;
    private ViewGroup _languageSelection;
    private ViewGroup _mainView;
    private View _videoContainerView;
    private List<TournamentVideoInformation> _videos;
    
    public TournamentVideoFragment() {
        this._languageButtons = new HashMap<TournamentVideoInformation, View>();
    }
    
    private void deselectLanguageButtons() {
        final Iterator<View> iterator = this._languageButtons.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setSelected(false);
        }
    }
    
    private void selectVideo(final TournamentVideoInformation currentVideo) {
        if (this._currentVideo != currentVideo) {
            this._currentVideo = currentVideo;
            this.deselectLanguageButtons();
            final View view = this._languageButtons.get(currentVideo);
            if (view != null) {
                view.setSelected(true);
            }
            final VideoStreamFragment instanceForStreamWithURI = VideoStreamFragment.createInstanceForStreamWithURI(currentVideo.getUri().toString(), true, true);
            final Fragment fragmentById = this.getFragmentManager().findFragmentById(2131296411);
            final FragmentTransaction beginTransaction = this.getFragmentManager().beginTransaction();
            if (fragmentById != null) {
                beginTransaction.remove(fragmentById);
            }
            beginTransaction.add(2131296411, instanceForStreamWithURI);
            beginTransaction.commit();
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        this._mainView = (ViewGroup)layoutInflater.inflate(2131427396, viewGroup, false);
        (this._videoContainerView = this._mainView.findViewById(2131296411)).setVisibility(8);
        this._mainView.findViewById(2131296410).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
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
                    final Country language = tournamentVideoInformation.getLanguage();
                    final ImageView imageView = new ImageView((Context)this.getActivity());
                    imageView.setBackgroundResource(2131230957);
                    int imageId = 2131231366;
                    if (language != null) {
                        imageId = language.getImageId();
                    }
                    imageView.setImageResource(imageId);
                    imageView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            TournamentVideoFragment.this.selectVideo(tournamentVideoInformation);
                        }
                    });
                    final int n = (int)(32.0f * this.getActivity().getResources().getDisplayMetrics().density);
                    this._languageSelection.addView((View)imageView, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(n, n, 1.0f));
                    this._languageButtons.put(tournamentVideoInformation, (View)imageView);
                }
                this.selectVideo(this._videos.get(0));
            }
            if (this._videos.size() == 1) {
                this._languageSelection.setVisibility(8);
            }
        }
        return (View)this._mainView;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        this.getView().post((Runnable)new Runnable() {
            @Override
            public void run() {
                TournamentVideoFragment.this._videoContainerView.setVisibility(0);
            }
        });
    }
}
