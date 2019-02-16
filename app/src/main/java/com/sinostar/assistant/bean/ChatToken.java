package com.sinostar.assistant.bean;

public class ChatToken {

    /**
     * access_token : YWMtTmimwrDbEeiOvE0yMkF27QAAAAAAAAAAAAAAAAAAAAHS3SWQpB8R6JHGaTdvEkWxAgMAAAFlqJT2xABPGgDy49W5BVFrv1TZZK3rwAhQh5LryLBIucXcCpo0UnKPXA
     * expires_in : 5184000
     * application : d2dd2590-a41f-11e8-91c6-69376f1245b1
     */

    private String access_token;
    private int expires_in;
    private String application;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
