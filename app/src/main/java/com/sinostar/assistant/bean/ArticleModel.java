package com.sinostar.assistant.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleModel {

    private Integer Count;
    public Integer getCount(){return this.Count;}

    public void setCount(Integer count){this.Count=count;}

    private ArrayList<Article> ArticleList;

    public ArrayList<Article> getArticleList(){return this.ArticleList;}

    public void setArticleList(ArrayList<Article> list){this.ArticleList=list;}
    public static class Article
    {

        public Article(Integer id,String title,String content,String updateDate,Integer count){
            this.Id=id;
            this.Title=title;
            this.Content=content;
            this.UpdateTime=updateDate;
            this.Count=count;
        }
        private Integer Id;
        private String Title;
        private String Content;
        private String UpdateTime;
        private Integer Count;

        public Integer getId(){return this.Id;}

        public void setId(int id){this.Id=id;}

        public String getTitle(){return this.Title;}

        public void setTitle(String title){this.Title=title;}

        public String getContent(){return this.Content;}

        public void setContent(String content){this.Content=content;}

        public Integer getCount(){return this.Count;}

        public void setCount(int count){this.Count=count;}

        public String getDate(){return this.UpdateTime;}

        public void setDate(String updateDate){this.UpdateTime=updateDate;}
    }


}
