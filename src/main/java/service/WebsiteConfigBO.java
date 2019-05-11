package service;

import dao.DaoFactory;
import dao.WebsiteConfigDao;
import dbc.ConnectMysqlByMybatis;
import entity.WebsiteConfigPO;
import org.apache.ibatis.session.SqlSession;
import util.StringUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * 对网站配置的业务类实现
 * create at 2019-04-17 by MaXin
 *
 * 需要完成的操作：
 * 插入网站配置
 * 设置网站配置
 * 对要传入数据库的配置进行空字符串替换
 * 网站配置查重
 */
public class WebsiteConfigBO {

    /**
     * 通过URL找到对应的网站配置
     * @param url
     * @return 返回网站配置，如果没有对应网站，则返回一个null
     */
    public static WebsiteConfigPO findWebsiteConfigByUrl (String url){
        SqlSession session = ConnectMysqlByMybatis.getSession();
        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
        WebsiteConfigPO websiteConfigPO = null;
        try {
            List<WebsiteConfigPO> websites = websiteConfigDao.getAllWebsite();
            for (WebsiteConfigPO website : websites) {
                if (url.matches(website.getWebsite())){
                    websiteConfigPO = websiteConfigDao.findByID(website.getWebsiteId());
                    continue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库执行出错");
        }finally {
            session.close();
        }

        return websiteConfigPO;
    }


    /**
     * 进行null的转换
     * @param website
     */
    public static void transNull (WebsiteConfigPO website) throws Exception{
        if (website.getWebsite() == null )
            website.setWebsite("");
        if (website.getWebsiteName() ==null )
            website.setWebsiteName("");
        if (website.getWebsiteId() == null)
            website.setWebsiteId(new java.util.Date().getTime());
        if(website.getNovelDetail() == null )
            website.setNovelDetail("");
        if (website.getNovelTitle() == null )
            website.setNovelTitle("");
        if (website.getNovelTitleReplace() == null )
            website.setNovelTitleReplace("");
        if (website.getNovelAuthor() == null )
            website.setNovelAuthor("");
        if (website.getNovelAuthorReplace() == null )
            website.setNovelAuthorReplace("");
        if (website.getNovelDescription() == null )
            website.setNovelDescription("");
        if (website.getNovelDescriptionReplace() == null )
            website.setNovelDescriptionReplace("");
        if (website.getNovelCatalogUrl() == null )
            website.setNovelCatalogUrl("");
        if (website.getNovelCatalogType() == null )
            throw new Exception("传入的数据中含有非法数据，请检查代码 \n 目录页类型为空");
        if (website.getNovelCatalogNext() == null )
            website.setNovelCatalogNext("");
        if (website.getNovelCatalogNextText() == null )
            website.setNovelCatalogNextText("");
        if (website.getNovelCatalogList() == null )
            website.setNovelCatalogList("");
        if (website.getNovelCatalogListText() == null )
            website.setNovelCatalogListText("");
        if (website.getNovelChapterUrl() == null )
            website.setNovelCatalogUrl("");
        if (website.getNovelChapterTitle() == null )
            website.setNovelChapterTitle("");
        if (website.getNovelChapterTitleReplace() == null )
            website.setNovelChapterTitleReplace("");
        if (website.getNovelContent() == null )
            website.setNovelContent("");
        if (website.getNovelContentReplace() == null )
            website.setNovelContentReplace("");
        if (website.getNovelContentNext() == null )
            website.setNovelContentNext("");
        if (website.getNovelContentNextUrl() == null )
            website.setNovelContentNextUrl("");
        if (website.getNovelChapterNext() == null )
            website.setNovelChapterNext("");
        if (website.getNovelContentType() == Integer.valueOf(1) &&website.getNovelChapterNextType() == null )
            throw new Exception("传入的数据中含有非法数据，请检查代码 \n 内容页类型为空");
        else website.setNovelChapterNextType(0);
        if (website.getNovelChapterNextText() == null )
            website.setNovelChapterNextText("");
        if (website.getGmtCreate() == null)
            website.setGmtCreate(new Date(new java.util.Date().getTime()));
        if (website.getGmtModified() == null)
            website.setGmtModified(new Date(new java.util.Date().getTime()));
    }

    public static Long inspectConfig (WebsiteConfigPO website) throws Exception{
        if (website == null )
            throw new Exception("网站内容为空，检查代码");
        transNull(website);
        if (StringUtil.isNull(website.getWebsite()))
            throw new Exception("网站根目录未配置");
        Long id = new java.util.Date().getTime();
        website.setWebsiteId(id);
        return id;
    }

    /**
     * 对website进行查重工作、空字符串转换，将其插入数据库中。
     * @param website 送入的数据应为逻辑正确的
     * @return 如果数据无问题且插入成功，则返回对应的网站ID,否则返回null
     */

    public static Long insertWebsiteConfig (WebsiteConfigPO website) {
        SqlSession session = ConnectMysqlByMybatis.getSession();
        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
        try {
            if (inspectConfig(website) != null)
            if (websiteConfigDao.insert(website)){
                session.commit();
                return website.getWebsiteId();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 返回所有的网站的配置
     * @return 如果出错，返回null
     */
    public static List<WebsiteConfigPO> findAllWebsiteConfig (){
        SqlSession session = ConnectMysqlByMybatis.getSession();
        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
        try {
            List<WebsiteConfigPO> websiteConfigDaoAll = websiteConfigDao.getAll();
            return websiteConfigDaoAll;
        } catch (SQLException e) {
            System.out.println("数据库连接错误");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public static boolean insertBatch (List<WebsiteConfigPO> list){
        SqlSession session = ConnectMysqlByMybatis.getSession();
        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
        try {
            for (WebsiteConfigPO website : list) {
                inspectConfig(website);
            }
            if (websiteConfigDao.insertList(list)){
                session.commit();
                return true;
            }
        }catch (Exception e){
            System.out.println("批量插入网站配置出错");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
