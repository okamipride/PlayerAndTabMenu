package com.okamipride.player;
import android.util.Log;

/**
 * Created by rachael on 2014/9/13.
 */
public class PlayerState {

    private final static String TAG = "PlayerState";

    public static class MediaPlayerState {

        public final static int ERROR = 0x00;
        public final static int IDLE = 0x01;
        public final static int INITIALIZED = 0x02;
        public final static int PREPARED = 0x03;
        public final static int STOPPED = 0x04;
        public final static int PLAYING = 0x05;
        public final static int PAUSED = 0x06;
        public final static int COMPELETE = 0x07;
        public final static int END = 0x08;
     }

    public static class VideoViewState {

        public final static int COVER_FRONT = 1 << 0;
        public final static int VIDEO_FRONT = 1 << 1;
        public final static int CONTROLLER_CTRL_SHOW = 1 << 2;
        public final static int CONTROLLER_LIST_SHOW = 1 << 3;
        public final static int PLAY_BUTTON_SHOW = 0x40; //0100 0000
     }

    public static class PlayerType {

        public final static int PREVIEW = 0x01;
        public final static int NORMAL = 0x02;
    }

    public static class ControllerType {

         public final static int SIMPLE = 0x01;
         public final static int CLASSIC = 0x02;
     }

    private int playerState = 0;
    private int viewState = 0;
    private int controllerType = ControllerType.CLASSIC;
    private int playerType = PlayerType.NORMAL;

    public int getControllerType() {
        return controllerType;
    }

    public int getPlayerType() {
        return playerType;
    }

    public void setControllerType(int controllerType) {
        if (controllerType != ControllerType.SIMPLE) {
            this.controllerType = ControllerType.CLASSIC;
        } else {
            this.controllerType = ControllerType.SIMPLE;
        }
    }

    public void setPlayerType(int type) {
        if (type == PlayerType.PREVIEW) {
            this.playerType = PlayerType.PREVIEW;
        } else {
            this.playerType = PlayerType.NORMAL;
        }
    }

    public int getPlayerState() {
        return playerState;
    }

    public void setPlayerState(int playerState) {
        this.playerState = playerState;
    }

    public int getViewState() {
        return viewState;
    }

    public void setSimpleCtrlState(boolean on) {
        if (on) {
            viewState = viewState | VideoViewState.PLAY_BUTTON_SHOW;
        } else {
            viewState = viewState & (~VideoViewState.PLAY_BUTTON_SHOW);
        }
    }

    public void setCtrlShowState(boolean on) {
        if (on) {
            viewState = viewState | VideoViewState.CONTROLLER_CTRL_SHOW;
        } else {
            viewState = viewState & (~VideoViewState.CONTROLLER_CTRL_SHOW);
        }
    }

    public void setVideoFrontState(boolean on) {
        if (on) {
            viewState = VideoViewState.VIDEO_FRONT;
        }
    }

    public void setCoverFrontState(boolean on) {
        if (on) {
            viewState = VideoViewState.COVER_FRONT;
        }
    }

    public boolean isVideoFront() {
        int ret =  viewState & VideoViewState.VIDEO_FRONT;
        if (ret > 0)
            return true;
        else
            return false;
    }

    public boolean isCoverFront() {
        int ret =  viewState & VideoViewState.COVER_FRONT;
        if (ret > 0)
            return true;
        else
            return false;
    }

    public boolean isControllerShow() {
        int ret1 =  viewState & VideoViewState.CONTROLLER_CTRL_SHOW;
        int ret2 = viewState & VideoViewState.CONTROLLER_LIST_SHOW;
        if (ret1 > 0 || ret2 >0 )
            return true;
        else
            return false;
    }

    public boolean isSimpleCtrlShow() {
        int ret =  viewState & VideoViewState.PLAY_BUTTON_SHOW;
        if (ret > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void logCurrentViewState() {
        String retState = "";
        if ((viewState & VideoViewState.COVER_FRONT) != 0) {
            retState = retState + " COVER_FRONT ";
        }
        if ((viewState & VideoViewState.VIDEO_FRONT) != 0) {
            retState = retState + " VIDEO_FRONT ";
        }
        if ((viewState & VideoViewState.CONTROLLER_CTRL_SHOW) != 0) {
            retState = retState + " CONTROLLER_CTRL_SHOW ";
        }

        if ((viewState & VideoViewState.CONTROLLER_LIST_SHOW) != 0) {
            retState = retState + " CONTROLLER_LIST_SHOW ";
        }

        if ((viewState & VideoViewState.PLAY_BUTTON_SHOW) != 0) {
            retState = retState + " CHANNEL_INFO_SHOW ";
        }
        Log.i(TAG, retState);
    }

    public void logCurrentMediaPlayerState() {
        String retState = "";
        switch (playerState) {
            case MediaPlayerState.ERROR:
                retState = "ERROR";
                break;
            case MediaPlayerState.IDLE:
                retState = "IDLE";
                break;
            case MediaPlayerState.PREPARED:
                retState = "PREPARED";
                break;
            case MediaPlayerState.STOPPED:
                retState = "STOPPED";
                break;
            case MediaPlayerState.PLAYING:
                retState = "PLAYING";
                break;
            case MediaPlayerState.PAUSED:
                retState = "PAUSED";
                break;
            case MediaPlayerState.COMPELETE:
                retState = "PLAYBACK_COMPELETE";
                break;
            case MediaPlayerState.END:
                retState = "END";
                break;

        }
        Log.i(TAG, retState);
    }
}
