/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

class MediaSessionCompat.MediaSessionImplBase.MessageHandler
extends Handler {
    private static final int KEYCODE_MEDIA_PAUSE = 127;
    private static final int KEYCODE_MEDIA_PLAY = 126;
    private static final int MSG_ADD_QUEUE_ITEM = 25;
    private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
    private static final int MSG_ADJUST_VOLUME = 2;
    private static final int MSG_COMMAND = 1;
    private static final int MSG_CUSTOM_ACTION = 20;
    private static final int MSG_FAST_FORWARD = 16;
    private static final int MSG_MEDIA_BUTTON = 21;
    private static final int MSG_NEXT = 14;
    private static final int MSG_PAUSE = 12;
    private static final int MSG_PLAY = 7;
    private static final int MSG_PLAY_MEDIA_ID = 8;
    private static final int MSG_PLAY_SEARCH = 9;
    private static final int MSG_PLAY_URI = 10;
    private static final int MSG_PREPARE = 3;
    private static final int MSG_PREPARE_MEDIA_ID = 4;
    private static final int MSG_PREPARE_SEARCH = 5;
    private static final int MSG_PREPARE_URI = 6;
    private static final int MSG_PREVIOUS = 15;
    private static final int MSG_RATE = 19;
    private static final int MSG_RATE_EXTRA = 31;
    private static final int MSG_REMOVE_QUEUE_ITEM = 27;
    private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
    private static final int MSG_REWIND = 17;
    private static final int MSG_SEEK_TO = 18;
    private static final int MSG_SET_CAPTIONING_ENABLED = 29;
    private static final int MSG_SET_REPEAT_MODE = 23;
    private static final int MSG_SET_SHUFFLE_MODE = 30;
    private static final int MSG_SET_VOLUME = 22;
    private static final int MSG_SKIP_TO_ITEM = 11;
    private static final int MSG_STOP = 13;

    public MediaSessionCompat.MediaSessionImplBase.MessageHandler(Looper looper) {
        super(looper);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void onMediaButtonEvent(KeyEvent keyEvent, MediaSessionCompat.Callback callback) {
        if (keyEvent == null) return;
        if (keyEvent.getAction() != 0) {
            return;
        }
        long l = MediaSessionImplBase.this.mState == null ? 0L : MediaSessionImplBase.this.mState.getActions();
        int n = keyEvent.getKeyCode();
        if (n != 79) {
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            return;
                        }
                        case 127: {
                            if ((l & 2L) == 0L) return;
                            callback.onPause();
                            return;
                        }
                        case 126: 
                    }
                    if ((l & 4L) == 0L) return;
                    callback.onPlay();
                    return;
                }
                case 90: {
                    if ((l & 64L) == 0L) return;
                    callback.onFastForward();
                    return;
                }
                case 89: {
                    if ((l & 8L) == 0L) return;
                    callback.onRewind();
                    return;
                }
                case 88: {
                    if ((l & 16L) == 0L) return;
                    callback.onSkipToPrevious();
                    return;
                }
                case 87: {
                    if ((l & 32L) == 0L) return;
                    callback.onSkipToNext();
                    return;
                }
                case 86: {
                    if ((l & 1L) == 0L) return;
                    callback.onStop();
                    return;
                }
                case 85: 
            }
        }
        Log.w((String)MediaSessionCompat.TAG, (String)"KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
    }

    public void handleMessage(Message object) {
        MediaSessionCompat.Callback callback = MediaSessionImplBase.this.mCallback;
        if (callback == null) {
            return;
        }
        switch (object.what) {
            default: {
                return;
            }
            case 31: {
                callback.onSetRating((RatingCompat)object.obj, object.getData());
                return;
            }
            case 30: {
                callback.onSetShuffleMode(object.arg1);
                return;
            }
            case 29: {
                callback.onSetCaptioningEnabled((Boolean)object.obj);
                return;
            }
            case 28: {
                if (MediaSessionImplBase.this.mQueue == null) break;
                object = object.arg1 >= 0 && object.arg1 < MediaSessionImplBase.this.mQueue.size() ? MediaSessionImplBase.this.mQueue.get(object.arg1) : null;
                if (object == null) break;
                callback.onRemoveQueueItem(object.getDescription());
                return;
            }
            case 27: {
                callback.onRemoveQueueItem((MediaDescriptionCompat)object.obj);
                return;
            }
            case 26: {
                callback.onAddQueueItem((MediaDescriptionCompat)object.obj, object.arg1);
                return;
            }
            case 25: {
                callback.onAddQueueItem((MediaDescriptionCompat)object.obj);
                return;
            }
            case 23: {
                callback.onSetRepeatMode(object.arg1);
                return;
            }
            case 22: {
                MediaSessionImplBase.this.setVolumeTo(object.arg1, 0);
                return;
            }
            case 21: {
                object = (KeyEvent)object.obj;
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)object);
                if (callback.onMediaButtonEvent(intent)) break;
                this.onMediaButtonEvent((KeyEvent)object, callback);
                return;
            }
            case 20: {
                callback.onCustomAction((String)object.obj, object.getData());
                return;
            }
            case 19: {
                callback.onSetRating((RatingCompat)object.obj);
                return;
            }
            case 18: {
                callback.onSeekTo((Long)object.obj);
                return;
            }
            case 17: {
                callback.onRewind();
                return;
            }
            case 16: {
                callback.onFastForward();
                return;
            }
            case 15: {
                callback.onSkipToPrevious();
                return;
            }
            case 14: {
                callback.onSkipToNext();
                return;
            }
            case 13: {
                callback.onStop();
                return;
            }
            case 12: {
                callback.onPause();
                return;
            }
            case 11: {
                callback.onSkipToQueueItem((Long)object.obj);
                return;
            }
            case 10: {
                callback.onPlayFromUri((Uri)object.obj, object.getData());
                return;
            }
            case 9: {
                callback.onPlayFromSearch((String)object.obj, object.getData());
                return;
            }
            case 8: {
                callback.onPlayFromMediaId((String)object.obj, object.getData());
                return;
            }
            case 7: {
                callback.onPlay();
                return;
            }
            case 6: {
                callback.onPrepareFromUri((Uri)object.obj, object.getData());
                return;
            }
            case 5: {
                callback.onPrepareFromSearch((String)object.obj, object.getData());
                return;
            }
            case 4: {
                callback.onPrepareFromMediaId((String)object.obj, object.getData());
                return;
            }
            case 3: {
                callback.onPrepare();
                return;
            }
            case 2: {
                MediaSessionImplBase.this.adjustVolume(object.arg1, 0);
                return;
            }
            case 1: {
                object = (MediaSessionCompat.MediaSessionImplBase.Command)object.obj;
                callback.onCommand(object.command, object.extras, object.stub);
            }
        }
    }

    public void post(int n) {
        this.post(n, null);
    }

    public void post(int n, Object object) {
        this.obtainMessage(n, object).sendToTarget();
    }

    public void post(int n, Object object, int n2) {
        this.obtainMessage(n, n2, 0, object).sendToTarget();
    }

    public void post(int n, Object object, Bundle bundle) {
        object = this.obtainMessage(n, object);
        object.setData(bundle);
        object.sendToTarget();
    }
}
