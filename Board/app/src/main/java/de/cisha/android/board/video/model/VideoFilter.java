// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

public class VideoFilter
{
    private Set<VideoLanguage> _languages;
    private int _maxLevel;
    private int _minLevel;
    private boolean _onlyPurchasedItems;
    private String _searchString;
    
    private VideoFilter(final Builder builder) {
        this._languages = builder._languages;
        this._maxLevel = builder._maxLevel;
        this._minLevel = builder._minLevel;
        this._searchString = builder._searchString;
        this._onlyPurchasedItems = builder._onlyPurchasedItems;
    }
    
    public Map<String, String> createParams() {
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        if (this._onlyPurchasedItems) {
            treeMap.put("purchased", "1");
        }
        if (this._searchString != null && !this._searchString.isEmpty()) {
            treeMap.put("filter", this._searchString);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(this._minLevel);
        sb.append("");
        treeMap.put("elo_min", sb.toString());
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(this._maxLevel);
        sb2.append("");
        treeMap.put("elo_max", sb2.toString());
        String s = "";
        final Set<VideoLanguage> languages = this._languages;
        int i = 0;
        for (VideoLanguage[] array = languages.toArray(new VideoLanguage[0]); i < array.length; ++i) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(s);
            sb3.append(array[i].getIso2Code());
            final String s2 = s = sb3.toString();
            if (i < array.length - 1) {
                final StringBuilder sb4 = new StringBuilder();
                sb4.append(s2);
                sb4.append(",");
                s = sb4.toString();
            }
        }
        if (!s.isEmpty()) {
            treeMap.put("languages", s);
        }
        return treeMap;
    }
    
    public static class Builder
    {
        private Set<VideoLanguage> _languages;
        private int _maxLevel;
        private int _minLevel;
        private boolean _onlyPurchasedItems;
        private String _searchString;
        
        public Builder() {
            this._onlyPurchasedItems = false;
            this._minLevel = 0;
            this._maxLevel = Integer.MAX_VALUE;
            this._languages = new TreeSet<VideoLanguage>();
        }
        
        public Builder addLanguage(final VideoLanguage videoLanguage) {
            this._languages.add(videoLanguage);
            return this;
        }
        
        public VideoFilter build() {
            return new VideoFilter(this, null);
        }
        
        public Builder setMaximumLevel(final int maxLevel) {
            this._maxLevel = maxLevel;
            return this;
        }
        
        public Builder setMinimumLevel(final int minLevel) {
            this._minLevel = minLevel;
            return this;
        }
        
        public Builder setOnlyPurchasedItems() {
            this._onlyPurchasedItems = true;
            return this;
        }
        
        public Builder setSearchString(final String searchString) {
            this._searchString = searchString;
            return this;
        }
    }
}
