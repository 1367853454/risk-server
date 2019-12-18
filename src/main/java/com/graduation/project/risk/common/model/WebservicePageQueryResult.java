package com.graduation.project.risk.common.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@XmlSeeAlso({ArrayList.class, HashMap.class})
public class WebservicePageQueryResult<T> extends AbstractWebserviceResult {
    private List<T> content;
    protected int currentItemCount;
    protected int pageIndex;
    protected long totalItems;
    protected int totalPages;
    protected int itemsPerPage;

    public WebservicePageQueryResult() {
    }

    public <T> WebservicePageQueryResult(Page<T> page) {
        if (page.getContent() != null) {
            this.setContent(new ArrayList(page.getContent()));
        }

        this.setTotalPages(page.getTotalPages());
        this.setTotalItems(page.getTotalElements());
        this.setCurrentItemCount(page.getNumberOfElements());
        this.setItemsPerPage(page.getSize());
        this.setPageIndex(page.getNumber() + 1);
    }

    public <T> WebservicePageQueryResult(Page<Object> page, Function<Object, T> convert) {
        if (page.getContent() != null) {
            this.setContent(new ArrayList((Collection)page.getContent().stream().map(convert).collect(Collectors.toList())));
        }

        this.setTotalPages(page.getTotalPages());
        this.setTotalItems(page.getTotalElements());
        this.setCurrentItemCount(page.getNumberOfElements());
        this.setItemsPerPage(page.getSize());
        this.setPageIndex(page.getNumber() + 1);
    }

    public static final <T> WebservicePageQueryResult<T> instance(Page<T> page) {
        return new WebservicePageQueryResult(page);
    }

    public static final <T> WebservicePageQueryResult<T> instance(Page<Object> page, Function<Object, T> convert) {
        return new WebservicePageQueryResult(page, convert);
    }

    public Page<T> toPage() {
        PageRequest pageRequest = new PageRequest(this.getPageIndex() - 1, this.getItemsPerPage());
        List<T> pageContent = this.getContent();
        if (pageContent == null) {
            pageContent = new ArrayList();
        }

        PageImpl<T> page = new PageImpl((List)pageContent, pageRequest, this.getTotalItems());
        return page;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getCurrentItemCount() {
        return this.currentItemCount;
    }

    public void setCurrentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getItemsPerPage() {
        return this.itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}

