package entity;

import java.sql.Date;
import java.util.Objects;

/**
 * 小说表实体类
 */
public class NovelPO {
    private Long novelId;
    private String title;
    private String author;
    private String description;
    private String category;
    private Integer isEnd ;
    private Integer isDelete;
    private Date gmtCreate;
    private Date gmtModified;

    public static final Integer IS_DELETE = 1;
    public static final Integer NOT_IS_DELETE = 0;
    public static final Integer IS_END = 1;
    public static final Integer NOT_IS_END = 0;


    public NovelPO() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
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

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
