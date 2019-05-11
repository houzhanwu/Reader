package entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * 网站配置的json串的对应实体类
 * create at 2019-04-13 by MaXin
 */

public class WebsiteConfigPO implements Serializable {

    private String website;
    private String websiteName;
    private Long websiteId;
    private String websiteUrl;
    private String novelDetail;
    private String novelTitle;
    private String novelTitleReplace;
    private String novelAuthor;
    private String novelAuthorReplace;
    private String novelState;
    private String novelStateReplace;
    private String novelStateEnd;
    private String novelStateNotEnd;
    private String novelCategory;
    private String novelCategoryReplace;
    private String novelDescription;
    private String novelDescriptionReplace;
    private String novelCatalogUrl;
    private Integer novelCatalogType;
    private String novelCatalogNext;
    private String novelCatalogNextText;
    private String novelCatalogList;
    private String novelCatalogListText;
    private String novelChapterUrl;
    private String novelChapterTitle;
    private String novelChapterTitleReplace;
    private String novelContent;
    private String novelContentReplace;
    private Integer novelContentType;
    private String novelContentNext;
    private String novelContentNextUrl;
    private String novelContentNextText;
    private String novelChapterNext;
    private Integer novelChapterNextType;
    private String novelChapterNextText;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer isDelete;

    /**
     * 目录页分页
     */
    public static final Integer CATALOG_IS_PAGE = 1;
    /**
     * 目录页不分页
     */
    public static final Integer CATALOG_NOT_IS_PAGE = 0;
    /**
     * 内容页不分页
     */
    public static final Integer CONTENT_NOT_IS_PAGE = 0;
    /**
     * 内容页分页
     */
    public static final Integer CONTENT_IS_PAGE = 1;
    /**
     * 内容页分页时，下一页与下一章所在位置不相同
     */
    public static final Integer CHAPTER_SELECTOR_NOT_IS_CONTENT_SELECTOR = 0;
    /**
     * 内容页分页时，下一页与下一章所在位置相同
     */
    public static final Integer CHAPTER_SELECTOR_IS_CONTENT_SELECTOR = 1;
    public static final Integer DEFAULT_ID = 0;

    public WebsiteConfigPO() {
    }

    /**
     * 获得website的ID
     * @return
     */
    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    /**
     * 获取网站根目录对应的正则表达式
     * @return
     */
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 获取网站名字
     * @return
     */
    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    /**
     * 网站首页
     * @return
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
     * 小说详情页URL的正则表达式，如为空字符串则表示没有详情页
     * @return
     */
    public String getNovelDetail() {
        return novelDetail;
    }

    public void setNovelDetail(String novelDetail) {
        this.novelDetail = novelDetail;
    }

    /**
     * 详情页中标题的jsoup选择器规则
     * @return
     */
    public String getNovelTitle() {
        return novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    /**
     * 小说标题中需要删除的内容
     * @return
     */
    public String getNovelTitleReplace() {
        return novelTitleReplace;
    }

    public void setNovelTitleReplace(String novelTitleReplace) {
        this.novelTitleReplace = novelTitleReplace;
    }

    /**
     * 小说作者位置的选择器
     * @return
     */
    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    /**
     * 小说作者中需要删除的内容
     * @return
     */
    public String getNovelAuthorReplace() {
        return novelAuthorReplace;
    }

    public void setNovelAuthorReplace(String novelAuthorReplace) {
        this.novelAuthorReplace = novelAuthorReplace;
    }

    public String getNovelState() {
        return novelState;
    }

    public void setNovelState(String novelState) {
        this.novelState = novelState;
    }

    public String getNovelStateReplace() {
        return novelStateReplace;
    }

    public void setNovelStateReplace(String novelStateReplace) {
        this.novelStateReplace = novelStateReplace;
    }

    public String getNovelStateEnd() {
        return novelStateEnd;
    }

    public void setNovelStateEnd(String novelStateEnd) {
        this.novelStateEnd = novelStateEnd;
    }

    public String getNovelStateNotEnd() {
        return novelStateNotEnd;
    }

    public void setNovelStateNotEnd(String novelStateNotEnd) {
        this.novelStateNotEnd = novelStateNotEnd;
    }

    /**
     * 小说分类
     * @return
     */
    public String getNovelCategory() {
        return novelCategory;
    }

    public void setNovelCategory(String novelCategory) {
        this.novelCategory = novelCategory;
    }

    /**
     * 小说分类中需要删除的内容
     * @return
     */
    public String getNovelCategoryReplace() {
        return novelCategoryReplace;
    }

    public void setNovelCategoryReplace(String novelCategoryReplace) {
        this.novelCategoryReplace = novelCategoryReplace;
    }

    /**
     * 小说简介的选择器
     * @return
     */
    public String getNovelDescription() {
        return novelDescription;
    }

    public void setNovelDescription(String novelDescription) {
        this.novelDescription = novelDescription;
    }

    /**
     * 小说简介中需要删除的内容
     * @return
     */
    public String getNovelDescriptionReplace() {
        return novelDescriptionReplace;
    }

    public void setNovelDescriptionReplace(String novelDescriptionReplace) {
        this.novelDescriptionReplace = novelDescriptionReplace;
    }

    /**
     * 小说目录页的地址规则，如果为空字符串，表示没有目录页
     * @return
     */
    public String getNovelCatalogUrl() {
        return novelCatalogUrl;
    }

    public void setNovelCatalogUrl(String novelCatalogUrl) {
        this.novelCatalogUrl = novelCatalogUrl;
    }

    /**
     * 小说目录页是否分页，0表示不分页，1表示分页
     * @return
     */
    public Integer getNovelCatalogType() {
        return novelCatalogType;
    }

    public void setNovelCatalogType(Integer novelCatalogType) {
        this.novelCatalogType = novelCatalogType;
    }

    /**
     * 当目录页分页显示时，此项有效，表示下一页连接的选择器
     * @return
     */
    public String getNovelCatalogNext() {
        return novelCatalogNext;
    }

    public void setNovelCatalogNext(String novelCatalogNext) {
        this.novelCatalogNext = novelCatalogNext;
    }

    /**
     * 当目录页分页显示时，此项有效，表示下一页连接所在的属性值，空字符串表示为文本
     * @return
     */
    public String getNovelCatalogNextText() {
        return novelCatalogNextText;
    }

    public void setNovelCatalogNextText(String novelCatalogNextText) {
        this.novelCatalogNextText = novelCatalogNextText;
    }

    /**
     * 目录页中章节列表所在的选择器
     * @return
     */
    public String getNovelCatalogList() {
        return novelCatalogList;
    }

    public void setNovelCatalogList(String novelCatalogList) {
        this.novelCatalogList = novelCatalogList;
    }

    /**
     * 目录页中列表连接所在的属性值
     * @return
     */
    public String getNovelCatalogListText() {
        return novelCatalogListText;
    }

    public void setNovelCatalogListText(String novelCatalogListText) {
        this.novelCatalogListText = novelCatalogListText;
    }

    /**
     * 小说内容页的URL正则表达式
     * @return
     */
    public String getNovelChapterUrl() {
        return novelChapterUrl;
    }

    public void setNovelChapterUrl(String novelChapterUrl) {
        this.novelChapterUrl = novelChapterUrl;
    }

    /**
     * 章节页中章节名所在位置的选择器，为空表示没有章节名称
     * @return
     */
    public String getNovelChapterTitle() {
        return novelChapterTitle;
    }

    public void setNovelChapterTitle(String novelChapterTitle) {
        this.novelChapterTitle = novelChapterTitle;
    }

    /**
     * 章节名中需要删除的内容
     * @return
     */
    public String getNovelChapterTitleReplace() {
        return novelChapterTitleReplace;
    }

    public void setNovelChapterTitleReplace(String novelChapterTitleReplace) {
        this.novelChapterTitleReplace = novelChapterTitleReplace;
    }

    /**
     * 章节内容所在的选择器
     * @return
     */
    public String getNovelContent() {
        return novelContent;
    }

    public void setNovelContent(String novelContent) {
        this.novelContent = novelContent;
    }

    /**
     * 小说内容中需要删除的内容
     * @return
     */
    public String getNovelContentReplace() {
        return novelContentReplace;
    }

    public void setNovelContentReplace(String novelContentReplace) {
        this.novelContentReplace = novelContentReplace;
    }

    /**
     * 小说内容页是否分页显示，0表示不分页，1表示分页
     * @return
     */
    public Integer getNovelContentType() {
        return novelContentType;
    }

    public void setNovelContentType(Integer novelContentType) {
        this.novelContentType = novelContentType;
    }

    /**
     * 当小说内容页分页显示时，此项有效。表示内容页中下一页所在的选择器
     * @return
     */
    public String getNovelContentNext() {
        return novelContentNext;
    }

    public void setNovelContentNext(String novelContentNext) {
        this.novelContentNext = novelContentNext;
    }

    /**
     * 当小说内容页分页显示时，此项有效，表示小说内容页下一页的连接规则
     * @return
     */
    public String getNovelContentNextUrl() {
        return novelContentNextUrl;
    }

    public void setNovelContentNextUrl(String novelContentNextUrl) {
        this.novelContentNextUrl = novelContentNextUrl;
    }

    public String getNovelContentNextText() {
        return novelContentNextText;
    }

    public void setNovelContentNextText(String novelContentNextText) {
        this.novelContentNextText = novelContentNextText;
    }

    /**
     * 小说内容页中下一张所在的选择器
     * @return
     */
    public String getNovelChapterNext() {
        return novelChapterNext;
    }

    public void setNovelChapterNext(String novelChapterNext) {
        this.novelChapterNext = novelChapterNext;
    }

    /**
     * 当小说内容页分页显示时，此项有效。表示下一页与下一章所在的选择器是否相同。0表示不相同，1表示相同
     * @return
     */
    public Integer getNovelChapterNextType() {
        return novelChapterNextType;
    }

    public void setNovelChapterNextType(Integer novelChapterNextType) {
        this.novelChapterNextType = novelChapterNextType;
    }

    /**
     * 小说下一章连接所在的属性值，空字符串表示文本
     * @return
     */
    public String getNovelChapterNextText() {
        return novelChapterNextText;
    }

    public void setNovelChapterNextText(String novelChapterNextText) {
        this.novelChapterNextText = novelChapterNextText;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "WebsiteConfigPO{ \n" +
                " website= " + website + '\n' +
                " websiteName= " + websiteName + '\n' +
                " websiteId= " + websiteId + '\n' +
                " websiteUrl= " + websiteUrl + '\n' +
                " novelDetail= " + novelDetail + '\n' +
                " novelTitle= " + novelTitle + '\n' +
                " novelTitleReplace= " + novelTitleReplace + '\n' +
                " novelAuthor= " + novelAuthor + '\n' +
                " novelAuthorReplace= " + novelAuthorReplace + '\n' +
                " novelState= " + novelState + '\n' +
                " novelStateReplace= " + novelStateReplace + '\n' +
                " novelStateEnd= " + novelStateEnd + '\n' +
                " novelStateNotEnd= " + novelStateNotEnd + '\n' +
                " novelCategory= " + novelCategory + '\n' +
                " novelCategoryReplace= " + novelCategoryReplace + '\n' +
                " novelDescription= " + novelDescription + '\n' +
                " novelDescriptionReplace= " + novelDescriptionReplace + '\n' +
                " novelCatalogUrl= " + novelCatalogUrl + '\n' +
                " novelCatalogType= " + novelCatalogType + '\n' +
                " novelCatalogNext= " + novelCatalogNext + '\n' +
                " novelCatalogNextText= " + novelCatalogNextText + '\n' +
                " novelCatalogList= " + novelCatalogList + '\n' +
                " novelCatalogListText= " + novelCatalogListText + '\n' +
                " novelChapterUrl= " + novelChapterUrl + '\n' +
                " novelChapterTitle= " + novelChapterTitle + '\n' +
                " novelChapterTitleReplace= " + novelChapterTitleReplace + '\n' +
                " novelContent= " + novelContent + '\n' +
                " novelContentReplace= " + novelContentReplace + '\n' +
                " novelContentType= " + novelContentType + '\n' +
                " novelContentNext= " + novelContentNext + '\n' +
                " novelContentNextUrl= " + novelContentNextUrl + '\n' +
                " novelChapterNext= " + novelChapterNext + '\n' +
                " novelChapterNextType= " + novelChapterNextType + '\n' +
                " novelChapterNextText= " + novelChapterNextText + '\n' +
                " gmtCreate= " + gmtCreate + '\n' +
                " gmtModified= " + gmtModified + '\n' +
                " isDelete= " + isDelete + '\n' +
                '}';
    }
}
