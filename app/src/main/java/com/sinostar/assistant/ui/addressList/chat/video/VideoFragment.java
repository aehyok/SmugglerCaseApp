package com.sinostar.assistant.ui.addressList.chat.video;


import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.FileUtil;
import com.sinostar.assistant.widget.DragImageView;
import com.sinostar.assistant.widget.DragPhotoView;

import com.sinostar.assistant.widget.DragVideoSurface;
import com.sinostar.assistant.widget.DragVideoView;
import com.sinostar.assistant.widget.LodingCircleView;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 视频、照片浏览页面
 */
public class VideoFragment extends Fragment  {


    @BindView(R.id.video_view)
    DragVideoView videoView;
    Unbinder unbinder;
    @BindView(R.id.video_star_button)
    ImageView videoStarButton;
    @BindView(R.id.video_image)
    DragImageView videoImageView;
    @BindView(R.id.video_photoview)
    DragPhotoView videoPhotoview;
    @BindView(R.id.video_texture)
    DragVideoSurface videoTexture;
    @BindView(R.id.video_loading)
    LodingCircleView videoLoading;

    private Bundle arg;

    private boolean isStarVideo;
    private Surface surface;
    private MediaPlayer mMediaPlayer;
    private EMMessage message;
    private EMVideoMessageBody videoBody;
    private EMImageMessageBody imageBody;
    private String image;
    private String personName;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arg = getArguments();
        isStarVideo = arg.getBoolean("isStarVideo");
        message = arg.getParcelable("message");
        personName=arg.getString(Constent.PERSON_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        //判断是否直接播放视频
        if (videoBody!=null&&isStarVideo && ApplicationUtil.isIsFirstPlay()) {
            starVideo(videoBody.getLocalUrl());
            ApplicationUtil.setIsFirstPlay(false);
        }else{
            ApplicationUtil.setIsFirstPlay(false);
        }
        initListener();
        videoListener();
        return view;
    }
    //
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (videoView != null && videoPhotoview.getVisibility() != View.VISIBLE) {
                videoView.stopPlayback();
                videoView.destroyDrawingCache();
                videoView.setVisibility(View.GONE);
                videoImageView.setVisibility(View.VISIBLE);
                videoStarButton.setVisibility(View.VISIBLE);
            }
        }
    }


    private void init() {


        if(message.getType()== EMMessage.Type.VIDEO){     //视频
            videoBody = (EMVideoMessageBody) message.getBody();
            videoPhotoview.setVisibility(View.GONE);
            videoImageView.setVisibility(View.VISIBLE);
            videoStarButton.setVisibility(View.VISIBLE);
           if(FileUtil.fileIsExists(videoBody.getLocalUrl())){
               MediaMetadataRetriever media = new MediaMetadataRetriever();media.setDataSource(videoBody.getLocalUrl());
               Bitmap bitmap = media.getFrameAtTime();
               videoImageView.setImageBitmap(bitmap);
           }else{
               Picasso.with(getActivity())
                       .load(videoBody.getThumbnailUrl())
                       .into(videoImageView);
           }


        }else{  //照片
            imageBody= (EMImageMessageBody) message.getBody();
            videoPhotoview.setVisibility(View.VISIBLE);
            videoImageView.setVisibility(View.GONE);
            videoStarButton.setVisibility(View.GONE);
            if (FileUtil.fileIsExists(imageBody.getLocalUrl())) {

                if(message.getFrom().equals(personName)){
                    image=imageBody.getThumbnailUrl();
                    Picasso.with(getActivity())
                            .load(image)
                            .into(videoPhotoview);
                }else{
                    image=imageBody.getLocalUrl();
                    Picasso.with(getActivity())
                            .load("file://"+image)
                            .into(videoPhotoview);
                }

            } else {
                downloadAttachment(message);
            }
        }

    }

    /**
     * 播放视频
     * @param path 视频路径
     */
    private void starVideo(String path) {
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoPath(path);
        videoTexture.setVisibility(View.VISIBLE);
        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoTexture);
        videoTexture.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
    }

    /**
     * 下载附件
     *
     * @param message
     */
    private void downloadAttachment(final EMMessage message) {
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(message.getType()== EMMessage.Type.IMAGE){
                            Picasso.with(getActivity())
                                    .load(imageBody.getLocalUrl())
                                    .into(videoPhotoview);
                        }else{
                            starVideo(videoBody.getLocalUrl());
                        }
                    }
                });

            }

            @Override
            public void onProgress(final int progress, String status) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progress==100){
                            videoLoading.setVisibility(View.GONE);
                        }else{
                            videoLoading.setVisibility(View.VISIBLE);
                            videoLoading.setProgerss(progress,true);
                        }
                    }
                });

            }

            @Override
            public void onError(int error, String msg) {

            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(message);
    }

    /**
     * 视频播放监听
     */
    private void videoListener() {
        //视频播放准备
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer1) {
                videoView.start();
                videoImageView.setVisibility(View.GONE);
                videoStarButton.setVisibility(View.GONE);
                mediaPlayer1.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            }
        });
        //视频播放完成
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
                videoImageView.setVisibility(View.VISIBLE);
                videoStarButton.setVisibility(View.VISIBLE);

            }
        });

        //视频播放出错
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });
    }

    private void initListener() {
        videoTexture.setOnExitListener(new DragVideoSurface.OnExitListener() {
            @Override
            public void onExit(DragVideoSurface view, float translateX, float translateY, float w, float h) {
                getActivity().finish();
            }
        });
        videoStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FileUtil.fileIsExists(videoBody.getLocalUrl())) {
                    starVideo(videoBody.getLocalUrl());
                } else {
                    downloadAttachment(message);
                }

            }
        });
        videoImageView.setOnMoveListener(new DragImageView.OnMoveListener() {
            @Override
            public void onMove(float translateX, float translateY, float mScale) {
                videoStarButton.setTranslationX(translateX);
                videoStarButton.setTranslationY(translateY);
                videoStarButton.setScaleX(mScale);
                videoStarButton.setScaleY(mScale);
            }
        });

        videoImageView.setOnExitListener(new DragImageView.OnExitListener() {
            @Override
            public void onExit(DragImageView view, float translateX, float translateY, float w, float h) {
                getActivity().finish();
            }
        });

        videoImageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return true;
            }
        });
        videoPhotoview.setOnTapListener(new DragPhotoView.OnTapListener() {
            @Override
            public void onTap(DragPhotoView view) {
                getActivity().finish();
            }
        });
        videoPhotoview.setOnExitListener(new DragPhotoView.OnExitListener() {
            @Override
            public void onExit(DragPhotoView view, float translateX, float translateY, float w, float h) {
                getActivity().finish();
            }
        });

    }
    public static VideoFragment newInstance(Bundle args) {
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    public void play(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {//文件不存在
                Toast.makeText(getActivity(), "文件路径错误", Toast.LENGTH_SHORT).show();
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(file.getAbsolutePath());
            mMediaPlayer.setSurface(surface);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
