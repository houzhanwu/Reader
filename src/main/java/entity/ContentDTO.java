package entity;

/**
 * 小说章节的具体内容实体类
 * create at 2019-04-23 by MaXin
 */
public class ContentDTO {
    private String title ;
    private String content;
    private String url;
    private String nextUrl;

    public ContentDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    @Override
    public String toString() {
        return "ContentDTO{ \n" +
                " title= " + title + '\n' +
                " content= " + content + '\n' +
                " url= " + url + '\n' +
                " nextUrl= " + nextUrl.substring(0, 20)+"......" + '\n' +
                '}';
    }
}
