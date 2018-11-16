// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database.service;

import android.provider.BaseColumns;

public final class GameDbContract
{
    public abstract static class Game implements BaseColumns
    {
        public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_EDIT_DATE = "edit_date";
        public static final String COLUMN_NAME_EVENT = "event";
        public static final String COLUMN_NAME_GAMEID = "game_id";
        public static final String COLUMN_NAME_JSON_GAME = "jsongame";
        public static final String COLUMN_NAME_ORIGIN_GAMEID = "origin_game_id";
        public static final String COLUMN_NAME_ORIGIN_TYPE = "origin_game_type";
        public static final String COLUMN_NAME_PLAYER_BLACK = "player_black";
        public static final String COLUMN_NAME_PLAYER_BLACK_ELO = "player_black_elo";
        public static final String COLUMN_NAME_PLAYER_WHITE = "player_white";
        public static final String COLUMN_NAME_PLAYER_WHITE_ELO = "player_white_elo";
        public static final String COLUMN_NAME_RESULT = "result";
        public static final String COLUMN_NAME_SITE = "site";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String TABLE_NAME = "game";
    }
}
