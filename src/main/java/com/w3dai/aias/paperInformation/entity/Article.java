package com.w3dai.aias.paperInformation.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(indexName = "papers", type = "article")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String      id;
    private String    eyebrowTitle;
    private String    mainTitle;
    private String    subTitle;
    private String    authorsName;
    private String    editorsName;
    private String    pageName;
    private String    paperCategory;
    private String    publishDate;
    private String    paperType;
    private String    articleText;
    private String    columnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEyebrowTitle() {
        return eyebrowTitle;
    }

    public void setEyebrowTitle(String eyebrowTitle) {
        this.eyebrowTitle = eyebrowTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(String authorsName) {
        this.authorsName = authorsName;
    }

    public String getEditorsName() {
        return editorsName;
    }

    public void setEditorsName(String editorsName) {
        this.editorsName = editorsName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPaperCategory() {
        return paperCategory;
    }

    public void setPaperCategory(String paperCategory) {
        this.paperCategory = paperCategory;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }


}

