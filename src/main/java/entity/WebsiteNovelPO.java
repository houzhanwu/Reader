package entity;

import java.sql.Date;

/**
 * 网站信息表的实体类
 * create at 2019-04-20 by MaXin
 *
 * 此表用于存储各个网站中解析出来的数据
 */
public class WebsiteNovelPO {

    private Long websiteNovelId;
    private Long novelId;
    private String chapter;
    private Date gmtCreate;
    private Date gmtModified;

    public WebsiteNovelPO() {
    }

    public Long getWebsiteNovelId() {
        return websiteNovelId;
    }

    public void setWebsiteNovelId(Long websiteNovelId) {
        this.websiteNovelId = websiteNovelId;
    }

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    /**
     * 章节信息由json串存储
     * 为对象数组，对象内变量有：
     * title：表示标题
     * URL：表示文章链接
     * 对应类型为 {@link ChapterDTO}
     * @return
     */
    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
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
