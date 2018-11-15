/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoLanguage;
import java.util.Set;
import java.util.TreeSet;

public static class VideoFilter.Builder {
    private Set<VideoLanguage> _languages = new TreeSet<VideoLanguage>();
    private int _maxLevel = Integer.MAX_VALUE;
    private int _minLevel = 0;
    private boolean _onlyPurchasedItems = false;
    private String _searchString;

    static /* synthetic */ Set access$000(VideoFilter.Builder builder) {
        return builder._languages;
    }

    static /* synthetic */ int access$100(VideoFilter.Builder builder) {
        return builder._maxLevel;
    }

    static /* synthetic */ int access$200(VideoFilter.Builder builder) {
        return builder._minLevel;
    }

    static /* synthetic */ String access$300(VideoFilter.Builder builder) {
        return builder._searchString;
    }

    static /* synthetic */ boolean access$400(VideoFilter.Builder builder) {
        return builder._onlyPurchasedItems;
    }

    public VideoFilter.Builder addLanguage(VideoLanguage videoLanguage) {
        this._languages.add(videoLanguage);
        return this;
    }

    public VideoFilter build() {
        return new VideoFilter(this, null);
    }

    public VideoFilter.Builder setMaximumLevel(int n) {
        this._maxLevel = n;
        return this;
    }

    public VideoFilter.Builder setMinimumLevel(int n) {
        this._minLevel = n;
        return this;
    }

    public VideoFilter.Builder setOnlyPurchasedItems() {
        this._onlyPurchasedItems = true;
        return this;
    }

    public VideoFilter.Builder setSearchString(String string) {
        this._searchString = string;
        return this;
    }
}
