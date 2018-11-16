// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.graphics.Path.FillType;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Path;
import android.content.res.Resources;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.Queen;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.graphics.Color;
import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import android.view.MotionEvent;
import android.view.KeyEvent;
import java.util.Collection;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.media.MediaPlayer.OnCompletionListener;
import de.cisha.android.board.service.ServiceProvider;
import android.media.MediaPlayer;
import android.graphics.Rect;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import java.util.HashSet;
import android.os.Looper;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import java.util.Iterator;
import de.cisha.chess.model.SEP;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import de.cisha.chess.model.MoveContainer;
import android.view.animation.Animation.AnimationListener;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.PieceSetup;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.LinkedList;
import android.content.Context;
import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.PointF;
import de.cisha.chess.model.MoveExecutor;
import android.graphics.Paint;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.position.Position;
import java.util.List;
import android.graphics.drawable.Drawable;
import de.cisha.chess.model.Square;
import java.util.Set;
import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.service.SettingsService;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.android.board.StateHolder;

public class BoardView extends ScaleableGridLayout implements StateHolder, PositionObserver, BoardSettingObserver, BoardMarkingObserver
{
    private static final String BOARDVIEW_PERSPECTIVE = "BOARDVIEW_PERSPECTIVE";
    private static final int SOUND_CAPTURE = 2131623936;
    private static final int SOUND_MOVE = 2131623938;
    private Set<Square> _activePieceMoveSquares;
    private Square _activePieceSquare;
    private boolean _allowBlackMoves;
    private boolean _allowBlackPremoves;
    private boolean _allowWhiteMoves;
    private boolean _allowWhitePremoves;
    private MyAnimationListenerImageView _animatedMovingPiece;
    private ArrowView _arrowsLayer;
    Drawable _background;
    private BoardViewSettings _boardViewSettings;
    private List<Integer> _colors;
    private Position _currentPosition;
    private BoardMarks _externalBoardMarkings;
    private boolean _flagBoardSettingsChanged;
    private boolean _isPromotion;
    private float _lastPaintedSquareWidth;
    private BoardMarks _localMarkings;
    private int _markColorPremove;
    private int _markColorUsualMove;
    Paint _markerPaint;
    private MoveExecutor _moveExecutor;
    private PointF _movePoint;
    private Square _moveSquare;
    private List<MoveAnimationInformationHolder> _nextAnimations;
    private boolean _perspective;
    private Square _probableSquare;
    private PromotionPresenter _promotionPresenter;
    private Square _promotionSquare;
    private boolean _soundOn;
    private float _squareHeight;
    private float _squareWidth;
    private Bitmap _squaresBitmap;
    private Handler _uiThreadHandler;
    private boolean _zooming;
    
    protected BoardView(final Context context) {
        super(context, 8, 8);
        this._movePoint = null;
        this._nextAnimations = new LinkedList<MoveAnimationInformationHolder>();
        this._soundOn = false;
        this._perspective = true;
        this._isPromotion = false;
        this._lastPaintedSquareWidth = -100.0f;
        this._allowWhitePremoves = true;
        this._allowBlackPremoves = true;
        this._allowBlackMoves = true;
        this._allowWhiteMoves = true;
        this._markColorUsualMove = 1140850943;
        this._markColorPremove = 1140872533;
        this._markerPaint = new Paint();
        this._colors = new ArrayList<Integer>();
        this._context = context;
        this._currentPosition = new Position();
        this.init();
    }
    
    protected BoardView(final Context context, final AttributeSet set) {
        super(context, set, 8, 8);
        this._movePoint = null;
        this._nextAnimations = new LinkedList<MoveAnimationInformationHolder>();
        this._soundOn = false;
        this._perspective = true;
        this._isPromotion = false;
        this._lastPaintedSquareWidth = -100.0f;
        this._allowWhitePremoves = true;
        this._allowBlackPremoves = true;
        this._allowBlackMoves = true;
        this._allowWhiteMoves = true;
        this._markColorUsualMove = 1140850943;
        this._markColorPremove = 1140872533;
        this._markerPaint = new Paint();
        this._colors = new ArrayList<Integer>();
        this._context = context;
        this._currentPosition = new Position();
        this.init();
    }
    
    protected BoardView(final Context context, final Position currentPosition) {
        super(context, 8, 8);
        this._movePoint = null;
        this._nextAnimations = new LinkedList<MoveAnimationInformationHolder>();
        this._soundOn = false;
        this._perspective = true;
        this._isPromotion = false;
        this._lastPaintedSquareWidth = -100.0f;
        this._allowWhitePremoves = true;
        this._allowBlackPremoves = true;
        this._allowBlackMoves = true;
        this._allowWhiteMoves = true;
        this._markColorUsualMove = 1140850943;
        this._markColorPremove = 1140872533;
        this._markerPaint = new Paint();
        this._colors = new ArrayList<Integer>();
        this._context = context;
        this._currentPosition = currentPosition;
        this.init();
    }
    
    private void animateMove(final Move move, final int n, final Animation.AnimationListener animation.AnimationListener) {
        synchronized (this) {
            if (move.getResultingPosition().equals(this._currentPosition)) {
                final MoveContainer parent = move.getParent();
                if (parent != null) {
                    final Position resultingPosition = parent.getResultingPosition();
                    if (resultingPosition != null) {
                        this.stopMovement(resultingPosition);
                    }
                }
                if (move != null && n > 0) {
                    this._nextAnimations.add(new MoveAnimationInformationHolder(move, animation.AnimationListener, n));
                    if (this._animatedMovingPiece == null) {
                        this.startNextMovement();
                    }
                }
                else if (move != null) {
                    this.setBoardPosition(move.getResultingPosition());
                }
                this.invalidate();
            }
            else {
                this.setBoardPosition(move.getResultingPosition());
            }
        }
    }
    
    private void createBackgroundBitmap() {
        if (this._width > 0 && this._height > 0) {
            this._lastPaintedSquareWidth = this._squareWidth;
            this._squaresBitmap = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_4444);
            this.paintSquares(new Canvas(this._squaresBitmap));
            return;
        }
        this._lastPaintedSquareWidth = 1.0f;
        this._squaresBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
    }
    
    private Move createMove(final Square square, final Square square2, final int n) {
        return this._currentPosition.createMoveFrom(new SEP(square, square2, n));
    }
    
    private void doMove(final Move move) {
        if (move != null) {
            if (this._moveExecutor != null) {
                this._moveExecutor.doMoveInCurrentPosition(move.getSEP());
                return;
            }
            this.setPosition(move.getResultingPosition());
        }
    }
    
    private Drawable getDrawableForSquare(final Square square) {
        if (!this.isInEditMode()) {
            return this.getResources().getDrawable(this._boardViewSettings.getDrawableIdForSquare(square));
        }
        if (square.isWhite()) {
            return this.getResources().getDrawable(2131231911);
        }
        return this.getResources().getDrawable(2131230882);
    }
    
    private float getLowDistance(final float n, final float n2) {
        final Iterator<Square> iterator = this._activePieceMoveSquares.iterator();
        float n3 = Float.MAX_VALUE;
        while (iterator.hasNext()) {
            final Square square = iterator.next();
            final float n4 = (float)Math.sqrt(Math.pow(n - this.getSquareCenterX(square), 2.0) + Math.pow(n2 - this.getSquareCenterY(square), 2.0));
            if (n3 > n4) {
                n3 = n4;
            }
        }
        return n3;
    }
    
    private Drawable getPositionedDrawableForPiece(final PieceInformation pieceInformation) {
        final Drawable drawableForPiece = this.getDrawableForPiece(pieceInformation.getPiece());
        drawableForPiece.setBounds(this.getBounds(pieceInformation.getSquare().getColumn(), pieceInformation.getSquare().getRow()));
        return drawableForPiece;
    }
    
    private List<Square> getPossiblePremoveSquares(final Piece piece, final Position position, final Square square) {
        final ModifyablePosition modifyablePosition = new ModifyablePosition();
        modifyablePosition.setPiece(piece, square);
        final List<Square> allMoves = piece.getAllMoves(modifyablePosition.getSafePosition(), square);
        if (piece.equals(King.instanceForColor(true))) {
            if (square.equals(Square.SQUARE_E1)) {
                allMoves.add(Square.SQUARE_C1);
                allMoves.add(Square.SQUARE_G1);
                return allMoves;
            }
        }
        else if (piece.equals(King.instanceForColor(false))) {
            if (square.equals(Square.SQUARE_E8)) {
                allMoves.add(Square.SQUARE_C8);
                allMoves.add(Square.SQUARE_G8);
                return allMoves;
            }
        }
        else if (piece.equalsIgnoreColor(Pawn.instanceForColor(true))) {
            int n;
            if (piece.getColor()) {
                n = 1;
            }
            else {
                n = -1;
            }
            final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(square.getRow() + n, square.getColumn() + 1);
            if (instanceForRowAndColumn.isValid()) {
                allMoves.add(instanceForRowAndColumn);
            }
            final Square instanceForRowAndColumn2 = Square.instanceForRowAndColumn(square.getRow() + n, square.getColumn() - 1);
            if (instanceForRowAndColumn2.isValid()) {
                allMoves.add(instanceForRowAndColumn2);
            }
        }
        return allMoves;
    }
    
    private float getSquareWidth() {
        return this._squareWidth;
    }
    
    private Handler getUiThreadHandler() {
        if (this._uiThreadHandler == null) {
            this._uiThreadHandler = new Handler(Looper.getMainLooper());
        }
        return this._uiThreadHandler;
    }
    
    private void init() {
        this.setBoardViewSettings((BoardViewSettings)new BoardViewSettings() {
            private boolean _autoQueen = false;
            private int _moveTime = 300;
            private boolean _premoveEnabled = false;
            
            @Override
            public int getDrawableIdForPiece(final Piece piece) {
                return 2131231785;
            }
            
            @Override
            public int getDrawableIdForSquare(final Square square) {
                return 2131231936;
            }
            
            @Override
            public int getMoveTimeInMills() {
                return this._moveTime;
            }
            
            @Override
            public boolean isArrowOfLastMoveEnabled() {
                return true;
            }
            
            @Override
            public boolean isAutoQueenEnabled() {
                return this._autoQueen;
            }
            
            @Override
            public boolean isMarkMoveSquaresModeEnabled() {
                return true;
            }
            
            @Override
            public boolean isPlayingMoveSounds() {
                return false;
            }
            
            @Override
            public boolean isPremoveEnabled() {
                return this._premoveEnabled;
            }
            
            @Override
            public void setAutoQueenEnabled(final boolean autoQueen) {
                this._autoQueen = autoQueen;
            }
            
            @Override
            public void setMoveTimeInMills(final int moveTime) {
                this._moveTime = moveTime;
            }
            
            @Override
            public void setPremoveEnabled(final boolean premoveEnabled) {
                this._premoveEnabled = premoveEnabled;
            }
        });
        this._localMarkings = new BoardMarks();
        this.setBackgroundColor(-16777216);
        this._activePieceMoveSquares = new HashSet<Square>();
        this.createBackgroundBitmap();
        this.addView((View)(this._arrowsLayer = new ArrowView(this.getContext())), (ViewGroup.LayoutParams)new LayoutParams(0, 0, 8, 8));
        this.bringChildToFront(this._arrowsLayer);
        this.setWillNotDraw(false);
        if (this._currentPosition != null) {
            this.setBoardPosition(this._currentPosition);
        }
        this._promotionPresenter = (PromotionPresenter)new PromotionPresenter() {
            private View _promotionView;
            
            @Override
            public void dismissDialog() {
                BoardView.this.removeView(this._promotionView);
                BoardView.this.resetActivePiece();
            }
            
            @Override
            public boolean isPromotionShown() {
                return this._promotionView != null && this._promotionView.getParent() != null;
            }
            
            @Override
            public void showPromotionDialog(final View promotionView) {
                BoardView.this.requestFocus();
                (this._promotionView = promotionView).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                    }
                });
                BoardView.this.addView(promotionView, (ViewGroup.LayoutParams)new LayoutParams(0, 0, 8, 8));
            }
        };
        this.setFocusableInTouchMode(true);
    }
    
    private void paintSquares(final Canvas canvas) {
        final Square[] allSquares64 = Square.getAllSquares64();
        final boolean perspective = this.getPerspective();
        this._perspective = true;
        for (int i = 0; i < allSquares64.length; ++i) {
            final Square square = allSquares64[i];
            final Rect bounds = this.getBounds(square.getColumn(), square.getRow());
            final Drawable drawableForSquare = this.getDrawableForSquare(square);
            drawableForSquare.setBounds(bounds);
            drawableForSquare.draw(canvas);
        }
        this._perspective = perspective;
    }
    
    private void playMoveSound(final Move move) {
        if (this._soundOn) {
            final Context context = this.getContext();
            int n;
            if (move.isTaking()) {
                n = 2131623936;
            }
            else {
                n = 2131623938;
            }
            final MediaPlayer create = MediaPlayer.create(context, n);
            if (create != null) {
                final float moveAudioVolume = ServiceProvider.getInstance().getSettingsService().getMoveAudioVolume();
                create.setVolume(moveAudioVolume, moveAudioVolume);
                create.setOnCompletionListener((MediaPlayer.OnCompletionListener)new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(final MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });
                create.start();
            }
        }
    }
    
    private void setBoardPosition(final PieceSetup pieceSetup) {
        synchronized (this) {
            if (this._animatedMovingPiece != null) {
                this._animatedMovingPiece.clearAnimation();
                this._animatedMovingPiece = null;
            }
            this.removeAllViews();
            for (final PieceInformation pieceInformation : pieceSetup.getAllPieces()) {
                final ImageView imageViewForPiece = this.getImageViewForPiece(pieceInformation);
                LayoutParams layoutParams;
                if (this.getPerspective()) {
                    layoutParams = new LayoutParams(pieceInformation.getSquare().getColumn(), 7 - pieceInformation.getSquare().getRow(), 1, 1);
                }
                else {
                    layoutParams = new LayoutParams(7 - pieceInformation.getSquare().getColumn(), pieceInformation.getSquare().getRow(), 1, 1);
                }
                this.addView((View)imageViewForPiece, (ViewGroup.LayoutParams)layoutParams);
            }
            this.addView((View)(this._arrowsLayer = new ArrowView(this.getContext())), (ViewGroup.LayoutParams)new LayoutParams(0, 0, 8, 8));
            this.bringChildToFront(this._arrowsLayer);
            this.invalidate();
        }
    }
    
    private void startNextMovement() {
        synchronized (this) {
            if (this._nextAnimations.size() > 0) {
                final MoveAnimationInformationHolder moveAnimationInformationHolder = this._nextAnimations.get(0);
                if (moveAnimationInformationHolder != null) {
                    this._nextAnimations.remove(moveAnimationInformationHolder);
                    final ImageView imageViewForSquare = this.findImageViewForSquare(moveAnimationInformationHolder.move.getSquareFrom());
                    if (imageViewForSquare != null) {
                        final Move move = moveAnimationInformationHolder.move;
                        final Rect bounds = this.getBounds(move.getSquareFrom().getColumn(), move.getSquareFrom().getRow());
                        final Rect bounds2 = this.getBounds(move.getSquareTo().getColumn(), move.getSquareTo().getRow());
                        final TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, (float)(bounds2.centerX() - bounds.centerX()), 0.0f, (float)(bounds2.centerY() - bounds.centerY()));
                        translateAnimation.setDuration((long)moveAnimationInformationHolder.timeInMills);
                        if (this._animatedMovingPiece != null) {
                            this._animatedMovingPiece.clearAnimation();
                        }
                        (this._animatedMovingPiece = new MyAnimationListenerImageView(move, moveAnimationInformationHolder.listener, this.getContext(), (Animation)translateAnimation)).setImageDrawable(imageViewForSquare.getDrawable());
                        LayoutParams layoutParams = new LayoutParams(move.getSquareFrom().getColumn(), 7 - move.getSquareFrom().getRow(), 1, 1);
                        if (!this.getPerspective()) {
                            layoutParams = new LayoutParams(7 - move.getSquareFrom().getColumn(), move.getSquareFrom().getRow(), 1, 1);
                        }
                        this.addView((View)this._animatedMovingPiece, (ViewGroup.LayoutParams)layoutParams);
                        this.removeView((View)imageViewForSquare);
                        translateAnimation.setZAdjustment(1);
                        translateAnimation.setFillAfter(false);
                        translateAnimation.setFillBefore(false);
                        this._animatedMovingPiece.bringToFront();
                        this._animatedMovingPiece.startAnimation((Animation)translateAnimation);
                    }
                    else {
                        this.startNextMovement();
                    }
                }
            }
        }
    }
    
    private void updateBackgroundIfNeeded() {
        if (this._lastPaintedSquareWidth < this._squareWidth * 0.9 || this._lastPaintedSquareWidth > this._squareWidth * 1.1) {
            this._lastPaintedSquareWidth = this._squareWidth;
            this.updateBackground();
        }
    }
    
    @Override
    public void boardMarkingChanged(final BoardMarks externalBoardMarkings) {
        this._externalBoardMarkings = externalBoardMarkings;
        this.invalidate();
    }
    
    @Override
    public void boardSettingsChanged() {
        this._flagBoardSettingsChanged = true;
        if (this.getVisibility() == 0) {
            this.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    BoardView.this.rereadSettings();
                    BoardView.this._flagBoardSettingsChanged = false;
                }
            });
        }
    }
    
    public void bringChildToFront(final View view) {
        super.bringChildToFront(view);
        final ArrowView arrowsLayer = this._arrowsLayer;
    }
    
    public void clearCachedBitmaps() {
        synchronized (this) {
            if (this._squaresBitmap != null) {
                this._background = null;
                this._squaresBitmap.recycle();
                this._squaresBitmap = null;
            }
        }
    }
    
    public ImageView findImageViewForSquare(final Square square) {
        for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            final View child = this.getChildAt(i);
            if (child.getTag() != null && child.getTag().equals(square)) {
                return (ImageView)child;
            }
        }
        return null;
    }
    
    public void flip() {
        this.flip(this.getPerspective() ^ true);
    }
    
    public void flip(final boolean perspective) {
        this._perspective = perspective;
        this.setBoardPosition(this._currentPosition);
        this.invalidate();
    }
    
    @Override
    protected Rect getBounds(int n, int n2) {
        if (this.getPerspective()) {
            n2 = 7 - n2;
        }
        else {
            n = 7 - n;
        }
        return super.getBounds(n, n2);
    }
    
    public Drawable getDrawableForPiece(final Piece piece) {
        final int drawableIdForPiece = this._boardViewSettings.getDrawableIdForPiece(piece);
        if (drawableIdForPiece != -1) {
            return this._context.getResources().getDrawable(drawableIdForPiece);
        }
        return null;
    }
    
    protected ImageView getImageViewForPiece(final PieceInformation pieceInformation) {
        final ImageView imageView = new ImageView(this._context);
        imageView.setImageDrawable(this.getPositionedDrawableForPiece(pieceInformation));
        imageView.setTag((Object)pieceInformation.getSquare());
        return imageView;
    }
    
    protected Square getNextMarkedSquareForCoordinatesForActivePiece(final float n, final float n2, float n3) {
        n3 *= (float)Math.sqrt(Math.pow(this.getSquareWidth() / 2.0f, 2.0) * 2.0);
        final Iterator<Square> iterator = this._activePieceMoveSquares.iterator();
        Square square = null;
        while (iterator.hasNext()) {
            final Square square2 = iterator.next();
            final float n4 = (float)Math.sqrt(Math.pow(n - this.getSquareCenterX(square2), 2.0) + Math.pow(n2 - this.getSquareCenterY(square2), 2.0));
            if (n4 < n3) {
                square = square2;
                n3 = n4;
            }
        }
        return square;
    }
    
    public boolean getPerspective() {
        return this._perspective;
    }
    
    public Position getPosition() {
        return this._currentPosition;
    }
    
    public float getSquareCenterX(final Square square) {
        if (this.getPerspective()) {
            return this._squareWidth * (square.getColumn() + 0.5f);
        }
        return this._squareWidth * (7.5f - square.getColumn());
    }
    
    public float getSquareCenterY(final Square square) {
        if (this.getPerspective()) {
            return this._squareHeight * (7.5f - square.getRow());
        }
        return this._squareHeight * (square.getRow() + 0.5f);
    }
    
    public Square getSquareForCoordinates(final float n, final float n2) {
        int n3;
        if (this.getPerspective()) {
            n3 = 7 - (int)Math.floor(n2 / this._squareHeight);
        }
        else {
            n3 = (int)Math.floor(n2 / this._squareHeight);
        }
        int n4;
        if (this.getPerspective()) {
            n4 = (int)Math.floor(n / this._squareWidth);
        }
        else {
            n4 = 7 - (int)Math.floor(n / this._squareWidth);
        }
        return Square.instanceForRowAndColumn(n3, n4);
    }
    
    public void invalidate() {
        super.invalidate();
        if (this._arrowsLayer != null) {
            this._arrowsLayer.invalidate();
        }
    }
    
    public boolean isCurrentlyZooming() {
        return this._zooming;
    }
    
    public boolean isPlayingSounds() {
        return this._soundOn;
    }
    
    public void markSquare(final Square square, final int n) {
        this._localMarkings.getSquareMarkings().addSquareMark(square, n);
        this.invalidate();
    }
    
    protected void onDraw(final Canvas canvas) {
        int i = 0;
        final Rect bounds = this.getBounds(0, 0, 8, 8);
        this._markerPaint.setARGB(128, 200, 50, 50);
        if (this._background == null) {
            if (this._squaresBitmap == null) {
                this.createBackgroundBitmap();
            }
            this._background = (Drawable)new BitmapDrawable(this.getResources(), this._squaresBitmap);
        }
        this._background.setBounds(bounds);
        this._background.draw(canvas);
        for (Square[] allSquares64 = Square.getAllSquares64(); i < allSquares64.length; ++i) {
            final Square square = allSquares64[i];
            final Rect bounds2 = this.getBounds(square.getColumn(), square.getRow());
            this._colors.clear();
            final List<Integer> markingsForSquare = this._localMarkings.getSquareMarkings().getMarkingsForSquare(square);
            if (markingsForSquare != null) {
                this._colors.addAll(markingsForSquare);
            }
            if (this._externalBoardMarkings != null) {
                final List<Integer> markingsForSquare2 = this._externalBoardMarkings.getSquareMarkings().getMarkingsForSquare(square);
                if (markingsForSquare2 != null) {
                    this._colors.addAll(markingsForSquare2);
                }
            }
            final Iterator<Integer> iterator = this._colors.iterator();
            while (iterator.hasNext()) {
                this._markerPaint.setColor((int)iterator.next());
                this._markerPaint.setAlpha(80);
                canvas.drawRect(bounds2, this._markerPaint);
            }
            if (this._boardViewSettings.isMarkMoveSquaresModeEnabled() && this._activePieceMoveSquares != null && this._activePieceMoveSquares.contains(square) && this._activePieceSquare != null && this._currentPosition.getPieceFor(this._activePieceSquare) != null) {
                if (this._currentPosition.getPieceFor(this._activePieceSquare).getColor() == this._currentPosition.getActiveColor()) {
                    this._markerPaint.setColor(this._markColorUsualMove);
                }
                else {
                    this._markerPaint.setColor(this._markColorPremove);
                }
                this._markerPaint.setAlpha(80);
                canvas.drawRect(bounds2, this._markerPaint);
            }
        }
        if (this._moveSquare != null) {
            final Rect bounds3 = this.getBounds(this._moveSquare.getColumn(), this._moveSquare.getRow());
            canvas.drawCircle((float)bounds3.centerX(), (float)bounds3.centerY(), bounds3.width() * 1.41f, this._markerPaint);
        }
        if (this._movePoint != null) {
            this._markerPaint.setAlpha((int)(128.0f - 12.0f * (this.getLowDistance(this._movePoint.x, this._movePoint.y) / this._squareWidth)));
            canvas.drawCircle(this._movePoint.x, this._movePoint.y, this._squareWidth * 1.41f, this._markerPaint);
        }
        super.onDraw(canvas);
    }
    
    public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4 && this._promotionPresenter != null && this._promotionPresenter.isPromotionShown()) {
            this._promotionPresenter.dismissDialog();
            return true;
        }
        return false;
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this._squareWidth = n / 8.0f;
        this._squareHeight = n2 / 8.0f;
        if (!this._zooming) {
            this.updateBackgroundIfNeeded();
        }
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (this._allowBlackMoves || this._allowWhiteMoves || this._allowBlackPremoves || this._allowWhitePremoves) {
            final float x = motionEvent.getX();
            final float y = motionEvent.getY();
            final Square squareForCoordinates = this.getSquareForCoordinates(x, y);
            final Piece piece = this._currentPosition.getPieceFor(squareForCoordinates);
            switch (motionEvent.getAction()) {
                case 2: {
                    this._probableSquare = this.getNextMarkedSquareForCoordinatesForActivePiece(x, y, 2.0f);
                    if (this._probableSquare != null && this._activePieceSquare != null && !this._activePieceSquare.equals(squareForCoordinates)) {
                        this.setMoveSquare(this._probableSquare);
                        this._movePoint = null;
                    }
                    else {
                        this.setMovePoint(x, y);
                        this.setMoveSquare(null);
                    }
                    this.invalidate();
                    break;
                }
                case 1: {
                    this._movePoint = null;
                    if (this._activePieceSquare != null) {
                        final Piece piece2 = this._currentPosition.getPieceFor(this._activePieceSquare);
                        this._probableSquare = this.getNextMarkedSquareForCoordinatesForActivePiece(x, y, 2.0f);
                        if (this._probableSquare != null) {
                            if (piece2 != null) {
                                if (piece2.getColor() == this._currentPosition.getActiveColor()) {
                                    if (((this._currentPosition.getActiveColor() && this._allowWhiteMoves) || (!this._currentPosition.getActiveColor() && this._allowBlackMoves)) && !this._activePieceSquare.equals(squareForCoordinates) && this._currentPosition.isMovePossible(this._activePieceSquare, this._probableSquare)) {
                                        this._isPromotion = this._currentPosition.isPromotion(piece2, this._activePieceSquare, this._probableSquare);
                                        final boolean autoQueenEnabled = this._boardViewSettings.isAutoQueenEnabled();
                                        if (this._isPromotion && !autoQueenEnabled) {
                                            this.showPromotionView(this._currentPosition.getActiveColor());
                                        }
                                        else {
                                            int n2;
                                            final int n = n2 = 0;
                                            if (this._isPromotion) {
                                                n2 = n;
                                                if (autoQueenEnabled) {
                                                    n2 = 3;
                                                }
                                            }
                                            this.doMove(this.createMove(this._activePieceSquare, this._probableSquare, n2));
                                        }
                                    }
                                }
                                else if (this._boardViewSettings.isPremoveEnabled() && ((!this._currentPosition.getActiveColor() && this._allowWhitePremoves) || (this._currentPosition.getActiveColor() && this._allowBlackPremoves)) && !this._activePieceSquare.equals(squareForCoordinates)) {
                                    if (this._moveExecutor != null) {
                                        this._moveExecutor.registerPremove(this._activePieceSquare, this._probableSquare);
                                    }
                                    this._localMarkings.getArrowContainer().addArrow(new Arrow(new SEP(this._activePieceSquare, this._probableSquare), ArrowCategory.USER_MARKS, this._markColorPremove));
                                    this.resetActivePiece();
                                }
                            }
                        }
                        else if (squareForCoordinates == null || !squareForCoordinates.equals(this._activePieceSquare)) {
                            this.resetActivePiece();
                        }
                    }
                    this._probableSquare = null;
                    this.invalidate();
                    break;
                }
                case 0: {
                    if (squareForCoordinates.equals(this._activePieceSquare)) {
                        this.resetActivePiece();
                    }
                    else {
                        if ((this._currentPosition.getActiveColor() && this._allowWhiteMoves) || (!this._currentPosition.getActiveColor() && this._allowBlackMoves)) {
                            if (this._activePieceSquare != null) {
                                this._probableSquare = this.getNextMarkedSquareForCoordinatesForActivePiece(x, y, 2.0f);
                                if (!squareForCoordinates.equals(this._activePieceSquare) && piece != null && piece.getColor() == this._currentPosition.getActiveColor()) {
                                    this.resetActivePiece();
                                }
                                else {
                                    this.setMoveSquare(this._probableSquare);
                                }
                            }
                            else if (piece != null && piece.getColor() == this._currentPosition.getActiveColor()) {
                                this.setActivePieceSquare(squareForCoordinates);
                            }
                            else {
                                this.resetActivePiece();
                            }
                        }
                        if (this._boardViewSettings.isPremoveEnabled() && ((!this._currentPosition.getActiveColor() && this._allowWhitePremoves) || (this._currentPosition.getActiveColor() && this._allowBlackPremoves)) && this._activePieceSquare == null && piece != null && piece.getColor() != this._currentPosition.getActiveColor()) {
                            if (this._moveExecutor != null) {
                                this._moveExecutor.registerPremove(null, null);
                            }
                            this._localMarkings.getArrowContainer().removeAllArrows();
                            this.setActivePieceSquare(squareForCoordinates);
                        }
                    }
                    this.invalidate();
                    break;
                }
            }
        }
        return true;
    }
    
    protected void onVisibilityChanged(final View view, final int n) {
        super.onVisibilityChanged(view, n);
        if (this._flagBoardSettingsChanged && n == 0 && view == this) {
            this.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    BoardView.this.rereadSettings();
                }
            });
            this._flagBoardSettingsChanged = false;
        }
    }
    
    @Override
    public void positionChanged(final Position position, final Move move) {
        // monitorenter(this)
        boolean b = true;
        boolean b2 = move != null;
        Label_0032: {
            if (move != null) {
                break Label_0032;
            }
        Label_0065_Outer:
            while (true) {
                while (true) {
                    int n = 0;
                Label_0065:
                    while (true) {
                        while (true) {
                            try {
                                this.setPosition(position);
                                n = 0;
                                break Label_0065;
                                // iftrue(Label_0120:, parent == null || !parent.getResultingPosition().equals((Object)this._currentPosition))
                                while (true) {
                                    n = 1;
                                    break Label_0065;
                                    Label_0080: {
                                        this.setPosition(position);
                                    }
                                    b2 = false;
                                    break Label_0065;
                                    final MoveContainer parent = move.getParent();
                                    continue Label_0065_Outer;
                                }
                                this.post((Runnable)new Runnable() {
                                    @Override
                                    public void run() {
                                        BoardView.this._localMarkings.clear();
                                        if (move != null && BoardView.this._boardViewSettings.isArrowOfLastMoveEnabled()) {
                                            BoardView.this._localMarkings.getArrowContainer().addArrow(new Arrow(move.getSEP(), ArrowCategory.LAST_MOVE, Color.argb(110, 255, 255, 255)));
                                            BoardView.this._arrowsLayer.invalidate();
                                        }
                                        final Square access.300 = BoardView.this._activePieceSquare;
                                        BoardView.this.resetActivePiece();
                                        if (access.300 != null && move != null && ((BoardView.this._allowWhitePremoves && move.getPiece().isBlack()) || (BoardView.this._allowBlackPremoves && move.getPiece().isWhite()))) {
                                            BoardView.this.setActivePieceSquare(access.300);
                                            BoardView.this.setMoveSquare(BoardView.this._probableSquare);
                                        }
                                        if (b2) {
                                            BoardView.this.animateMove(move, BoardView.this._boardViewSettings.getMoveTimeInMills(), null);
                                        }
                                        if (move != null && b) {
                                            BoardView.this.playMoveSound(move);
                                        }
                                    }
                                });
                                // monitorexit(this)
                                return;
                                // monitorexit(this)
                                throw position;
                                // iftrue(Label_0080:, n == 0)
                                this._currentPosition = move.getResultingPosition();
                                break Label_0065;
                            }
                            finally {
                                continue;
                            }
                            break;
                        }
                        Label_0120: {
                            n = 0;
                        }
                        continue Label_0065;
                    }
                    if (b2 && n != 0) {
                        continue;
                    }
                    b = false;
                    continue;
                }
            }
        }
    }
    
    public void removeAllMoveArrows() {
        this._localMarkings.getArrowContainer().removeArrowsForCategory(ArrowCategory.LAST_MOVE);
        this.invalidate();
    }
    
    public void rereadSettings() {
        this._background = null;
        this.updateBackground();
        this.setPlaySounds(this._boardViewSettings.isPlayingMoveSounds());
        this.setPosition(this._currentPosition.getSafePosition());
    }
    
    public void resetActivePiece() {
        this.setActivePieceSquare(null);
        this._activePieceMoveSquares.clear();
        this._movePoint = null;
        this.setMoveSquare(null);
        this.invalidate();
    }
    
    @Override
    public void restoreState(final Bundle bundle) {
        this.flip(bundle.getBoolean("BOARDVIEW_PERSPECTIVE", true));
    }
    
    @Override
    public void saveState(final Bundle bundle) {
        bundle.putBoolean("BOARDVIEW_PERSPECTIVE", this._perspective);
    }
    
    public void setActivePieceSquare(final Square activePieceSquare) {
        this._activePieceMoveSquares.clear();
        if (this._activePieceSquare != null) {
            this._localMarkings.getSquareMarkings().removeSquareMark(this._activePieceSquare);
        }
        this._activePieceSquare = null;
        if (activePieceSquare != null) {
            final Piece piece = this._currentPosition.getPieceFor(activePieceSquare);
            if (piece != null) {
                if (piece.getColor() == this._currentPosition.getActiveColor()) {
                    this._activePieceMoveSquares.addAll(piece.getAllMoves(this._currentPosition.getSafePosition(), activePieceSquare));
                }
                else {
                    this._activePieceMoveSquares.addAll(this.getPossiblePremoveSquares(piece, this._currentPosition.getSafePosition(), activePieceSquare));
                }
                if (!this._activePieceMoveSquares.isEmpty()) {
                    this._activePieceSquare = activePieceSquare;
                    if (piece.getColor() == this._currentPosition.getActiveColor()) {
                        this._localMarkings.getSquareMarkings().addSquareMark(activePieceSquare, this._markColorUsualMove);
                    }
                    else {
                        this._localMarkings.getSquareMarkings().addSquareMark(activePieceSquare, this._markColorPremove);
                    }
                }
                else if (this._currentPosition.isCheck()) {
                    this._localMarkings.getSquareMarkings().addSquareMark(this._currentPosition.getKingSquareForColor(piece.getColor()), -65536);
                }
            }
        }
        this.invalidate();
    }
    
    public void setBlackMoveable(final boolean allowBlackMoves) {
        this._allowBlackMoves = allowBlackMoves;
    }
    
    public void setBlackPreMoveable(final boolean allowBlackPremoves) {
        this._allowBlackPremoves = allowBlackPremoves;
    }
    
    public void setBoardViewSettings(final BoardViewSettings boardViewSettings) {
        this._boardViewSettings = boardViewSettings;
    }
    
    public void setIsZooming(final boolean zooming) {
        if (this._zooming != zooming) {
            this.updateBackgroundIfNeeded();
        }
        this._zooming = zooming;
    }
    
    public void setMovable(final boolean b) {
        this._allowWhiteMoves = b;
        this._allowBlackMoves = b;
    }
    
    public void setMoveExecutor(final MoveExecutor moveExecutor) {
        this._moveExecutor = moveExecutor;
    }
    
    protected void setMovePoint(final float n, final float n2) {
        this._movePoint = new PointF(n, n2);
    }
    
    public void setMoveSquare(final Square moveSquare) {
        this._moveSquare = moveSquare;
    }
    
    public void setPlaySounds(final boolean soundOn) {
        this._soundOn = soundOn;
    }
    
    public void setPosition(final Position currentPosition) {
        synchronized (this) {
            this._currentPosition = currentPosition;
            this.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    BoardView.this.stopMovement(currentPosition);
                    BoardView.this.setBoardPosition(currentPosition);
                    BoardView.this.resetActivePiece();
                    BoardView.this._localMarkings.clear();
                    BoardView.this.invalidate();
                }
            });
        }
    }
    
    public void setPremovable(final boolean allowBlackPremoves) {
        this._boardViewSettings.setPremoveEnabled(allowBlackPremoves);
        this._allowWhitePremoves = allowBlackPremoves;
        this._allowBlackPremoves = allowBlackPremoves;
    }
    
    public void setPromotionPresenter(final PromotionPresenter promotionPresenter) {
        this._promotionPresenter = promotionPresenter;
    }
    
    public void setWhiteMoveable(final boolean allowWhiteMoves) {
        this._allowWhiteMoves = allowWhiteMoves;
    }
    
    public void setWhitePreMoveable(final boolean allowWhitePremoves) {
        this._allowWhitePremoves = allowWhitePremoves;
    }
    
    public void showPromotionView(final boolean b) {
        this._promotionSquare = this._probableSquare;
        final View inflate = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427525, (ViewGroup)null);
        final ImageView imageView = (ImageView)inflate.findViewById(2131296885);
        final ImageView imageView2 = (ImageView)inflate.findViewById(2131296883);
        final ImageView imageView3 = (ImageView)inflate.findViewById(2131296884);
        final ImageView imageView4 = (ImageView)inflate.findViewById(2131296886);
        final Resources resources = this.getResources();
        final BoardViewSettings boardViewSettings = this._boardViewSettings;
        final int drawableIdForPiece = boardViewSettings.getDrawableIdForPiece(Queen.instanceForColor(b));
        final int drawableIdForPiece2 = boardViewSettings.getDrawableIdForPiece(Bishop.instanceForColor(b));
        final int drawableIdForPiece3 = boardViewSettings.getDrawableIdForPiece(Knight.instanceForColor(b));
        final int drawableIdForPiece4 = boardViewSettings.getDrawableIdForPiece(Rook.instanceForColor(b));
        imageView.setImageDrawable(resources.getDrawable(drawableIdForPiece));
        imageView2.setImageDrawable(resources.getDrawable(drawableIdForPiece2));
        imageView3.setImageDrawable(resources.getDrawable(drawableIdForPiece3));
        imageView4.setImageDrawable(resources.getDrawable(drawableIdForPiece4));
        this._promotionPresenter.showPromotionDialog(inflate);
        final PromotionClickListener promotionClickListener = new PromotionClickListener();
        imageView.setOnClickListener((View.OnClickListener)promotionClickListener);
        imageView3.setOnClickListener((View.OnClickListener)promotionClickListener);
        imageView2.setOnClickListener((View.OnClickListener)promotionClickListener);
        imageView4.setOnClickListener((View.OnClickListener)promotionClickListener);
    }
    
    public void stopMovement(final Position boardPosition) {
        if (this._animatedMovingPiece != null) {
            this._nextAnimations.clear();
            this._animatedMovingPiece.clearAnimation();
            this._animatedMovingPiece = null;
            this.setBoardPosition(boardPosition);
        }
    }
    
    public void updateBackground() {
        synchronized (this) {
            this.clearCachedBitmaps();
            this.createBackgroundBitmap();
        }
    }
    
    private class ArrowView extends View
    {
        Set<Arrow> arrows;
        private Paint markerPaint;
        private Path path;
        private Point point1;
        private Point point2;
        private Point point3;
        private Point wayFromStartToFinish;
        
        public ArrowView(final Context context) {
            super(context);
            this.wayFromStartToFinish = new Point();
            this.point1 = new Point();
            this.point2 = new Point();
            this.point3 = new Point();
            this.markerPaint = new Paint();
            this.path = new Path();
            this.arrows = new HashSet<Arrow>();
            this.setWillNotDraw(false);
        }
        
        protected void onDraw(final Canvas canvas) {
            this.arrows.clear();
            final Collection<Arrow> allArrows = BoardView.this._localMarkings.getArrowContainer().getAllArrows();
            if (allArrows != null) {
                this.arrows.addAll(allArrows);
            }
            if (BoardView.this._externalBoardMarkings != null) {
                final Collection<Arrow> allArrows2 = BoardView.this._externalBoardMarkings.getArrowContainer().getAllArrows();
                if (allArrows2 != null) {
                    this.arrows.addAll(allArrows2);
                }
            }
            for (final Arrow arrow : this.arrows) {
                final int color = arrow.getColor();
                this.markerPaint.setColor(Color.argb(128, Color.red(color), Color.green(color), Color.blue(color)));
                final SEP sep = arrow.getSep();
                final Square startSquare = sep.getStartSquare();
                final Square endSquare = sep.getEndSquare();
                final Rect bounds = BoardView.this.getBounds(startSquare.getColumn(), startSquare.getRow());
                final Rect bounds2 = BoardView.this.getBounds(endSquare.getColumn(), endSquare.getRow());
                final int width = bounds.width();
                this.markerPaint.setStrokeWidth((float)Math.max(width / 7, 1));
                this.markerPaint.setStyle(Paint.Style.FILL);
                this.markerPaint.setAntiAlias(true);
                this.wayFromStartToFinish.x = bounds2.centerX() - bounds.centerX();
                this.wayFromStartToFinish.y = bounds2.centerY() - bounds.centerY();
                final double sqrt = Math.sqrt(this.wayFromStartToFinish.x * this.wayFromStartToFinish.x + this.wayFromStartToFinish.y * this.wayFromStartToFinish.y);
                final float n = 0;
                final double n2 = width / sqrt * 3;
                this.wayFromStartToFinish.x = (int)Math.round(this.wayFromStartToFinish.x * n2);
                this.wayFromStartToFinish.y = (int)Math.round(this.wayFromStartToFinish.y * n2);
                this.point1.x = bounds2.centerX() + (int)(this.wayFromStartToFinish.x / 8 * n);
                this.point1.y = bounds2.centerY() + (int)(this.wayFromStartToFinish.y / 8 * n);
                this.point2.x = bounds2.centerX() - this.wayFromStartToFinish.y / 8 - this.wayFromStartToFinish.x / 5;
                this.point2.y = bounds2.centerY() + this.wayFromStartToFinish.x / 8 - this.wayFromStartToFinish.y / 5;
                this.point3.x = bounds2.centerX() + this.wayFromStartToFinish.y / 8 - this.wayFromStartToFinish.x / 5;
                this.point3.y = bounds2.centerY() - this.wayFromStartToFinish.x / 8 - this.wayFromStartToFinish.y / 5;
                this.path.reset();
                this.path.setFillType(Path.FillType.EVEN_ODD);
                this.path.moveTo((float)this.point1.x, (float)this.point1.y);
                this.path.lineTo((float)this.point2.x, (float)this.point2.y);
                this.path.lineTo((float)this.point3.x, (float)this.point3.y);
                this.path.lineTo((float)this.point1.x, (float)this.point1.y);
                this.path.close();
                canvas.drawLine((float)bounds.centerX(), (float)bounds.centerY(), (float)(bounds2.centerX() - this.wayFromStartToFinish.x / 5), (float)(bounds2.centerY() - this.wayFromStartToFinish.y / 5), this.markerPaint);
                canvas.drawPath(this.path, this.markerPaint);
            }
        }
    }
    
    public interface BoardViewSettings
    {
        int getDrawableIdForPiece(final Piece p0);
        
        int getDrawableIdForSquare(final Square p0);
        
        int getMoveTimeInMills();
        
        boolean isArrowOfLastMoveEnabled();
        
        boolean isAutoQueenEnabled();
        
        boolean isMarkMoveSquaresModeEnabled();
        
        boolean isPlayingMoveSounds();
        
        boolean isPremoveEnabled();
        
        void setAutoQueenEnabled(final boolean p0);
        
        void setMoveTimeInMills(final int p0);
        
        void setPremoveEnabled(final boolean p0);
    }
    
    private static class MoveAnimationInformationHolder
    {
        public Animation.AnimationListener listener;
        public Move move;
        public int timeInMills;
        
        public MoveAnimationInformationHolder(final Move move, final Animation.AnimationListener listener, final int timeInMills) {
            this.move = move;
            this.listener = listener;
            this.timeInMills = timeInMills;
        }
    }
    
    private class MyAnimationListenerImageView extends ImageView
    {
        private Animation _animation;
        private Animation.AnimationListener _externalListener;
        public Move _move;
        
        public MyAnimationListenerImageView(final Move move, final Animation.AnimationListener externalListener, final Context context, final Animation animation) {
            super(context);
            this._externalListener = externalListener;
            this._animation = animation;
            this._move = move;
        }
        
        public void clearAnimation() {
            synchronized (this) {
                this._animation.cancel();
                super.clearAnimation();
                BoardView.this.getUiThreadHandler().postAtFrontOfQueue((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        MyAnimationListenerImageView.this.setVisibility(4);
                    }
                });
            }
        }
        
        protected void onAnimationEnd() {
            synchronized (this) {
                super.onAnimationEnd();
                if (this._externalListener != null) {
                    this._externalListener.onAnimationEnd(this._animation);
                }
                BoardView.this.getUiThreadHandler().postAtFrontOfQueue((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        BoardView.this.setBoardPosition(MyAnimationListenerImageView.this._move.getResultingPieceSetup());
                    }
                });
                BoardView.this._animatedMovingPiece = null;
            }
        }
        
        public void onAnimationStart() {
            synchronized (this) {
                super.onAnimationStart();
                if (this._externalListener != null) {
                    this._externalListener.onAnimationStart(this._animation);
                }
            }
        }
    }
    
    private class PromotionClickListener implements View.OnClickListener
    {
        public void onClick(final View view) {
            Move move = null;
            switch (view.getId()) {
                default: {
                    move = null;
                    break;
                }
                case 2131296886: {
                    move = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 0);
                    break;
                }
                case 2131296885: {
                    move = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 3);
                    break;
                }
                case 2131296884: {
                    move = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 1);
                    break;
                }
                case 2131296883: {
                    move = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 2);
                    break;
                }
            }
            BoardView.this._promotionPresenter.dismissDialog();
            BoardView.this.doMove(move);
        }
    }
    
    public interface PromotionPresenter
    {
        void dismissDialog();
        
        boolean isPromotionShown();
        
        void showPromotionDialog(final View p0);
    }
}
