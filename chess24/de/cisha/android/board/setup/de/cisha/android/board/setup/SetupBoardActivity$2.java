/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.setup;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.setup.view.DragBoardView;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.validator.IPositionValidator;
import java.util.List;

class SetupBoardActivity
implements View.OnClickListener {
    SetupBoardActivity() {
    }

    public void onClick(View object) {
        if (SetupBoardActivity.this._isValidPosition) {
            object = SetupBoardActivity.this._boardView.getPosition().getFEN();
            Intent intent = new Intent();
            intent.putExtra(de.cisha.android.board.setup.SetupBoardActivity.CURRENT_BOARD_FEN_STRING, object.getFENString());
            SetupBoardActivity.this.setResult(-1, intent);
            SetupBoardActivity.this.finish();
            return;
        }
        SimpleOkDialogFragment simpleOkDialogFragment = new SimpleOkDialogFragment();
        simpleOkDialogFragment.setTitle(SetupBoardActivity.this.getString(2131690291));
        object = "";
        List<String> list = SetupBoardActivity.this._positionValidator.getErrorMessages();
        for (int i = 0; i < list.size(); ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(list.get(i));
            object = i < list.size() - 1 ? "\n\n" : "";
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
        }
        simpleOkDialogFragment.setText((CharSequence)object);
        simpleOkDialogFragment.show(SetupBoardActivity.this.getSupportFragmentManager(), "");
    }
}
