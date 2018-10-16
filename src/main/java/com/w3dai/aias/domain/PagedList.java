package com.w3dai.aias.domain;

import java.util.List;

public class PagedList<T> {

    private int buttonsToShow = 5;  //默认显示5个按钮
    private List<T> itemList;
    private int pageSize;   //每页的数据条数
    private int pageIndex;  //当前在第几页，从0开始
    private int pageOffset; //当前页开始的数据条数
    private int totalCount; //一共有几条记录
    private int totalPageCount; //一共有几页
    private int startPage;  //开始页
    private int endPage;    //结束页

    public PagedList(int pageSize, int pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.pageOffset = calculatePageOffset(pageSize, pageIndex);
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        pageOffset = calculatePageOffset(pageSize, pageIndex);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        pageOffset = calculatePageOffset(pageSize, pageIndex);
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        totalPageCount = (totalCount + pageSize - 1) / pageSize;
        calculateStartEndPage();
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }

    public void setButtonsToShow(int buttonsToShow) {
        this.buttonsToShow = buttonsToShow;
    }

    private int calculatePageOffset(int pageSize, int pageIndex) {
        return pageSize * pageIndex;
    }

    private void calculateStartEndPage() {
        int halfPagesToShow = getButtonsToShow() / 2;

        if (totalPageCount <= getButtonsToShow()) {
            setStartPage(1);
            setEndPage(totalPageCount);
        } else if (pageIndex - halfPagesToShow <= 0) {
            setStartPage(1);
            setEndPage(getButtonsToShow());
        } else if (pageIndex + halfPagesToShow == totalPageCount) {
            setStartPage(pageIndex - halfPagesToShow);
            setEndPage(totalPageCount);
        } else if (pageIndex + halfPagesToShow > totalPageCount) {
            setStartPage(totalPageCount - getButtonsToShow() + 1);
            setEndPage(totalPageCount);
        } else {
            setStartPage(pageIndex - halfPagesToShow);
            setEndPage(pageIndex + halfPagesToShow);
        }
    }

    @Override
    public String toString() {
        return "PagedList{" +
                "buttonsToShow=" + buttonsToShow +
                ", itemList=" + itemList +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", pageOffset=" + pageOffset +
                ", totalCount=" + totalCount +
                ", totalPageCount=" + totalPageCount +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                '}';
    }
}
