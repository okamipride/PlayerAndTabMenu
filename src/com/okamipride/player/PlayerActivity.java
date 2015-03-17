package com.okamipride.player;
import java.io.IOException;

import com.okamipride.player.R;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class PlayerActivity extends Activity {
	
	private final static String TAG = "PlayerActivity";
	private MediaPlayer mediaPlayer;
	private SurfaceHolder surfHolder;
	private SurfaceView vidSurface;
	private PlayerState state;
	private VideoInfo videoInfo;
	//private MenuView viewL;
	private MenuTabView viewL;

	
	public class  MediaType {
        public final static int LOCAL_VIDEO = 0x01;
        public final static int YOUTUBE = 0x02;
        public final static int DAILYMOTION = 0x03;
        public final static int YOUKU = 0x04;
        public final static int DIRECT_STREAM = 5;
    }
	
	private class VideoInfo {
        public volatile int channelId;
        public volatile String curVideoHash;
        public volatile int curVideoId;
        public volatile int mediaType = MediaType.LOCAL_VIDEO;
        public volatile String cover;
        public volatile int playtime = 0;
    }
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"onCreate");
		setContentView(R.layout.activity_player);
		state = new PlayerState();
	    videoInfo= new VideoInfo();
	    makeDefaultVideoInfo();
	   // viewL = (MenuView) findViewById(R.id.menulist);
	    viewL = (MenuTabView) findViewById(R.id.menulist);
	    viewL.setVisibility(View.VISIBLE);    
	}

	@Override
	protected void onStart() {
		Log.d(TAG,"onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG,"onResume");
		vidSurface = (SurfaceView) findViewById(R.id.surf_player);
		surfHolder = vidSurface.getHolder();
		surfHolder.addCallback(mSurfaceHolderCallback);
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG,"onPause");
		playerRelease();
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG,"onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG,"onDestroy");
		super.onDestroy();
	}
	
		
	SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, ":surfaceCreated"); 
            prepareVideo();
            /*
            vidSurface.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				if (viewL.getVisibility()==View.VISIBLE)
    					viewL.setVisibility(View.GONE);
    				else 
    					viewL.setVisibility(View.VISIBLE);
    				
    			}    	    	
    	    });
    	    */
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {          
            Log.d(TAG, "surfaceDestroyed");
            playerRelease();
        }
    };
    
    private void prepareVideo() {
        Log.d(TAG, ":prepareVideo : player create");
        playerRelease();
        mediaPlayer = new MediaPlayer();

        if (surfHolder != null) {

            mediaPlayer.setDisplay(surfHolder);
            state.setPlayerState(PlayerState.MediaPlayerState.IDLE);
            mediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mediaPlayer.setOnErrorListener(mOnErrorListener);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //mediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);

            switch (videoInfo.mediaType) {
                case MediaType.LOCAL_VIDEO:
                    Uri videoFromFile = Uri.parse("android.resource://" + this.getPackageName() + "/" + videoInfo.curVideoHash);
                    try {
                        mediaPlayer.setDataSource(this, videoFromFile);
                        state.setPlayerState(PlayerState.MediaPlayerState.INITIALIZED);
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case MediaType.DIRECT_STREAM:
                    try {
                        Uri directUri = Uri.parse(videoInfo.curVideoHash);
                        Log.d(TAG, "Direct Uri = " + directUri);
                        mediaPlayer.setDataSource(this, directUri);
                        state.setPlayerState(PlayerState.MediaPlayerState.INITIALIZED);
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case MediaType.YOUTUBE:
                case MediaType.DAILYMOTION:
                case MediaType.YOUKU:
                	/* Take care latter
                    Logger.d(TAG, "start parsing mediatype=" + Integer.toString(videoInfo.mediaType));
                    if (parserTask != null) {
                        parserTask.cancel(true);
                    }
                    parserTask = new ParserAsyncTask();
                    parserTask.execute();
                    Logger.d(TAG, "prepareVideo:End");
                    */
                    break;
              
                default:
                	//None
                    showErrorMsg("MediaType Unexcepted");
                    break;
            }
        } else {
            Log.e(TAG, "Video Prepared but surface destroyed!");
        }
    }
    
  //-- Help function Start
    private void playerRelease() {
        Log.d(TAG, ":playerRelease");
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        state.setPlayerState(PlayerState.MediaPlayerState.END);
    }
    private void showErrorMsg(String error) {
        Log.d(TAG, ":showErrorMsg");
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
  //--Help functions End
  //--Mediaplayer Listener start  
    MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, ":onPrepared");

            mp.setScreenOnWhilePlaying(true);
            state.setPlayerState(PlayerState.MediaPlayerState.PREPARED);
            if (videoInfo.playtime > 0) {
                mp.seekTo(videoInfo.playtime);
            }
            mp.start();
            Log.d(TAG,"mediaplayer start ");
            state.setPlayerState(PlayerState.MediaPlayerState.PLAYING);
        }
    };
    
    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.d(TAG, ":onCompletion:type:" + state.getPlayerType());
            state.setPlayerState(PlayerState.MediaPlayerState.COMPELETE);
            //TODO Play Next Video
            // playNextVideo();
        }
    };

    MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            state.setPlayerState(PlayerState.MediaPlayerState.ERROR);
            Log.e(TAG, "onError what=" + Integer.toString(what) + " extra= " + Integer.toString(extra));
            switch (what) {
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    //TODO: Create a new One mediaPlayer
                    showErrorMsg("MEDIA_ERROR_SERVER_DIED");
                    Log.e(TAG, "onError: MEDIA_ERROR_SERVER_DIED");
                    break;
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    showErrorMsg("MEDIA_ERROR_UNKNOWN");
                    mediaPlayer.reset();
                    Log.e(TAG, "onError: MEDIA_ERROR_UNKNOWN");
                    break;
            }
            switch (extra) {
                case MediaPlayer.MEDIA_ERROR_IO:
                    showErrorMsg("MEDIA_ERROR_IO");
                    Log.e(TAG, "onError: MEDIA_ERROR_IO");
                    break;
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    showErrorMsg("MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                    Log.e(TAG, "onError: MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                    break;
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    showErrorMsg("MEDIA_ERROR_MALFORMED");
                    Log.e(TAG, "onError: MEDIA_ERROR_MALFORMED");
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    showErrorMsg("MEDIA_ERROR_TIMED_OUT");
                    Log.e(TAG, "onError: MEDIA_ERROR_TIMED_OUT");
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    showErrorMsg("MEDIA_ERROR_UNSUPPORTED");
                    Log.e(TAG, "onError: MEDIA_ERROR_UNSUPPORbTED");
                    break;
            }
            return false;
        }
    };
  //--Mediaplayer Listener End 
    
    //-- Fake Data 
    private void makeDefaultVideoInfo() {
    	if(videoInfo != null) {
	    	videoInfo.mediaType = MediaType.DIRECT_STREAM;
	  	    videoInfo.channelId = 20;
	  	    videoInfo.curVideoId = 8;
	  	    videoInfo.cover =   "http://24-7kpop.com/wp-content/uploads/2014/02/Screen-Shot-2014-02-23-at-12.10.24-AM1.png";
	  	    videoInfo.curVideoHash = "http://catdc.livecache.org:8081/freetv/gmmone/playlist.m3u8";
    	}
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		switch (keyCode) {	
			case KeyEvent.KEYCODE_1:
				Log.d(TAG, "onKeyDown KEYCODE_1 ");
				if (viewL.getVisibility()!= View.VISIBLE) {
					viewL.setVisibility(View.VISIBLE);
					viewL.requestFocus();
					//viewL.initFocus();
				} else { 
					viewL.setVisibility(View.GONE);
				}
				viewL.requestFocus();
			
				break;
			case KeyEvent.KEYCODE_2:
				Log.d(TAG, "onKeyDown KEYCODE_2 ");
				viewL.setVisibility(View.GONE);
			case KeyEvent.KEYCODE_3:
				Log.d(TAG, "onKeyDown KEYCODE_2 ");
				  Intent intent = new Intent(this, NewActivity.class);
				  startActivity(intent);
			case KeyEvent.KEYCODE_4:
				Log.d(TAG,"onKeyDown KEYCODE_4");
				viewL.setVisibility(View.GONE);
				vidSurface.getResources();
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				Log.d(TAG,"onKeyDown KEYCODE_DPAD_UP");
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				Log.d(TAG,"onKeyDown KEYCODE_DPAD_DOWN");
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				Log.d(TAG,"onKeyDown KEYCODE_DPAD_LEFT");
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				Log.d(TAG,"onKeyDown KEYCODE_DPAD_RIGHT");
				break;
			default:
				Log.d(TAG, "onKeyDown event = " + event.toString());
				break;		
		}
		return super.onKeyDown(keyCode, event);
	}
    
    
    
}
