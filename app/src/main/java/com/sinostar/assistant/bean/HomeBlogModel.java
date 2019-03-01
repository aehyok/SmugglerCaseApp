package com.sinostar.assistant.bean;

public class HomeBlogModel {
    private String Id;

    public String getId(){return Id;}

    public void setId(String id){this.Id=id;}

    private String Title;

    public String getTitle(){return Title;}

    public void setTitle(String title){this.Title=title;}

    private String Url;

    public String getUrl(){return Url;}

    public void setUrl(String url){this.Url =url;}

    private String Description;

    public String getDescription(){return Description;}
    public void setDescription(String description){this.Description=description;}

    private String Author;
    public String getAuthor(){return Author;}
    public void setAuthor(String author){this.getAuthor();}

    private String BlogApp;
    public String getBlogApp(){return BlogApp;}
    public void setBlogApp(String blogApp){this.BlogApp=blogApp;}

    private String Avatar;
    public String getAvatar(){return Avatar;}
    public void setAvatar(String avatar){this.Avatar=avatar;}

    private String PostDate;
    public String getPostDate(){return PostDate;}
    public void setPostDate(String postDate){this.PostDate=postDate;}

    private int ViewCount;
    public int getViewCount(){return ViewCount;}
    public void setViewCount(int viewCount){this.ViewCount=viewCount;}

    private  int CommentCount;
    public int getCommentCount(){return CommentCount;}
    public void setCommentCount(int commentCount){this.CommentCount=commentCount;}

    private int DiggCount;
    public int getDiggCount(){return DiggCount;}
    public void setDiggCount(int diggCount){this.DiggCount=diggCount;}




    }
