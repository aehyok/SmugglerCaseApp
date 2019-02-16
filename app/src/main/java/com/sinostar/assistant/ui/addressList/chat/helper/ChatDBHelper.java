package com.sinostar.assistant.ui.addressList.chat.helper;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.sinostar.assistant.ui.addressList.ChatLogin;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.bean.User;
import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.Constent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * 头像昵称帮助类
 */
public class ChatDBHelper {
    /**
     * 获取头像昵称数据
     * @param userId
     * @return
     */
    public static User getUserData(String userId) {
         ACache aCache=ACache.get(ApplicationUtil.getContext());
        if(aCache.getAsObject(userId)!=null){
            User user= (User) aCache.getAsObject(userId);
            return user;
        }else{
            return null;
        }
    }

    /**
     * 设置头像昵称
     * @param userId 用户ID
     * @param user 头像昵称封装类
     */
    public static void setUserDataMap( String userId,User user) {
         ACache aCache=ACache.get(ApplicationUtil.getContext());
        aCache.put(userId,user);
    }

    /**
     * 设置用户信息
     * @param userId 用户ID
     * @param message 收到的消息
     */
    public static void setUserDataMap(String userId, EMMessage message){
        try {
            String ext= message.getStringAttribute(Constent.USER_EXT);
            JSONObject extObject = new JSONObject(ext);
            User user=new User();
            user.setUserAvatar(message.getStringAttribute(Constent.USER_AVATAR));
            user.setUserName((String) extObject.get(Constent.USER_NAME));
            if(getUserData(userId)!=null){
                if(!user.equals(getUserData(userId))){
                    setUserDataMap(userId,user);
                }
            }else{
                setUserDataMap(userId,user);
            }

        } catch (HyphenateException | JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取用户的昵称
     * @param userId
     * @return
     */

    public static String getUserName( String userId){
        if(getUserData(userId)!=null){
            String name=getUserData(userId).getUserName();
            return Objects.requireNonNull(getUserData(userId)).getUserName();
        }else{
            String name = ChatLogin.getNickName(userId);
            User user=new User();
            user.setUserName(name);
            user.setUserAvatar(ChatLogin.getAvatar(userId));
            setUserDataMap(userId,user);
            return null;
        }
    }

    /**
     * 获取用户头像
     * @param userId 用户ID
     * @return
     */
    public static String getUserAvatar(String userId){
        if(getUserData(userId)!=null){
            return getUserData(userId).getUserAvatar();
        }else{
            //模拟联网获取头像
            String avatar = ChatLogin.getAvatar(userId);
            User user=new User();
            user.setUserName(ChatLogin.getNickName(userId));
            user.setUserAvatar(avatar);
            setUserDataMap(userId,user);
            return getUserAvatar(userId);
        }
    }

    /**
     * 设置当前用户的id
     * @param chatId
     */
    public static void setChatId(String chatId){
        ACache aCache=ACache.get(ApplicationUtil.getContext());
        aCache.put(Constent.USER_ID,chatId);
    }

    /**
     * 获取当前用户的id
     * @return
     */
    public static String getChatId(){
        ACache aCache=ACache.get(ApplicationUtil.getContext());
        String chatId;
        if(ApplicationUtil.getChatId()!=null){
            String sd=ApplicationUtil.getChatId();
            return ApplicationUtil.getChatId();
        }else if (aCache.getAsString(Constent.USER_ID)!=null){
            String sdfasdf=aCache.getAsString(Constent.USER_ID);
            ApplicationUtil.setChatId(aCache.getAsString(Constent.USER_ID));
            return aCache.getAsString(Constent.USER_ID);
        }else{
            return null;
        }
    }

    /**
     * 删除某个聊天
     * @param username
     */
    public static void deleteConversation(String username,boolean isDeleteRecord){
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(username, isDeleteRecord);
    }


    /**
     * 判断某个聊天是否置顶
     * @param context
     * @param id
     * @return
     */
    public static boolean isTop(Context context,String id){
        ACache aCache=ACache.get(context);
        if(aCache.getAsObject(id)!=null){
            User user= (User) aCache.getAsObject(id);
            return user.isTop();
        }else{
            return false;
        }
    }
    public static void setTop(Context context,String id,boolean isTop){
        ACache aCache=ACache.get(context);
        if(aCache.getAsObject(id)!=null){
            User user= (User) aCache.getAsObject(id);
            user.setTop(isTop);
            aCache.put(id,user);
        }else{
           User user=new User();
           user.setTop(isTop);
            aCache.put(id,user);
        }
    }

    /**
     * 判断某个聊天是否请勿打扰
     * @param context
     * @param id
     * @return
     */
    public static boolean isQueit(Context context,String id){
        ACache aCache=ACache.get(context);
        if(aCache.getAsObject(id)!=null){
            User user= (User) aCache.getAsObject(id);
            return user.isQuiet();
        }else{
            return false;
        }
    }
    public static void setQuiet(Context context,String id,boolean isQuiet){
        ACache aCache=ACache.get(context);
        if(aCache.getAsObject(id)!=null){
            User user= (User) aCache.getAsObject(id);
            user.setQuiet(isQuiet);
            aCache.put(id,user);
        }else{
            User user=new User();
            user.setQuiet(isQuiet);
            aCache.put(id,user);
        }
    }
}
