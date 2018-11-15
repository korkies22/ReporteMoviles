/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.Point
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.TranslateAnimation
 *  android.widget.ImageView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import de.cisha.android.board.model.ArrowContainer;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.model.SquareMarkings;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.view.ScaleableGridLayout;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.PieceSetup;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BoardView
extends ScaleableGridLayout
implements StateHolder,
PositionObserver,
SettingsService.BoardSettingObserver,
BoardMarksManager.BoardMarkingObserver {
    private static final String BOARDVIEW_PERSPECTIVE = "BOARDVIEW_PERSPECTIVE";
    private static final int SOUND_CAPTURE = 2131623936;
    private static final int SOUND_MOVE = 2131623938;
    private Set<Square> _activePieceMoveSquares;
    private Square _activePieceSquare;
    private boolean _allowBlackMoves = true;
    private boolean _allowBlackPremoves = true;
    private boolean _allowWhiteMoves = true;
    private boolean _allowWhitePremoves = true;
    private MyAnimationListenerImageView _animatedMovingPiece;
    private ArrowView _arrowsLayer;
    Drawable _background;
    private BoardViewSettings _boardViewSettings;
    private List<Integer> _colors = new ArrayList<Integer>();
    private Position _currentPosition;
    private BoardMarks _externalBoardMarkings;
    private boolean _flagBoardSettingsChanged;
    private boolean _isPromotion = false;
    private float _lastPaintedSquareWidth = -100.0f;
    private BoardMarks _localMarkings;
    private int _markColorPremove = 1140872533;
    private int _markColorUsualMove = 1140850943;
    Paint _markerPaint = new Paint();
    private MoveExecutor _moveExecutor;
    private PointF _movePoint = null;
    private Square _moveSquare;
    private List<MoveAnimationInformationHolder> _nextAnimations = new LinkedList<MoveAnimationInformationHolder>();
    private boolean _perspective = true;
    private Square _probableSquare;
    private PromotionPresenter _promotionPresenter;
    private Square _promotionSquare;
    private boolean _soundOn = false;
    private float _squareHeight;
    private float _squareWidth;
    private Bitmap _squaresBitmap;
    private Handler _uiThreadHandler;
    private boolean _zooming;

    protected BoardView(Context context) {
        super(context, 8, 8);
        this._context = context;
        this._currentPosition = new Position();
        this.init();
    }

    protected BoardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 8, 8);
        this._context = context;
        this._currentPosition = new Position();
        this.init();
    }

    protected BoardView(Context context, Position position) {
        super(context, 8, 8);
        this._context = context;
        this._currentPosition = position;
        this.init();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void animateMove(Move move, int n, Animation.AnimationListener animationListener) {
        synchronized (this) {
            if (move.getResultingPosition().equals(this._currentPosition)) {
                void var2_2;
                Object object = move.getParent();
                if (object != null && (object = object.getResultingPosition()) != null) {
                    this.stopMovement((Position)object);
                }
                if (move != null && var2_2 > 0) {
                    void var3_3;
                    this._nextAnimations.add(new MoveAnimationInformationHolder(move, (Animation.AnimationListener)var3_3, (int)var2_2));
                    if (this._animatedMovingPiece == null) {
                        this.startNextMovement();
                    }
                } else if (move != null) {
                    this.setBoardPosition(move.getResultingPosition());
                }
                this.invalidate();
            } else {
                this.setBoardPosition(move.getResultingPosition());
            }
            return;
        }
    }

    private void createBackgroundBitmap() {
        if (this._width > 0 && this._height > 0) {
            this._lastPaintedSquareWidth = this._squareWidth;
            Bitmap.Config config = Bitmap.Config.ARGB_4444;
            this._squaresBitmap = Bitmap.createBitmap((int)this._width, (int)this._height, (Bitmap.Config)config);
            this.paintSquares(new Canvas(this._squaresBitmap));
            return;
        }
        this._lastPaintedSquareWidth = 1.0f;
        this._squaresBitmap = Bitmap.createBitmap((int)1, (int)1, (Bitmap.Config)Bitmap.Config.ARGB_4444);
    }

    private Move createMove(Square square, Square square2, int n) {
        return this._currentPosition.createMoveFrom(new SEP(square, square2, n));
    }

    private void doMove(Move move) {
        if (move != null) {
            if (this._moveExecutor != null) {
                this._moveExecutor.doMoveInCurrentPosition(move.getSEP());
                return;
            }
            this.setPosition(move.getResultingPosition());
        }
    }

    private Drawable getDrawableForSquare(Square square) {
        if (this.isInEditMode()) {
            if (square.isWhite()) {
                return this.getResources().getDrawable(2131231911);
            }
            return this.getResources().getDrawable(2131230882);
        }
        return this.getResources().getDrawable(this._boardViewSettings.getDrawableIdForSquare(square));
    }

    private float getLowDistance(float f, float f2) {
        Iterator<Square> iterator = this._activePieceMoveSquares.iterator();
        float f3 = Float.MAX_VALUE;
        while (iterator.hasNext()) {
            Square square = iterator.next();
            float f4 = (float)Math.sqrt(Math.pow(f - this.getSquareCenterX(square), 2.0) + Math.pow(f2 - this.getSquareCenterY(square), 2.0));
            if (f3 <= f4) continue;
            f3 = f4;
        }
        return f3;
    }

    private Drawable getPositionedDrawableForPiece(PieceInformation pieceInformation) {
        Drawable drawable = this.getDrawableForPiece(pieceInformation.getPiece());
        drawable.setBounds(this.getBounds(pieceInformation.getSquare().getColumn(), pieceInformation.getSquare().getRow()));
        return drawable;
    }

    private List<Square> getPossiblePremoveSquares(Piece serializable, Position object, Square square) {
        object = new ModifyablePosition();
        object.setPiece((Piece)serializable, square);
        object = serializable.getAllMoves(object.getSafePosition(), square);
        if (serializable.equals(King.instanceForColor(true))) {
            if (square.equals(Square.SQUARE_E1)) {
                object.add(Square.SQUARE_C1);
                object.add(Square.SQUARE_G1);
                return object;
            }
        } else if (serializable.equals(King.instanceForColor(false))) {
            if (square.equals(Square.SQUARE_E8)) {
                object.add(Square.SQUARE_C8);
                object.add(Square.SQUARE_G8);
                return object;
            }
        } else if (serializable.equalsIgnoreColor(Pawn.instanceForColor(true))) {
            int n = serializable.getColor() ? 1 : -1;
            serializable = Square.instanceForRowAndColumn(square.getRow() + n, square.getColumn() + 1);
            if (serializable.isValid()) {
                object.add(serializable);
            }
            if ((serializable = Square.instanceForRowAndColumn(square.getRow() + n, square.getColumn() - 1)).isValid()) {
                object.add(serializable);
            }
        }
        return object;
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
        this.setBoardViewSettings(new BoardViewSettings(){
            private boolean _autoQueen = false;
            private int _moveTime = 300;
            private boolean _premoveEnabled = false;

            @Override
            public int getDrawableIdForPiece(Piece piece) {
                return 2131231785;
            }

            @Override
            public int getDrawableIdForSquare(Square square) {
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
            public void setAutoQueenEnabled(boolean bl) {
                this._autoQueen = bl;
            }

            @Override
            public void setMoveTimeInMills(int n) {
                this._moveTime = n;
            }

            @Override
            public void setPremoveEnabled(boolean bl) {
                this._premoveEnabled = bl;
            }
        });
        this._localMarkings = new BoardMarks();
        this.setBackgroundColor(-16777216);
        this._activePieceMoveSquares = new HashSet<Square>();
        this.createBackgroundBitmap();
        this._arrowsLayer = new ArrowView(this.getContext());
        ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(0, 0, 8, 8);
        this.addView((View)this._arrowsLayer, (ViewGroup.LayoutParams)layoutParams);
        this.bringChildToFront(this._arrowsLayer);
        this.setWillNotDraw(false);
        if (this._currentPosition != null) {
            this.setBoardPosition(this._currentPosition);
        }
        this._promotionPresenter = new PromotionPresenter(){
            private View _promotionView;

            @Override
            public void dismissDialog() {
                BoardView.this.removeView(this._promotionView);
                BoardView.this.resetActivePiece();
            }

            @Override
            public boolean isPromotionShown() {
                if (this._promotionView != null && this._promotionView.getParent() != null) {
                    return true;
                }
                return false;
            }

            @Override
            public void showPromotionDialog(View view) {
                BoardView.this.requestFocus();
                this._promotionView = view;
                this._promotionView.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                    }
                });
                ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(0, 0, 8, 8);
                BoardView.this.addView(view, (ViewGroup.LayoutParams)layoutParams);
            }

        };
        this.setFocusableInTouchMode(true);
    }

    private void paintSquares(Canvas canvas) {
        Square[] arrsquare = Square.getAllSquares64();
        boolean bl = this.getPerspective();
        this._perspective = true;
        for (int i = 0; i < arrsquare.length; ++i) {
            Square square = arrsquare[i];
            Rect rect = this.getBounds(square.getColumn(), square.getRow());
            square = this.getDrawableForSquare(square);
            square.setBounds(rect);
            square.draw(canvas);
        }
        this._perspective = bl;
    }

    private void playMoveSound(Move move) {
        if (this._soundOn) {
            Context context = this.getContext();
            int n = move.isTaking() ? 2131623936 : 2131623938;
            move = MediaPlayer.create((Context)context, (int)n);
            if (move != null) {
                float f = ServiceProvider.getInstance().getSettingsService().getMoveAudioVolume();
                move.setVolume(f, f);
                move.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });
                move.start();
            }
        }
    }

    private void setBoardPosition(PieceSetup object2) {
        synchronized (this) {
            if (this._animatedMovingPiece != null) {
                this._animatedMovingPiece.clearAnimation();
                this._animatedMovingPiece = null;
            }
            this.removeAllViews();
            for (PieceInformation pieceInformation : object2.getAllPieces()) {
                void var1_6;
                ImageView imageView = this.getImageViewForPiece(pieceInformation);
                if (this.getPerspective()) {
                    ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(pieceInformation.getSquare().getColumn(), 7 - pieceInformation.getSquare().getRow(), 1, 1);
                } else {
                    ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(7 - pieceInformation.getSquare().getColumn(), pieceInformation.getSquare().getRow(), 1, 1);
                }
                this.addView((View)imageView, (ViewGroup.LayoutParams)var1_6);
            }
            this._arrowsLayer = new ArrowView(this.getContext());
            ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(0, 0, 8, 8);
            this.addView((View)this._arrowsLayer, (ViewGroup.LayoutParams)layoutParams);
            this.bringChildToFront(this._arrowsLayer);
            this.invalidate();
            return;
        }
    }

    private void startNextMovement() {
        synchronized (this) {
            Object object;
            if (this._nextAnimations.size() > 0 && (object = this._nextAnimations.get(0)) != null) {
                this._nextAnimations.remove(object);
                ImageView imageView = this.findImageViewForSquare(object.move.getSquareFrom());
                if (imageView != null) {
                    Move move = object.move;
                    Rect rect = this.getBounds(move.getSquareFrom().getColumn(), move.getSquareFrom().getRow());
                    Rect rect2 = this.getBounds(move.getSquareTo().getColumn(), move.getSquareTo().getRow());
                    rect = new TranslateAnimation(0.0f, (float)(rect2.centerX() - rect.centerX()), 0.0f, (float)(rect2.centerY() - rect.centerY()));
                    rect.setDuration((long)object.timeInMills);
                    if (this._animatedMovingPiece != null) {
                        this._animatedMovingPiece.clearAnimation();
                    }
                    this._animatedMovingPiece = new MyAnimationListenerImageView(move, object.listener, this.getContext(), (Animation)rect);
                    this._animatedMovingPiece.setImageDrawable(imageView.getDrawable());
                    object = new ScaleableGridLayout.LayoutParams(move.getSquareFrom().getColumn(), 7 - move.getSquareFrom().getRow(), 1, 1);
                    if (!this.getPerspective()) {
                        object = new ScaleableGridLayout.LayoutParams(7 - move.getSquareFrom().getColumn(), move.getSquareFrom().getRow(), 1, 1);
                    }
                    this.addView((View)this._animatedMovingPiece, (ViewGroup.LayoutParams)object);
                    this.removeView((View)imageView);
                    rect.setZAdjustment(1);
                    rect.setFillAfter(false);
                    rect.setFillBefore(false);
                    this._animatedMovingPiece.bringToFront();
                    this._animatedMovingPiece.startAnimation((Animation)rect);
                } else {
                    this.startNextMovement();
                }
            }
            return;
        }
    }

    private void updateBackgroundIfNeeded() {
        if ((double)this._lastPaintedSquareWidth < (double)this._squareWidth * 0.9 || (double)this._lastPaintedSquareWidth > (double)this._squareWidth * 1.1) {
            this._lastPaintedSquareWidth = this._squareWidth;
            this.updateBackground();
        }
    }

    @Override
    public void boardMarkingChanged(BoardMarks boardMarks) {
        this._externalBoardMarkings = boardMarks;
        this.invalidate();
    }

    @Override
    public void boardSettingsChanged() {
        this._flagBoardSettingsChanged = true;
        if (this.getVisibility() == 0) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    BoardView.this.rereadSettings();
                    BoardView.this._flagBoardSettingsChanged = false;
                }
            });
        }
    }

    public void bringChildToFront(View view) {
        super.bringChildToFront(view);
        view = this._arrowsLayer;
    }

    public void clearCachedBitmaps() {
        synchronized (this) {
            if (this._squaresBitmap != null) {
                this._background = null;
                this._squaresBitmap.recycle();
                this._squaresBitmap = null;
            }
            return;
        }
    }

    public ImageView findImageViewForSquare(Square square) {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (view.getTag() == null || !view.getTag().equals(square)) continue;
            return (ImageView)view;
        }
        return null;
    }

    public void flip() {
        this.flip(this.getPerspective() ^ true);
    }

    public void flip(boolean bl) {
        this._perspective = bl;
        this.setBoardPosition(this._currentPosition);
        this.invalidate();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected Rect getBounds(int n, int n2) {
        if (this.getPerspective()) {
            n2 = 7 - n2;
            do {
                return super.getBounds(n, n2);
                break;
            } while (true);
        }
        n = 7 - n;
        return super.getBounds(n, n2);
    }

    public Drawable getDrawableForPiece(Piece piece) {
        int n = this._boardViewSettings.getDrawableIdForPiece(piece);
        if (n != -1) {
            return this._context.getResources().getDrawable(n);
        }
        return null;
    }

    protected ImageView getImageViewForPiece(PieceInformation pieceInformation) {
        ImageView imageView = new ImageView(this._context);
        imageView.setImageDrawable(this.getPositionedDrawableForPiece(pieceInformation));
        imageView.setTag((Object)pieceInformation.getSquare());
        return imageView;
    }

    protected Square getNextMarkedSquareForCoordinatesForActivePiece(float f, float f2, float f3) {
        f3 = (float)(Math.sqrt(Math.pow(this.getSquareWidth() / 2.0f, 2.0) * 2.0) * (double)f3);
        Iterator<Square> iterator = this._activePieceMoveSquares.iterator();
        Square square = null;
        while (iterator.hasNext()) {
            Square square2 = iterator.next();
            float f4 = (float)Math.sqrt(Math.pow(f - this.getSquareCenterX(square2), 2.0) + Math.pow(f2 - this.getSquareCenterY(square2), 2.0));
            if (f4 >= f3) continue;
            square = square2;
            f3 = f4;
        }
        return square;
    }

    public boolean getPerspective() {
        return this._perspective;
    }

    public Position getPosition() {
        return this._currentPosition;
    }

    public float getSquareCenterX(Square square) {
        if (this.getPerspective()) {
            return this._squareWidth * ((float)square.getColumn() + 0.5f);
        }
        return this._squareWidth * (7.5f - (float)square.getColumn());
    }

    public float getSquareCenterY(Square square) {
        if (this.getPerspective()) {
            return this._squareHeight * (7.5f - (float)square.getRow());
        }
        return this._squareHeight * ((float)square.getRow() + 0.5f);
    }

    public Square getSquareForCoordinates(float f, float f2) {
        int n = this.getPerspective() ? 7 - (int)Math.floor(f2 / this._squareHeight) : (int)Math.floor(f2 / this._squareHeight);
        int n2 = this.getPerspective() ? (int)Math.floor(f / this._squareWidth) : 7 - (int)Math.floor(f / this._squareWidth);
        return Square.instanceForRowAndColumn(n, n2);
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

    public void markSquare(Square square, int n) {
        this._localMarkings.getSquareMarkings().addSquareMark(square, n);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int n = 0;
        Rect rect = this.getBounds(0, 0, 8, 8);
        this._markerPaint.setARGB(128, 200, 50, 50);
        if (this._background == null) {
            if (this._squaresBitmap == null) {
                this.createBackgroundBitmap();
            }
            this._background = new BitmapDrawable(this.getResources(), this._squaresBitmap);
        }
        this._background.setBounds(rect);
        this._background.draw(canvas);
        rect = Square.getAllSquares64();
        while (n < ((Square[])rect).length) {
            Square square = rect[n];
            Rect rect2 = this.getBounds(square.getColumn(), square.getRow());
            this._colors.clear();
            List<Integer> list = this._localMarkings.getSquareMarkings().getMarkingsForSquare(square);
            if (list != null) {
                this._colors.addAll(list);
            }
            if (this._externalBoardMarkings != null && (list = this._externalBoardMarkings.getSquareMarkings().getMarkingsForSquare(square)) != null) {
                this._colors.addAll(list);
            }
            for (Integer n2 : this._colors) {
                this._markerPaint.setColor(n2.intValue());
                this._markerPaint.setAlpha(80);
                canvas.drawRect(rect2, this._markerPaint);
            }
            if (this._boardViewSettings.isMarkMoveSquaresModeEnabled() && this._activePieceMoveSquares != null && this._activePieceMoveSquares.contains(square) && this._activePieceSquare != null && this._currentPosition.getPieceFor(this._activePieceSquare) != null) {
                if (this._currentPosition.getPieceFor(this._activePieceSquare).getColor() == this._currentPosition.getActiveColor()) {
                    this._markerPaint.setColor(this._markColorUsualMove);
                } else {
                    this._markerPaint.setColor(this._markColorPremove);
                }
                this._markerPaint.setAlpha(80);
                canvas.drawRect(rect2, this._markerPaint);
            }
            ++n;
        }
        if (this._moveSquare != null) {
            rect = this.getBounds(this._moveSquare.getColumn(), this._moveSquare.getRow());
            canvas.drawCircle((float)rect.centerX(), (float)rect.centerY(), (float)rect.width() * 1.41f, this._markerPaint);
        }
        if (this._movePoint != null) {
            float f = this.getLowDistance(this._movePoint.x, this._movePoint.y) / this._squareWidth;
            this._markerPaint.setAlpha((int)(128.0f - 12.0f * f));
            canvas.drawCircle(this._movePoint.x, this._movePoint.y, this._squareWidth * 1.41f, this._markerPaint);
        }
        super.onDraw(canvas);
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && this._promotionPresenter != null && this._promotionPresenter.isPromotionShown()) {
            this._promotionPresenter.dismissDialog();
            return true;
        }
        return false;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this._squareWidth = (float)n / 8.0f;
        this._squareHeight = (float)n2 / 8.0f;
        if (!this._zooming) {
            this.updateBackgroundIfNeeded();
        }
    }

    public boolean onTouchEvent(MotionEvent object) {
        if (this._allowBlackMoves || this._allowWhiteMoves || this._allowBlackPremoves || this._allowWhitePremoves) {
            float f = object.getX();
            float f2 = object.getY();
            Square square = this.getSquareForCoordinates(f, f2);
            Piece piece = this._currentPosition.getPieceFor(square);
            switch (object.getAction()) {
                default: {
                    break;
                }
                case 2: {
                    this._probableSquare = this.getNextMarkedSquareForCoordinatesForActivePiece(f, f2, 2.0f);
                    if (this._probableSquare != null && this._activePieceSquare != null && !this._activePieceSquare.equals(square)) {
                        this.setMoveSquare(this._probableSquare);
                        this._movePoint = null;
                    } else {
                        this.setMovePoint(f, f2);
                        this.setMoveSquare(null);
                    }
                    this.invalidate();
                    break;
                }
                case 1: {
                    this._movePoint = null;
                    if (this._activePieceSquare != null) {
                        object = this._currentPosition.getPieceFor(this._activePieceSquare);
                        this._probableSquare = this.getNextMarkedSquareForCoordinatesForActivePiece(f, f2, 2.0f);
                        if (this._probableSquare != null) {
                            if (object != null) {
                                if (object.getColor() == this._currentPosition.getActiveColor()) {
                                    if ((this._currentPosition.getActiveColor() && this._allowWhiteMoves || !this._currentPosition.getActiveColor() && this._allowBlackMoves) && !this._activePieceSquare.equals(square) && this._currentPosition.isMovePossible(this._activePieceSquare, this._probableSquare)) {
                                        this._isPromotion = this._currentPosition.isPromotion((Piece)object, this._activePieceSquare, this._probableSquare);
                                        boolean bl = this._boardViewSettings.isAutoQueenEnabled();
                                        if (this._isPromotion && !bl) {
                                            this.showPromotionView(this._currentPosition.getActiveColor());
                                        } else {
                                            int n;
                                            int n2 = n = 0;
                                            if (this._isPromotion) {
                                                n2 = n;
                                                if (bl) {
                                                    n2 = 3;
                                                }
                                            }
                                            this.doMove(this.createMove(this._activePieceSquare, this._probableSquare, n2));
                                        }
                                    }
                                } else if (this._boardViewSettings.isPremoveEnabled() && (!this._currentPosition.getActiveColor() && this._allowWhitePremoves || this._currentPosition.getActiveColor() && this._allowBlackPremoves) && !this._activePieceSquare.equals(square)) {
                                    if (this._moveExecutor != null) {
                                        this._moveExecutor.registerPremove(this._activePieceSquare, this._probableSquare);
                                    }
                                    object = new Arrow(new SEP(this._activePieceSquare, this._probableSquare), ArrowCategory.USER_MARKS, this._markColorPremove);
                                    this._localMarkings.getArrowContainer().addArrow((Arrow)object);
                                    this.resetActivePiece();
                                }
                            }
                        } else if (square == null || !square.equals(this._activePieceSquare)) {
                            this.resetActivePiece();
                        }
                    }
                    this._probableSquare = null;
                    this.invalidate();
                    break;
                }
                case 0: {
                    if (square.equals(this._activePieceSquare)) {
                        this.resetActivePiece();
                    } else {
                        if (this._currentPosition.getActiveColor() && this._allowWhiteMoves || !this._currentPosition.getActiveColor() && this._allowBlackMoves) {
                            if (this._activePieceSquare != null) {
                                this._probableSquare = this.getNextMarkedSquareForCoordinatesForActivePiece(f, f2, 2.0f);
                                if (!square.equals(this._activePieceSquare) && piece != null && piece.getColor() == this._currentPosition.getActiveColor()) {
                                    this.resetActivePiece();
                                } else {
                                    this.setMoveSquare(this._probableSquare);
                                }
                            } else if (piece != null && piece.getColor() == this._currentPosition.getActiveColor()) {
                                this.setActivePieceSquare(square);
                            } else {
                                this.resetActivePiece();
                            }
                        }
                        if (this._boardViewSettings.isPremoveEnabled() && (!this._currentPosition.getActiveColor() && this._allowWhitePremoves || this._currentPosition.getActiveColor() && this._allowBlackPremoves) && this._activePieceSquare == null && piece != null && piece.getColor() != this._currentPosition.getActiveColor()) {
                            if (this._moveExecutor != null) {
                                this._moveExecutor.registerPremove(null, null);
                            }
                            this._localMarkings.getArrowContainer().removeAllArrows();
                            this.setActivePieceSquare(square);
                        }
                    }
                    this.invalidate();
                }
            }
        }
        return true;
    }

    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        if (this._flagBoardSettingsChanged && n == 0 && view == this) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    BoardView.this.rereadSettings();
                }
            });
            this._flagBoardSettingsChanged = false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void positionChanged(Position var1_1, final Move var2_3) {
        block6 : {
            // MONITORENTER : this
            var5_4 = true;
            var4_5 = var2_3 != null;
            if (var2_3 != null) ** GOTO lbl9
            this.setPosition(var1_1);
            var3_6 = false;
            break block6;
lbl9: // 1 sources:
            var6_7 = var2_3.getParent();
            var3_6 = var6_7 != null && var6_7.getResultingPosition().equals(this._currentPosition) != false;
            if (var3_6) {
                this._currentPosition = var2_3.getResultingPosition();
            } else {
                this.setPosition(var1_1);
                var4_5 = false;
            }
        }
        if (!var4_5 || !var3_6) {
            var5_4 = false;
        }
        this.post(new Runnable(){

            @Override
            public void run() {
                Object object;
                BoardView.this._localMarkings.clear();
                if (var2_3 != null && BoardView.this._boardViewSettings.isArrowOfLastMoveEnabled()) {
                    object = new Arrow(var2_3.getSEP(), ArrowCategory.LAST_MOVE, Color.argb((int)110, (int)255, (int)255, (int)255));
                    BoardView.this._localMarkings.getArrowContainer().addArrow((Arrow)object);
                    BoardView.this._arrowsLayer.invalidate();
                }
                object = BoardView.this._activePieceSquare;
                BoardView.this.resetActivePiece();
                if (object != null && var2_3 != null && (BoardView.this._allowWhitePremoves && var2_3.getPiece().isBlack() || BoardView.this._allowBlackPremoves && var2_3.getPiece().isWhite())) {
                    BoardView.this.setActivePieceSquare((Square)object);
                    BoardView.this.setMoveSquare(BoardView.this._probableSquare);
                }
                if (var4_5) {
                    BoardView.this.animateMove(var2_3, BoardView.this._boardViewSettings.getMoveTimeInMills(), null);
                }
                if (var2_3 != null && var5_4) {
                    BoardView.this.playMoveSound(var2_3);
                }
            }
        });
        // MONITOREXIT : this
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
    public void restoreState(Bundle bundle) {
        this.flip(bundle.getBoolean(BOARDVIEW_PERSPECTIVE, true));
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putBoolean(BOARDVIEW_PERSPECTIVE, this._perspective);
    }

    public void setActivePieceSquare(Square square) {
        Piece piece;
        this._activePieceMoveSquares.clear();
        if (this._activePieceSquare != null) {
            this._localMarkings.getSquareMarkings().removeSquareMark(this._activePieceSquare);
        }
        this._activePieceSquare = null;
        if (square != null && (piece = this._currentPosition.getPieceFor(square)) != null) {
            if (piece.getColor() == this._currentPosition.getActiveColor()) {
                this._activePieceMoveSquares.addAll(piece.getAllMoves(this._currentPosition.getSafePosition(), square));
            } else {
                this._activePieceMoveSquares.addAll(this.getPossiblePremoveSquares(piece, this._currentPosition.getSafePosition(), square));
            }
            if (!this._activePieceMoveSquares.isEmpty()) {
                this._activePieceSquare = square;
                if (piece.getColor() == this._currentPosition.getActiveColor()) {
                    this._localMarkings.getSquareMarkings().addSquareMark(square, this._markColorUsualMove);
                } else {
                    this._localMarkings.getSquareMarkings().addSquareMark(square, this._markColorPremove);
                }
            } else if (this._currentPosition.isCheck()) {
                this._localMarkings.getSquareMarkings().addSquareMark(this._currentPosition.getKingSquareForColor(piece.getColor()), -65536);
            }
        }
        this.invalidate();
    }

    public void setBlackMoveable(boolean bl) {
        this._allowBlackMoves = bl;
    }

    public void setBlackPreMoveable(boolean bl) {
        this._allowBlackPremoves = bl;
    }

    public void setBoardViewSettings(BoardViewSettings boardViewSettings) {
        this._boardViewSettings = boardViewSettings;
    }

    public void setIsZooming(boolean bl) {
        if (this._zooming != bl) {
            this.updateBackgroundIfNeeded();
        }
        this._zooming = bl;
    }

    public void setMovable(boolean bl) {
        this._allowWhiteMoves = bl;
        this._allowBlackMoves = bl;
    }

    public void setMoveExecutor(MoveExecutor moveExecutor) {
        this._moveExecutor = moveExecutor;
    }

    protected void setMovePoint(float f, float f2) {
        this._movePoint = new PointF(f, f2);
    }

    public void setMoveSquare(Square square) {
        this._moveSquare = square;
    }

    public void setPlaySounds(boolean bl) {
        this._soundOn = bl;
    }

    public void setPosition(final Position position) {
        synchronized (this) {
            this._currentPosition = position;
            this.post(new Runnable(){

                @Override
                public void run() {
                    BoardView.this.stopMovement(position);
                    BoardView.this.setBoardPosition(position);
                    BoardView.this.resetActivePiece();
                    BoardView.this._localMarkings.clear();
                    BoardView.this.invalidate();
                }
            });
            return;
        }
    }

    public void setPremovable(boolean bl) {
        this._boardViewSettings.setPremoveEnabled(bl);
        this._allowWhitePremoves = bl;
        this._allowBlackPremoves = bl;
    }

    public void setPromotionPresenter(PromotionPresenter promotionPresenter) {
        this._promotionPresenter = promotionPresenter;
    }

    public void setWhiteMoveable(boolean bl) {
        this._allowWhiteMoves = bl;
    }

    public void setWhitePreMoveable(boolean bl) {
        this._allowWhitePremoves = bl;
    }

    public void showPromotionView(boolean bl) {
        this._promotionSquare = this._probableSquare;
        Object object = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427525, null);
        ImageView imageView = (ImageView)object.findViewById(2131296885);
        ImageView imageView2 = (ImageView)object.findViewById(2131296883);
        ImageView imageView3 = (ImageView)object.findViewById(2131296884);
        ImageView imageView4 = (ImageView)object.findViewById(2131296886);
        Resources resources = this.getResources();
        BoardViewSettings boardViewSettings = this._boardViewSettings;
        int n = boardViewSettings.getDrawableIdForPiece(Queen.instanceForColor(bl));
        int n2 = boardViewSettings.getDrawableIdForPiece(Bishop.instanceForColor(bl));
        int n3 = boardViewSettings.getDrawableIdForPiece(Knight.instanceForColor(bl));
        int n4 = boardViewSettings.getDrawableIdForPiece(Rook.instanceForColor(bl));
        imageView.setImageDrawable(resources.getDrawable(n));
        imageView2.setImageDrawable(resources.getDrawable(n2));
        imageView3.setImageDrawable(resources.getDrawable(n3));
        imageView4.setImageDrawable(resources.getDrawable(n4));
        this._promotionPresenter.showPromotionDialog((View)object);
        object = new PromotionClickListener();
        imageView.setOnClickListener((View.OnClickListener)object);
        imageView3.setOnClickListener((View.OnClickListener)object);
        imageView2.setOnClickListener((View.OnClickListener)object);
        imageView4.setOnClickListener((View.OnClickListener)object);
    }

    public void stopMovement(Position position) {
        if (this._animatedMovingPiece != null) {
            this._nextAnimations.clear();
            this._animatedMovingPiece.clearAnimation();
            this._animatedMovingPiece = null;
            this.setBoardPosition(position);
        }
    }

    public void updateBackground() {
        synchronized (this) {
            this.clearCachedBitmaps();
            this.createBackgroundBitmap();
            return;
        }
    }

    private class ArrowView
    extends View {
        Set<Arrow> arrows;
        private Paint markerPaint;
        private Path path;
        private Point point1;
        private Point point2;
        private Point point3;
        private Point wayFromStartToFinish;

        public ArrowView(Context context) {
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

        protected void onDraw(Canvas canvas) {
            this.arrows.clear();
            Collection<Arrow> collection = BoardView.this._localMarkings.getArrowContainer().getAllArrows();
            if (collection != null) {
                this.arrows.addAll(collection);
            }
            if (BoardView.this._externalBoardMarkings != null && (collection = BoardView.this._externalBoardMarkings.getArrowContainer().getAllArrows()) != null) {
                this.arrows.addAll(collection);
            }
            for (Arrow arrow : this.arrows) {
                int n = arrow.getColor();
                this.markerPaint.setColor(Color.argb((int)128, (int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n)));
                Serializable serializable = arrow.getSep();
                Square square = serializable.getStartSquare();
                serializable = serializable.getEndSquare();
                Rect object = BoardView.this.getBounds(square.getColumn(), square.getRow());
                serializable = BoardView.this.getBounds(serializable.getColumn(), serializable.getRow());
                n = object.width();
                this.markerPaint.setStrokeWidth((float)Math.max(n / 7, 1));
                this.markerPaint.setStyle(Paint.Style.FILL);
                this.markerPaint.setAntiAlias(true);
                this.wayFromStartToFinish.x = serializable.centerX() - object.centerX();
                this.wayFromStartToFinish.y = serializable.centerY() - object.centerY();
                double d = Math.sqrt(this.wayFromStartToFinish.x * this.wayFromStartToFinish.x + this.wayFromStartToFinish.y * this.wayFromStartToFinish.y);
                float f = (float)false;
                d = (double)n / d * (double)3;
                this.wayFromStartToFinish.x = (int)Math.round((double)this.wayFromStartToFinish.x * d);
                this.wayFromStartToFinish.y = (int)Math.round((double)this.wayFromStartToFinish.y * d);
                this.point1.x = serializable.centerX() + (int)((float)(this.wayFromStartToFinish.x / 8) * f);
                this.point1.y = serializable.centerY() + (int)((float)(this.wayFromStartToFinish.y / 8) * f);
                this.point2.x = serializable.centerX() - this.wayFromStartToFinish.y / 8 - this.wayFromStartToFinish.x / 5;
                this.point2.y = serializable.centerY() + this.wayFromStartToFinish.x / 8 - this.wayFromStartToFinish.y / 5;
                this.point3.x = serializable.centerX() + this.wayFromStartToFinish.y / 8 - this.wayFromStartToFinish.x / 5;
                this.point3.y = serializable.centerY() - this.wayFromStartToFinish.x / 8 - this.wayFromStartToFinish.y / 5;
                this.path.reset();
                this.path.setFillType(Path.FillType.EVEN_ODD);
                this.path.moveTo((float)this.point1.x, (float)this.point1.y);
                this.path.lineTo((float)this.point2.x, (float)this.point2.y);
                this.path.lineTo((float)this.point3.x, (float)this.point3.y);
                this.path.lineTo((float)this.point1.x, (float)this.point1.y);
                this.path.close();
                canvas.drawLine((float)object.centerX(), (float)object.centerY(), (float)(serializable.centerX() - this.wayFromStartToFinish.x / 5), (float)(serializable.centerY() - this.wayFromStartToFinish.y / 5), this.markerPaint);
                canvas.drawPath(this.path, this.markerPaint);
            }
        }
    }

    public static interface BoardViewSettings {
        public int getDrawableIdForPiece(Piece var1);

        public int getDrawableIdForSquare(Square var1);

        public int getMoveTimeInMills();

        public boolean isArrowOfLastMoveEnabled();

        public boolean isAutoQueenEnabled();

        public boolean isMarkMoveSquaresModeEnabled();

        public boolean isPlayingMoveSounds();

        public boolean isPremoveEnabled();

        public void setAutoQueenEnabled(boolean var1);

        public void setMoveTimeInMills(int var1);

        public void setPremoveEnabled(boolean var1);
    }

    private static class MoveAnimationInformationHolder {
        public Animation.AnimationListener listener;
        public Move move;
        public int timeInMills;

        public MoveAnimationInformationHolder(Move move, Animation.AnimationListener animationListener, int n) {
            this.move = move;
            this.listener = animationListener;
            this.timeInMills = n;
        }
    }

    private class MyAnimationListenerImageView
    extends ImageView {
        private Animation _animation;
        private Animation.AnimationListener _externalListener;
        public Move _move;

        public MyAnimationListenerImageView(Move move, Animation.AnimationListener animationListener, Context context, Animation animation) {
            super(context);
            this._externalListener = animationListener;
            this._animation = animation;
            this._move = move;
        }

        public void clearAnimation() {
            synchronized (this) {
                this._animation.cancel();
                super.clearAnimation();
                BoardView.this.getUiThreadHandler().postAtFrontOfQueue(new Runnable(){

                    @Override
                    public void run() {
                        MyAnimationListenerImageView.this.setVisibility(4);
                    }
                });
                return;
            }
        }

        protected void onAnimationEnd() {
            synchronized (this) {
                super.onAnimationEnd();
                if (this._externalListener != null) {
                    this._externalListener.onAnimationEnd(this._animation);
                }
                BoardView.this.getUiThreadHandler().postAtFrontOfQueue(new Runnable(){

                    @Override
                    public void run() {
                        BoardView.this.setBoardPosition(MyAnimationListenerImageView.this._move.getResultingPieceSetup());
                    }
                });
                BoardView.this._animatedMovingPiece = null;
                return;
            }
        }

        public void onAnimationStart() {
            synchronized (this) {
                super.onAnimationStart();
                if (this._externalListener != null) {
                    this._externalListener.onAnimationStart(this._animation);
                }
                return;
            }
        }

    }

    private class PromotionClickListener
    implements View.OnClickListener {
        private PromotionClickListener() {
        }

        public void onClick(View object) {
            switch (object.getId()) {
                default: {
                    object = null;
                    break;
                }
                case 2131296886: {
                    object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 0);
                    break;
                }
                case 2131296885: {
                    object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 3);
                    break;
                }
                case 2131296884: {
                    object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 1);
                    break;
                }
                case 2131296883: {
                    object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 2);
                }
            }
            BoardView.this._promotionPresenter.dismissDialog();
            BoardView.this.doMove((Move)object);
        }
    }

    public static interface PromotionPresenter {
        public void dismissDialog();

        public boolean isPromotionShown();

        public void showPromotionDialog(View var1);
    }

}
