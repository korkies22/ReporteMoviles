/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Content;
import de.cisha.chess.model.ContentId;

public abstract class AbstractContent
implements Content {
    private ContentId _id;

    public AbstractContent(ContentId contentId) {
        this._id = contentId;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Content;
        boolean bl2 = false;
        if (bl) {
            if (this._id != null) {
                bl2 = this._id.equals(((Content)object).getContentId());
            }
            return bl2;
        }
        return false;
    }

    @Override
    public ContentId getContentId() {
        return this._id;
    }

    public int hashCode() {
        if (this._id != null) {
            return this._id.hashCode();
        }
        return 0;
    }
}
