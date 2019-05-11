package entity;

import java.util.List;
import java.util.Objects;

/**
 * 页面解析之后生成的数据对象
 * create at 2019-04-02 by MaXin
 */
public class NovelDTO {
    private String url;
    private String title;
    private String author;
    private String description;
    private String category;
    private Integer isEnd ;
    private List<ChapterDTO> chapter;

    public NovelDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public List<ChapterDTO> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterDTO> chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "NovelDTO{ \n" +
                " url= " + url + '\n' +
                " title= " + title + '\n' +
                " author= " + author + '\n' +
                " description= " + description + '\n' +
                " category= " + category + '\n' +
                " isEnd= " + isEnd + '\n' +
                " chapter= " + chapter + '\n' +
                '}';
    }
}
