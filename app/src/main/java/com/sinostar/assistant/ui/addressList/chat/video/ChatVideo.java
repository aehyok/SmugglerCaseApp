package com.sinostar.assistant.ui.addressList.chat.video;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.sinostar.assistant.ui.addressList.chat.Chat.RESULT_CODE_VIDEO;


/**
 * 聊天页面小视频拍摄
 */
public class ChatVideo extends BaseActivity {

    @BindView(R.id.chat_video)
    JCameraView chatVideo;
    private Context context;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_vidio);
        ButterKnife.bind(this);
        context=ChatVideo.this;
        setFullScreen();
        permission();
    }

    /**
     * 获取权限
     */
    private void permission() {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.RECORD_AUDIO}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                init();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    ToastUtil.showToast(context, "有部分权限没有授权，请授权后再进行相关操作");
                }
            }
        });
    }
    private void init() {
        //设置视频保存路径
        chatVideo.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "Camera");

        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        chatVideo.setFeatures(JCameraView.BUTTON_STATE_BOTH);

        //设置视频质量
        chatVideo.setMediaQuality(JCameraView.DRAWING_CACHE_QUALITY_HIGH);

        //JCameraView监听
        chatVideo.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //打开Camera失败回调
            }
            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
            }
        });

        chatVideo.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                String url=saveBitmap(generateFileName(),bitmap);
                compressImage(url,"");
            }
            @Override
            public void recordSuccess(String url,Bitmap firstFrame) {
                //获取视频路径
                String sdf=saveBitmap(generateFileName(),getVideoThumbnail(url,
                        192,256, MediaStore.Video.Thumbnails.MINI_KIND));
                compressImage(sdf,url);


            }
        });
        //左边按钮点击事件
        chatVideo.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                ChatVideo.this.finish();
            }
        });
        //右边按钮点击事件
        chatVideo.setRightClickListener(new ClickListener() {

            @Override
            public void onClick() {
                finish();
            }
        });


    }

    /**
     * 压缩图片
     * @param path 要压缩图片的路径
     * @param videoUrl  视频路径
     */
    private void compressImage(String path, final String videoUrl){
        Luban.with(this)
                .load(path)
                .ignoreBy(100)
//                .setTargetDir(path)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if(videoUrl!=null&&!videoUrl.equals("")){
                            intent=new Intent();
                            intent.putExtra(Constent.VIDEO_URL,videoUrl);
                            intent.putExtra(Constent.VIDEO_IMAGE_URL,file.getPath());
                            setResult(RESULT_CODE_VIDEO,intent);
                            finish();
                        }else{
                            intent=new Intent();
                            intent.putExtra(Constent.IMAGE_URL,file.getPath());
                            setResult(RESULT_CODE_VIDEO,intent);
                            finish();
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }
    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * @param videoPath 视频的路径
     * @param width 指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind 参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        if(bitmap!= null){
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        }
        return bitmap;
    }


    /**
     * 拍摄视频画面强制全屏
     */
    private void setFullScreen() {
        //全屏设置
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }
    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }


    /**
     * 保存bitmao图片
     * @param bitName
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(String bitName,Bitmap mBitmap) {
        File f = new File("/storage/emulated/0/DCIM/camera/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (Exception e) {
            return "create_bitmap_error";
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/storage/emulated/0/DCIM/camera/"  + bitName + ".png";
    }



    @Override
    protected void onResume() {
        super.onResume();
        chatVideo.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        chatVideo.onPause();
    }
}

