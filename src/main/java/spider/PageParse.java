package spider;

import entity.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * 对页面进行解析
 * 存在问题，页面解析还未和页面获取分开
 * create at 2019-04-22 by MaXin
 */
public class PageParse {
    private WebsiteConfigPO websiteConfig;
    /**
     * 其他页面
     */
    public final static int IS_OTHER = 1;
    /**
     * 详情页
     */
    public final static int IS_DETAIL = 2;
    /**
     * 目录页
     */
    public final static int IS_CATALOG = 3;
    /**
     * 内容页
     */
    public final static int IS_CHAPTER =4;
    /**
     * 不是设置的网站
     */
    public final static int NOT_THIS_WEBSITE = 0;

    public PageParse(WebsiteConfigPO websiteConfig) {
        this.websiteConfig = websiteConfig;
    }

    public WebsiteConfigPO getWebsiteConfig (){
        return websiteConfig;
    }

    /**
     * 实现逻辑：
     * 网站是否有详情页
     * --有详情页
     *  传入的url是否是详情页
     *   --是详情页
     *    解析详情页
     *    网站是否有目录页
     *     --有目录页
     *      当前是否是目录页
     *       --是目录页
     *        解析目录页，获取章节列表（包括标题，及其对应的URL），结束，返回结果
     *       --不是目录页
     *        获取目录页 （目前通过检索页面中的URL，找到第一个匹配的链接）
     *        是否获取到目录页
     *         --获取到目录页
     *          解析目录页，获取章节列表（包括标题，及其对应的URL），结束，返回结果
     *         --没有找到目录页
     *          将{@link NovelDTO}中的chapter设为null，抛出异常：没有找到目录页
     *     --没有目录页
     *      返回错误信息：网站暂不支持
     *   --不是详情页
     *    返回错误信息：输入的网站有详情页配置，但未传入详情页链接
     * --没有详情页
     *  返回错误信息：暂不支持
     * @param url
     * @return
     */
    public NovelDTO getNovelDTO(String url) throws Exception {
        NovelDTO novelDTO = new NovelDTO();
        novelDTO.setUrl(url);
        if (!"".equals(websiteConfig.getNovelDetail())) {
            // 有详情页
            if (url.matches(websiteConfig.getNovelDetail())) {
                // 有详情页，当前传入详情页
                Document document = GetPage.getDocument(websiteConfig.getWebsiteId(), url);
                parseDetailPage(document, novelDTO);
                if (!"".equals(websiteConfig.getNovelCatalogUrl())){
                    // 有详情页，当前传入详情页，网站有目录页
                    if (url.matches(websiteConfig.getNovelCatalogUrl())){
                        // 当前匹配目录页，解析目录页，获取章节列表
                        parseCatalogPage(document, novelDTO);
                    } else {
                        // 当前页面是详情页，不是目录页，获取目录页
                        List<UrlDTO> list = GetPage.getListUrl(document);
                        String catalogUrl = null;
                        for (UrlDTO urlDTO : list) {
                            if (urlDTO.getUrl().matches(websiteConfig.getNovelCatalogUrl())){
                                catalogUrl = urlDTO.getUrl();
                                document = GetPage.getDocument(websiteConfig.getWebsiteId(), catalogUrl);
                                break;
                            }
                        }
                        if (catalogUrl != null){
                            parseCatalogPage(document, novelDTO);
                        } else {
                            throw new Exception("没有找到目录页");
                        }
                    }
                } else {
                    throw new Exception("此网站暂不支持");
                }
            }
            else {
                // 有详情页，当前未传入详情页
                throw new Exception("输入的网站有详情页配置，但未传入详情页链接");
            }
        } else {
            // 网站没有详情页
            throw new Exception("网站暂不支持");
        }
        return novelDTO;
    }

    /**
     * 解析详情页面
     */
    private NovelDTO parseDetailPage(Document document, NovelDTO novelDTO) {
        Elements elements ;
        Element element ;
        String message;
        try {
            elements = document.select(websiteConfig.getNovelTitle());
            if (elements.size() > 1 || elements.isEmpty())
                throw new Exception("网站的标题解析器配置有错误");
            element = elements.get(0);
            message = element.text().replaceFirst(websiteConfig.getNovelTitleReplace() , "");
            novelDTO.setTitle(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            elements = document.select(websiteConfig.getNovelAuthor());
            if (elements.size() > 1 || elements.isEmpty())
                throw new Exception("网站的作者解析器配置有错误");
            element = elements.get(0);
            message = element.text().replaceFirst(websiteConfig.getNovelAuthorReplace() , "");
            novelDTO.setAuthor(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            elements = document.select(websiteConfig.getNovelDescription());
            if (elements.size() > 1 || elements.isEmpty())
                throw new Exception("网站的简介解析器配置有错误");
            element = elements.get(0);
            message = element.text().replaceFirst(websiteConfig.getNovelDescriptionReplace() , "");
            novelDTO.setDescription(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            elements = document.select(websiteConfig.getNovelCategory());
            if (elements.size() > 1 || elements.isEmpty())
                throw new Exception("网站的分类解析器配置有错误");
            element = elements.get(0);
            message = element.text().replaceFirst(websiteConfig.getNovelCategoryReplace() , "");
            novelDTO.setCategory(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            elements = document.select(websiteConfig.getNovelState());
            if (elements.size() > 1 || elements.isEmpty())
                throw new Exception("网站的状态解析器配置有错误");
            element = elements.get(0);
            message = element.text().replaceFirst(websiteConfig.getNovelStateReplace() , "");
            if (websiteConfig.getNovelStateEnd().equals(message))
                novelDTO.setIsEnd(1);
            else if (websiteConfig.getNovelStateNotEnd().equals(message))
                novelDTO.setIsEnd(0);
            else
                throw new Exception("小说状态解析匹配错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return novelDTO;
    }

    /**
     * 解析目录页
     * @param document
     * @param novelDTO
     * @return
     */
    private NovelDTO parseCatalogPage(Document document, NovelDTO novelDTO){
        List<ChapterDTO> list = new ArrayList<>();
        list = getChapterList(document, list);

        //如果目录页分页显示
        if (WebsiteConfigPO.CATALOG_IS_PAGE.equals(websiteConfig.getNovelCatalogType())){
            try {
                while (true) {
                    Elements nextList = document.select(websiteConfig.getNovelCatalogNext());
                    if (nextList.size() > 1 || nextList.isEmpty())
                        throw new Exception("网站的目录页下一页解析器配置有错误");
                    String key = "abs:" + websiteConfig.getNovelCatalogNextText();
                    String url = nextList.get(0).attr(key);
                    if ("".equals(url)) break;
                    document = GetPage.getDocument(websiteConfig.getWebsiteId(), url);
                    list = getChapterList(document, list);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            getChapterList(document , list);
        }
        novelDTO.setChapter(list);
        return novelDTO;
    }

    private List<ChapterDTO> getChapterList(Document document , List<ChapterDTO> list){
        Elements elements = document.select(websiteConfig.getNovelCatalogList());
        for (Element element : elements) {
            String title = element.text();
            String url = element.attr("abs:"+websiteConfig.getNovelCatalogListText());
            ChapterDTO chapter = new ChapterDTO();
            chapter.setTitle(title);
            chapter.setUrl(url);
            list.add(chapter);
        }
        return list;
    }

    /**
     * 解析内容页
     * @param url
     * @return
     * @throws Exception
     */
    public ContentDTO parseChapterPage(String url) throws Exception {
        if (!url.matches(websiteConfig.getNovelChapterUrl()))
            throw new Exception("内容页不匹配URL规则");
        Document document = GetPage.getDocumentRealTime(websiteConfig.getWebsiteId(), url);
        System.out.println("aaa" + document.charset());
        System.out.println(document.outerHtml());
        Elements elements = document.select(websiteConfig.getNovelContent());
        if (elements.size() > 1 || elements.isEmpty())
            throw new Exception("网站的内容页内容解析器配置有错误");
        Element element = elements.get(0);
        String content = element.wholeText().replace(websiteConfig.getNovelContentReplace(), "");
        String nextChapter = null;
        if (WebsiteConfigPO.CONTENT_IS_PAGE.equals(websiteConfig.getNovelContentType())){
            elements = document.select(websiteConfig.getNovelContentNext());
            if (elements.size() > 1 || elements.isEmpty()) {
                throw new Exception("网站内容页分页显示的下一页解析器配置有错误");
            }
            element = elements.get(0);
            String nextUrl = element.attr("abs:"+websiteConfig.getNovelContentNextText());
            while (nextUrl.matches(websiteConfig.getNovelContentNextUrl())){
                Document nextPage = GetPage.getDocumentRealTime(websiteConfig.getWebsiteId(), nextUrl);
                Elements list = nextPage.select(websiteConfig.getNovelContent());
                Element e = list.get(0);
                String next = e.wholeText().replace(websiteConfig.getNovelContentReplace(), "");
                content = content + next;
                list = nextPage.select(websiteConfig.getNovelContentNext());
                e = list.get(0);
                nextUrl = e.attr("abs:"+websiteConfig.getNovelContentNextText());
            }
            if (WebsiteConfigPO.CHAPTER_SELECTOR_IS_CONTENT_SELECTOR.equals(websiteConfig.getNovelChapterNextType())){
                nextChapter = nextUrl;
            }
        }
        elements = document.select(websiteConfig.getNovelChapterTitle());
        if (elements.size() > 1 || elements.isEmpty())
            throw new Exception("网站的内容页标题解析器配置有错误");
        element = elements.get(0);
        String title = element.text().replaceFirst(websiteConfig.getNovelChapterTitleReplace(), "");

        if (nextChapter == null){
            elements = document.select(websiteConfig.getNovelChapterNext());
            if (elements.size() > 1 || elements.isEmpty()) {
                throw new Exception("网站的内容页下一章解析器配置有错误");
            }
            element = elements.get(0);
            nextChapter = element.attr("abs:" + websiteConfig.getNovelChapterNextText());
        }

        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setUrl(url);
        contentDTO.setContent(content);
        contentDTO.setTitle(title);
        contentDTO.setNextUrl(nextChapter);
        return contentDTO;
    }

    /**
     * 获取页面链接，将页面中的连接分为两种，存入map中：key：novel，存储小说详情页的连接（；key：other，存储网页中其他属于本网站，但不是小说详情页、目录页、内容页的连接
     * @param document
     * @return
     * @throws Exception 如果网站配置错误抛出异常
     */
    public Map<String, Set<UrlDTO>> getListUrl (Document document) throws Exception {
        Set<UrlDTO> novel = new HashSet<>();
        Set<UrlDTO> other = new HashSet<>();

        List<UrlDTO> list = GetPage.getListUrl(document);
        if ("".equals(websiteConfig.getNovelDetail()) || "".equals(websiteConfig.getNovelCatalogUrl()))
            throw new Exception("网站没有详情页或目录页配置，");

        for (UrlDTO urlDTO : list) {
            boolean website = urlDTO.getUrl().matches(websiteConfig.getWebsite());
            boolean detail = urlDTO.getUrl().matches(websiteConfig.getNovelDetail());
            boolean catalog = urlDTO.getUrl().matches(websiteConfig.getNovelCatalogUrl());
            boolean chapter = urlDTO.getUrl().matches(websiteConfig.getNovelChapterUrl());
            if (!website) {
                continue;
            }
            if (detail){
                novel.add(urlDTO);
                continue;
            }
            if (catalog || chapter)
                continue;
            other.add(urlDTO);
        }

        Map<String , Set<UrlDTO>> map = new HashMap<>();
        map.put("novel", novel);
        map.put("other", other);
        return map;
    }

    /**
     * 判断URL类型
     * @param url
     * @return 页面类型
     */
    public int parseUrl (String url){
        if (!url.matches(websiteConfig.getWebsite()))
            return NOT_THIS_WEBSITE;
        if ("".equals(websiteConfig.getNovelDetail()) && url.matches(websiteConfig.getNovelDetail()))
            return IS_DETAIL;
        if ("".equals(websiteConfig.getNovelCatalogUrl()) && url.matches(websiteConfig.getNovelCatalogUrl()))
            return IS_CATALOG;
        if ("".equals(websiteConfig.getNovelChapterUrl()) && url.matches(websiteConfig.getNovelChapterUrl()))
            return IS_CHAPTER;
        return IS_OTHER;
    }

    public NovelDTO parseDetail (Document document , NovelDTO novel){
        return parseDetailPage(document , novel);
    }

    public NovelDTO parseCatalog (Document document , NovelDTO novelDTO){
        return parseCatalogPage(document , novelDTO);
    }

}
