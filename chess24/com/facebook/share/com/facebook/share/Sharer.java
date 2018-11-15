/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share;

public interface Sharer {
    public boolean getShouldFailOnDataError();

    public void setShouldFailOnDataError(boolean var1);

    public static class Result {
        final String postId;

        public Result(String string) {
            this.postId = string;
        }

        public String getPostId() {
            return this.postId;
        }
    }

}
