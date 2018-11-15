/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package de.cisha.android.board.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BoardThemeChooserController {
    private boolean _isLockOn;
    private View _lockView;
    private TwoSidedHashMap<View, View> _mapBoardToPiece;
    private Map<View, SettingsService.BoardTheme> _mapViewToTheme;
    private SettingsService.BoardTheme _selectedBoardTheme = this._settingsService.getBoardType();
    private SettingsService.BoardTheme _selectedPieceType = this._settingsService.getPiecesType();
    private SettingsService _settingsService = ServiceProvider.getInstance().getSettingsService();
    private View _view;

    public BoardThemeChooserController(Context context) {
        boolean bl = this._selectedBoardTheme == this._selectedPieceType;
        this._isLockOn = bl;
        this.initView(context);
    }

    private void addBoardPieceThemeView(int n, int n2, SettingsService.BoardTheme boardTheme) {
        View view = this._view.findViewById(n);
        View view2 = this._view.findViewById(n2);
        this._mapBoardToPiece.put(view, view2);
        this._mapViewToTheme.put(view, boardTheme);
        this._mapViewToTheme.put(view2, boardTheme);
        if (this._selectedBoardTheme == boardTheme) {
            view.setSelected(true);
        }
        if (this._selectedPieceType == boardTheme) {
            view2.setSelected(true);
        }
    }

    private void initView(Context object) {
        this._view = LayoutInflater.from((Context)object).inflate(2131427535, null);
        this._lockView = this._view.findViewById(2131296967);
        this._lockView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                BoardThemeChooserController.this.toggleLock();
            }
        });
        this._lockView.setSelected(this.isLockOn());
        this._mapBoardToPiece = new TwoSidedHashMap();
        this._mapViewToTheme = new HashMap<View, SettingsService.BoardTheme>();
        this.addBoardPieceThemeView(2131296962, 2131296965, SettingsService.BoardTheme.SILVER1);
        this.addBoardPieceThemeView(2131296963, 2131296966, SettingsService.BoardTheme.WOOD1);
        this.addBoardPieceThemeView(2131296961, 2131296964, SettingsService.BoardTheme.BLUE1);
        for (final View view : this._mapBoardToPiece.keySet()) {
            view.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view2) {
                    BoardThemeChooserController.this.selectBoardView(view);
                    if (BoardThemeChooserController.this.isLockOn()) {
                        BoardThemeChooserController.this.selectPieceView((View)BoardThemeChooserController.this._mapBoardToPiece.get((Object)view));
                    }
                }
            });
        }
        for (final View view : this._mapBoardToPiece.values()) {
            view.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view2) {
                    BoardThemeChooserController.this.selectPieceView(view);
                    if (BoardThemeChooserController.this.isLockOn()) {
                        BoardThemeChooserController.this.selectBoardView((View)BoardThemeChooserController.this._mapBoardToPiece.getKeyForValue(view));
                    }
                }
            });
        }
    }

    private void selectBoardView(View view) {
        for (Object object : this._mapBoardToPiece.keySet()) {
            boolean bl = object == view;
            object.setSelected(bl);
            if (!bl || (object = this._mapViewToTheme.get(object)) == null) continue;
            this._settingsService.setBoardType((SettingsService.BoardTheme)((Object)object));
            this._selectedPieceType = object;
        }
    }

    private void selectPieceView(View view) {
        for (Object object : this._mapBoardToPiece.values()) {
            boolean bl = object == view;
            object.setSelected(bl);
            if (!bl || (object = this._mapViewToTheme.get(object)) == null) continue;
            this._settingsService.setPiecesTypeName((SettingsService.BoardTheme)((Object)object));
            this._selectedBoardTheme = object;
        }
    }

    public View getView() {
        return this._view;
    }

    public boolean isLockOn() {
        return this._isLockOn;
    }

    public void toggleLock() {
        this._isLockOn ^= true;
        for (View view : this._mapBoardToPiece.keySet()) {
            if (!view.isSelected()) continue;
            this.selectPieceView(this._mapBoardToPiece.get((Object)view));
            break;
        }
        this._lockView.setSelected(this._isLockOn);
    }

}
