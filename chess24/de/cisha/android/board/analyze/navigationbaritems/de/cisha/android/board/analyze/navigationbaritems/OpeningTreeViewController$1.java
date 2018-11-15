/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.android.board.analyze.view.OpeningTreeView;
import de.cisha.android.board.service.IOpeningTreeService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.position.Position;

class OpeningTreeViewController
implements OpeningTreeView.OpeningTreeViewSourceAdapter {
    OpeningTreeViewController() {
    }

    @Override
    public OpeningPositionInformation getInformation(Position position) {
        return ServiceProvider.getInstance().getOpeningTreeService().getInformationForPosition(position);
    }
}
