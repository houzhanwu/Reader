package entity;

/**
 * 章节列表数据传输类
 * 本类作为website_novel表的chapter字段的json串解析对象，不许更改
 * 表对应的类为：{@link WebsiteNovelPO}
 * create at 2019-04-02 by MaXin
 */
public class ChapterDTO {
    private String title ;
    private String url ;

    public ChapterDTO() {
    }

    public ChapterDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ChapterDTO{ \n" +
                " title= " + title + '\n' +
                " url= " + url + '\n' +
                '}';
    }
}
