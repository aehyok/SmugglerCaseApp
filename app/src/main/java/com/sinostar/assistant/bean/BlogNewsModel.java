package com.sinostar.assistant.bean;

import java.util.Date;

public class BlogNewsModel {
    private String Id;
    private String Title;
    private String Summary;
    private String TopicId;
    private String TopicIcon;
    private int ViewCount;
    private int CommentCount;
    private int DiggCount;
    private String DateAdded;


    public String getId(){return this.Id;}
    public void setId(String id){this.Id=id;}

    public String getTitle(){return this.Title;}
    public void setTitle(String title){this.Title=title;}

    public String getSummary(){return this.Summary;}
    public void setSummary(String summary){this.Summary=summary;}

    public String getTopicId(){return this.TopicId;}
    public void setTopicId(String topicId){this.TopicId=topicId;}

    public String getTopicIcon(){return this.TopicIcon;}
    public void setTopicIcon(String topicIcon){this.TopicIcon=topicIcon;}

    public int getViewCount(){return this.ViewCount;}
    public void setViewCount(int viewCount){this.ViewCount=viewCount;}

    public int getCommentCount(){return CommentCount;}
    public void setCommentCount(int commentCount){this.CommentCount=commentCount;}

    public int getDiggCount(){return this.DiggCount;}
    public void setDiggCount(int diggCount){this.DiggCount=diggCount;}

    public  String getDateAdded(){return this.DateAdded;}
    public void setDateAdded(String dateAdded){this.DateAdded=dateAdded;}

}
