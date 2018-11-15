/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.android.board.video.model.VideoLanguage;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class VideoFilter {
    private Set<VideoLanguage> _languages;
    private int _maxLevel;
    private int _minLevel;
    private boolean _onlyPurchasedItems;
    private String _searchString;

    private VideoFilter(Builder builder) {
        this._languages = builder._languages;
        this._maxLevel = builder._maxLevel;
        this._minLevel = builder._minLevel;
        this._searchString = builder._searchString;
        this._onlyPurchasedItems = builder._onlyPurchasedItems;
    }

    public Map<String, String> createParams() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        if (this._onlyPurchasedItems) {
            treeMap.put("purchased", "1");
        }
        if (this._searchString != null && !this._searchString.isEmpty()) {
            treeMap.put("filter", this._searchString);
        }
        Object object = new StringBuilder();
        object.append(this._minLevel);
        object.append("");
        treeMap.put("elo_min", object.toString());
        object = new StringBuilder();
        object.append(this._maxLevel);
        object.append("");
        treeMap.put("elo_max", object.toString());
        object = "";
        Object object2 = this._languages;
        VideoLanguage[] arrvideoLanguage = object2.toArray((T[])new VideoLanguage[0]);
        for (int i = 0; i < arrvideoLanguage.length; ++i) {
            object2 = new StringBuilder();
            object2.append((String)object);
            object2.append(arrvideoLanguage[i].getIso2Code());
            object = object2 = object2.toString();
            if (i >= arrvideoLanguage.length - 1) continue;
            object = new StringBuilder();
            object.append((String)object2);
            object.append(",");
            object = object.toString();
        }
        if (!object.isEmpty()) {
            treeMap.put("languages", (String)object);
        }
        return treeMap;
    }

    public static class Builder {
        private Set<VideoLanguage> _languages = new TreeSet<VideoLanguage>();
        private int _maxLevel = Integer.MAX_VALUE;
        private int _minLevel = 0;
        private boolean _onlyPurchasedItems = false;
        private String _searchString;

        public Builder addLanguage(VideoLanguage videoLanguage) {
            this._languages.add(videoLanguage);
            return this;
        }

        public VideoFilter build() {
            return new VideoFilter(this);
        }

        public Builder setMaximumLevel(int n) {
            this._maxLevel = n;
            return this;
        }

        public Builder setMinimumLevel(int n) {
            this._minLevel = n;
            return this;
        }

        public Builder setOnlyPurchasedItems() {
            this._onlyPurchasedItems = true;
            return this;
        }

        public Builder setSearchString(String string) {
            this._searchString = string;
            return this;
        }
    }

}
