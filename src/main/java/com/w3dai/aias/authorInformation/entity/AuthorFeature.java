package com.w3dai.aias.authorInformation.entity;

import javax.persistence.*;

@Entity
@Table(name="authorfeature")
public class Authorfeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String authorName; //作者

    public String editors; //责编
    public String coauthors; //合作者
    public String articleTitles; //标题
    public String category; //分类
    public String pageName; //版名
    public String type; //体裁
    public String columnName; //栏目名

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(String coauthors) {
        this.coauthors = coauthors;
    }

    public String getArticleTitles() {
        return articleTitles;
    }

    public void setArticleTitles(String articleTitles) {
        this.articleTitles = articleTitles;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
