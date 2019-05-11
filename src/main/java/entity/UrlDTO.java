package entity;

import org.jsoup.nodes.Document;
import java.util.Objects;
import java.util.Optional;

/**
 * 包含链接的URL和文本信息的类
 * create at 2019-04-03 by MaXin
 */
public class UrlDTO {
    private String url = "";
    private String text ="";
//    private Document page;

    public UrlDTO(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public UrlDTO (){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Document getPage() {
//        return page;
//    }
//
//    public void setPage(Document page) {
//        this.page = page;
//    }

    @Override
    public String toString() {
        return "UrlDTO{ \n" +
                " url= " + url + '\n' +
                " text= " + text + '\n' +
//                " page= " + page==null ? "" : ( page.outerHtml().substring(0, 20) + "......" )+ '\n' +
                '}';
    }

    /**
     * 通过判断URL值来判断是否相同，即使text值不同，相同的URL值也会被判为相同
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlDTO urlDTO = (UrlDTO) o;
        return url.equals(urlDTO.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    public static void main(String[] args) {
        UrlDTO urlDTO = new UrlDTO("adinaidnd","diandia");
        UrlDTO urlDTO1 = new UrlDTO("adinaidnd","");
        System.out.println(urlDTO.equals(urlDTO1));
    }
}
