// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import java.util.Iterator;
import java.util.HashMap;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.service.ServiceProvider;
import android.content.Context;
import de.cisha.android.board.service.SettingsService;
import java.util.Map;
import de.cisha.android.board.TwoSidedHashMap;
import android.view.View;

public class BoardThemeChooserController
{
    private boolean _isLockOn;
    private View _lockView;
    private TwoSidedHashMap<View, View> _mapBoardToPiece;
    private Map<View, SettingsService.BoardTheme> _mapViewToTheme;
    private SettingsService.BoardTheme _selectedBoardTheme;
    private SettingsService.BoardTheme _selectedPieceType;
    private SettingsService _settingsService;
    private View _view;
    
    public BoardThemeChooserController(final Context context) {
        this._settingsService = ServiceProvider.getInstance().getSettingsService();
        this._selectedBoardTheme = this._settingsService.getBoardType();
        this._selectedPieceType = this._settingsService.getPiecesType();
        this._isLockOn = (this._selectedBoardTheme == this._selectedPieceType);
        this.initView(context);
    }
    
    private void addBoardPieceThemeView(final int n, final int n2, final SettingsService.BoardTheme boardTheme) {
        final View viewById = this._view.findViewById(n);
        final View viewById2 = this._view.findViewById(n2);
        this._mapBoardToPiece.put(viewById, viewById2);
        this._mapViewToTheme.put(viewById, boardTheme);
        this._mapViewToTheme.put(viewById2, boardTheme);
        if (this._selectedBoardTheme == boardTheme) {
            viewById.setSelected(true);
        }
        if (this._selectedPieceType == boardTheme) {
            viewById2.setSelected(true);
        }
    }
    
    private void initView(final Context context) {
        this._view = LayoutInflater.from(context).inflate(2131427535, (ViewGroup)null);
        (this._lockView = this._view.findViewById(2131296967)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                BoardThemeChooserController.this.toggleLock();
            }
        });
        this._lockView.setSelected(this.isLockOn());
        this._mapBoardToPiece = new TwoSidedHashMap<View, View>();
        this._mapViewToTheme = new HashMap<View, SettingsService.BoardTheme>();
        this.addBoardPieceThemeView(2131296962, 2131296965, SettingsService.BoardTheme.SILVER1);
        this.addBoardPieceThemeView(2131296963, 2131296966, SettingsService.BoardTheme.WOOD1);
        this.addBoardPieceThemeView(2131296961, 2131296964, SettingsService.BoardTheme.BLUE1);
        for (final View view : this._mapBoardToPiece.keySet()) {
            view.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    BoardThemeChooserController.this.selectBoardView(view);
                    if (BoardThemeChooserController.this.isLockOn()) {
                        BoardThemeChooserController.this.selectPieceView(BoardThemeChooserController.this._mapBoardToPiece.get(view));
                    }
                }
            });
        }
        for (final View view2 : this._mapBoardToPiece.values()) {
            view2.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    BoardThemeChooserController.this.selectPieceView(view2);
                    if (BoardThemeChooserController.this.isLockOn()) {
                        BoardThemeChooserController.this.selectBoardView(BoardThemeChooserController.this._mapBoardToPiece.getKeyForValue(view2));
                    }
                }
            });
        }
    }
    
    private void selectBoardView(final View view) {
        for (final View view2 : this._mapBoardToPiece.keySet()) {
            final boolean selected = view2 == view;
            view2.setSelected(selected);
            if (selected) {
                final SettingsService.BoardTheme boardTheme = this._mapViewToTheme.get(view2);
                if (boardTheme == null) {
                    continue;
                }
                this._settingsService.setBoardType(boardTheme);
                this._selectedPieceType = boardTheme;
            }
        }
    }
    
    private void selectPieceView(final View view) {
        for (final View view2 : this._mapBoardToPiece.values()) {
            final boolean selected = view2 == view;
            view2.setSelected(selected);
            if (selected) {
                final SettingsService.BoardTheme boardTheme = this._mapViewToTheme.get(view2);
                if (boardTheme == null) {
                    continue;
                }
                this._settingsService.setPiecesTypeName(boardTheme);
                this._selectedBoardTheme = boardTheme;
            }
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
        for (final View view : this._mapBoardToPiece.keySet()) {
            if (view.isSelected()) {
                this.selectPieceView(this._mapBoardToPiece.get(view));
                break;
            }
        }
        this._lockView.setSelected(this._isLockOn);
    }
}
