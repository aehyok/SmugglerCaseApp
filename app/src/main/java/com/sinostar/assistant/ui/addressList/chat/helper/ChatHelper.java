package com.sinostar.assistant.ui.addressList.chat.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Vibrator;
import android.widget.ImageView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.utils.AppScreenMgr;
import com.sinostar.assistant.utils.CircleTransform;
import com.sinostar.assistant.utils.Constent;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import static android.content.Context.VIBRATOR_SERVICE;

public class ChatHelper {
    private Context context;
    private static EMMessage message;
    public ChatHelper(Context context){
        this.context=context;
    }

    /**
     * 根据图片大小来展示
     */
    public Transformation transformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {

            int targetHeight = AppScreenMgr.getScreenHeight(context) / 6;
            if (source.getHeight() == 0) {
                return source;
            }
            //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
            double aspectRatio = (double) source.getWidth() / (double) source.getHeight();
            int targetWidth = (int) (targetHeight * aspectRatio);
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }

        }
        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };
    /**
     * 静态地图（用于定位发送或者收取）
     *
     * @param i
     * @return
     */
    public String getLocationUrl(int i,EMLocationMessageBody locationBody) {

        String url = "https://restapi.amap.com/v3/staticmap?" +
                "markers=mid,0xFF0000,:" + locationBody.getLongitude() + "," + locationBody.getLatitude()
                + "&zoom=" + 14
                + "&size=" + "300*150"
                + "&scale=" + "2"
                + "&key=" + "52e81be5f2d50796d896334ba67e0673";
        return url;
    }

    /**
     * 文件图标
     * @param fileType
     * @return
     */
    public int getFileType(String fileType) {
        switch (fileType) {
            case "txt":
                return R.drawable.file_txt;
            case "doc":
                return R.drawable.file_word;
            case "pdf":
                return R.drawable.file_pdf;
            case "ppt":
                return R.drawable.file_ppt;
            case "xls":
                return R.drawable.file_excel;
        }
        return 0;
    }

    /**
     * 获取用户头像
     * @param userId 用户ID
     * @return
     */
    public static String getAvatar(String userId){
        if(userId!=null&&!userId.equals("")&&ChatDBHelper.getUserAvatar(userId)!=null){
            return ChatDBHelper.getUserAvatar(userId);
        }else {
            return null;
        }
    }

    /**
     * 显示用户头像
     * @param context  上下文
     * @param userId  用户ID
     * @param imageView 图片展示view
     */
    public static void setPersonAvatar(Context context, String userId, ImageView imageView){
        String avatar=getAvatar(userId);
        if(avatar!=null&&!avatar.equals("")){
            Picasso.get()
                    .load(avatar)
                    .transform(new CircleTransform(context))
                    .into(imageView);
        }

    }
    /**
     * 发送文本消息
     *
     * @param text 发送的文本内容
     */
    public static EMMessage sendText(final String text,String personName) {
        message = EMMessage.createTxtSendMessage(text, personName);
        return message;
    }

    /**
     * 发送图片
     *
     * @param path 图片路径
     */
    public static EMMessage sendImage(final String path,String personName) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        if (path != null && !path.equals("")) {
            message = EMMessage.createImageSendMessage(path, false, personName);
        }
        return message;
    }

    /**
     * 发送语音
     *
     * @param second 语音长度
     * @param path   语音位置
     */
    public static EMMessage sendVoice(float second, String path,String personName) {
        if (second != 0 && path != null && !path.equals("")) {
            //filePath为语音文件路径，length为录音时间(秒)
            message = EMMessage.createVoiceSendMessage(path, (int) second, personName);
            // 增加自己特定的属性
            message.setAttribute(Constent.isVoiceListened, false);
        }
        return message;
    }

    /**
     * 发送位置
     *
     * @param latitude        经度
     * @param longitude       纬度
     * @param location        标题名称
     * @param locationAddress 具体位置
     */
    public static EMMessage sendLocation(double latitude, double longitude, String location, String locationAddress,String personName) {
        //latitude为纬度，longitude为经度，locationAddress为具体位置内容
        if (location != null && locationAddress != null && !location.equals("") && !locationAddress.equals("")) {
            message = EMMessage.createLocationSendMessage(latitude, longitude, location, personName);
            message.setAttribute("location", locationAddress);
        }
        return message;
    }

    /**
     * 发送视频
     *
     * @param videoPath
     * @param thumbPath
     */
    public static EMMessage sendVideo(String videoPath, String thumbPath,String personName) {
        if (videoPath != null && !videoPath.equals("") && thumbPath != null && !thumbPath.equals("")) {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
            message = EMMessage.createVideoSendMessage(videoPath, thumbPath, Integer.parseInt(duration), personName);
        }
        return message;
    }

    /**
     * 发送文件
     *
     * @param filePath
     * @param length
     */
    public static EMMessage sendFile(String filePath, String length,String personName) {
        if (filePath != null && !filePath.equals("")) {
            message = EMMessage.createFileSendMessage(filePath, personName);
            message.setAttribute(Constent.FILE_LENGTH, length);
        }
        return message;
    }

    /**
     * 根据消息类型返回
     * @param message
     * @return
     */
    public static String getChatMessageTypeText(EMMessage message){
        String contentText = "";
        switch (message.getType()) {
            case TXT:
                EMTextMessageBody textBody = (EMTextMessageBody) message.getBody();
                contentText = textBody.getMessage();
                break;
            case IMAGE:
                contentText = "[图片]";
                break;
            case VOICE:
                contentText = "[语音]";
                break;
            case VIDEO:
                contentText = "[视频]";
                break;
            case LOCATION:
                contentText = "[位置]";
                break;
            case FILE:
                contentText = "[文件]";
                break;

        }
        return contentText;
    }

    /**
     * 震动提醒
     */
    public static void getVibrate(){
        final Vibrator vibrator = (Vibrator) ApplicationUtil.getContext().getSystemService(VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(new long[]{50, 150, 50, 150}, -1);
    }

    /**
     * 获取群聊名称
     * @param groupName
     * @return
     */
    public static String getGroupName(String groupName) {
        EMGroup group = EMClient.getInstance().groupManager().getGroup(groupName);
        return group!=null?group.getGroupName():"";
    }


}
