/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import java.util.Comparator;

class TournamentTeam
implements Comparator<String> {
    TournamentTeam() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int compare(String string, String string2) {
        try {
            return Integer.valueOf(Integer.parseInt(string)).compareTo(Integer.parseInt(string2));
        }
        catch (NumberFormatException numberFormatException) {
            return string.compareTo(string2);
        }
    }
}
