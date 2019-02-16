package com.sinostar.assistant.bean;

/**
 * 登陆实体类
 */
public class Login {
    /**
     * Success : true
     * Message : 登录成功！
     * data : {"UserId":13345,"UserName":"赵福地<旧>"}
     */

    private boolean Success;
    private String Message;
    private DataBean data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * UserId : 13345
         * UserName : 赵福地<旧>
         */

        private int UserId;
        private String UserName;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }
    }
}
