package com.sinostar.assistant.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String userName;
    private String userAvatar;
    private boolean isTop;
    private boolean isQuiet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return isTop == user.isTop &&
                isQuiet == user.isQuiet &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userAvatar, user.userAvatar);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userName, userAvatar, isTop, isQuiet);
    }



    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isQuiet() {
        return isQuiet;
    }

    public void setQuiet(boolean quiet) {
        isQuiet = quiet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
