package com.sinostar.assistant.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatUplodData {
    /**
     * action : post
     * application : d2dd2590-a41f-11e8-91c6-69376f1245b1
     * path : /chatfiles
     * uri : https://a1.easemob.com/1155180820099490/assistant/chatfiles
     * entities : [{"uuid":"2dbc8c00-b0e9-11e8-b1a7-ff9dedbc1981","type":"chatfile","share-secret":"LbyMCrDpEeiIxiPIAaUvTCoZpm4k3bh2iwTypgaY13iWn2ik"}]
     * timestamp : 1536137617602
     * duration : 0
     * organization : 1155180820099490
     * applicationName : assistant
     */

    private String action;
    private String application;
    private String path;
    private String uri;
    private long timestamp;
    private int duration;
    private String organization;
    private String applicationName;
    private List<EntitiesBean> entities;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<EntitiesBean> getEntities() {
        return entities;
    }

    public void setEntities(List<EntitiesBean> entities) {
        this.entities = entities;
    }

    public static class EntitiesBean {
        /**
         * uuid : 2dbc8c00-b0e9-11e8-b1a7-ff9dedbc1981
         * type : chatfile
         * share-secret : LbyMCrDpEeiIxiPIAaUvTCoZpm4k3bh2iwTypgaY13iWn2ik
         */

        private String uuid;
        private String type;
        @SerializedName("share-secret")
        private String sharesecret;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSharesecret() {
            return sharesecret;
        }

        public void setSharesecret(String sharesecret) {
            this.sharesecret = sharesecret;
        }
    }
}
