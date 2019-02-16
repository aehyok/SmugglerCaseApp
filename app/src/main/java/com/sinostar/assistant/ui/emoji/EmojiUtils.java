package com.sinostar.assistant.ui.emoji;

/**
 * Created by Administrator on 2018/2/6.
 */

public class EmojiUtils {
    public static String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
