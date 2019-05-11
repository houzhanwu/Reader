package spider;

import entity.ChapterDTO;
import entity.NovelDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * 解析网页内容获取文章信息
 * 必须传入一个String类型的HTML页面内容，内容是小说的详情页面
 * create at 2019-04-02 by MaXin
 */
public class NovelUtil {
    private String page ;
    private Document document;
    private NovelDTO novel ;

    public NovelUtil(String page) {
        this.page = page;
        document = Jsoup.parse(page);
    }

    public NovelUtil(Document document) {
        this.document = document;
        page = document.html();
    }

}
