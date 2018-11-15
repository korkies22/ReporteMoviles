/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        ProfileEditorFragment.this.loadProfileData();
        ProfileEditorFragment.this.loadPrivacySettings();
    }
}
