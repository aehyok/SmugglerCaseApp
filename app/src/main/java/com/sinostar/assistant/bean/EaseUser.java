package com.sinostar.assistant.bean;

import com.hyphenate.chat.EMContact;

public class EaseUser extends EMContact {

    /**
     * nickName for user
     *
     */
    protected String nickName;
    /**
     * avatar of the user
     */
    protected String avatar;

    public EaseUser(String username){
        this.username = username;
    }

    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof EaseUser)) {
            return false;
        }
        return getUsername().equals(((EaseUser) o).getUsername());
    }

    @Override
    public String toString() {
        return nick == null ? username : nick;
    }
}

