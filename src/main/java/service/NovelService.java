package service;

import com.alibaba.fastjson.JSONObject;
import dao.DaoFactory;
import dao.NovelDao;
import dao.WebsiteConfigDao;
import dao.WebsiteNovelDao;
import dbc.ConnectFactory;
import entity.NovelPO;
import entity.WebsiteConfigPO;
import entity.WebsiteNovelPO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.SpiderControllerSingle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于小说相关的所有功能
 *
 * 1. 模糊查询小说，根据给定的作者名或者小说名操作
 * 2. 设定定时器，这个应该在类被加载时就调用
 * 3. 根据传入的小说ID，返回对应的小说信息，和小说章节信息，和小说网站ID
 * 4. 根据传入的小说URL和小说网站ID，解析对应的小说内容信息，并返回下一章连接和当前章节内容
 * create at 2019-05-10 by MaXin
 */
public class NovelService {
    private final static Logger logger = LoggerFactory.getLogger(NovelService.class);

//    static{
//        SqlSession session = ConnectFactory.connectMysql();
//        try {
//            WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
//            List<WebsiteConfigPO> list = null;
//            try {
//                list = websiteConfigDao.getAll();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            for (WebsiteConfigPO websiteConfigPO : list) {
//                new Thread(()->{
//                    SpiderControllerSingle spiderControllerSingle = new SpiderControllerSingle(websiteConfigPO);
//                    spiderControllerSingle.controlSpider();
//                }, websiteConfigPO.getWebsiteName()+"的线程").start();
//            }
//        }finally {
//            session.close();
//        }
//    }
    public static List<NovelPO> findByTitle(String title){
        SqlSession session = ConnectFactory.connectMysql();
        try {
            NovelDao novelDao = DaoFactory.getNovelDao(session);
            List<NovelPO> titleList = novelDao.findByTitle(title);
            return titleList;
        } catch (SQLException e) {
            logger.error("数据库查找标题\"" + title +"\"出错-----> \n",e);
        } finally {
            session.close();
        }
        return null;
    }
    public static List<NovelPO> findByAuthor(String author){
        SqlSession session = ConnectFactory.connectMysql();
        try {
            NovelDao novelDao = DaoFactory.getNovelDao(session);
            List<NovelPO> titleList = novelDao.findByAuthor(author);
            return titleList;
        } catch (SQLException e) {
            logger.error("数据库查找作者\"" + author +"\"出错-----> \n",e);
        } finally {
            session.close();
        }
        return null;
    }

    public static NovelPO findNovelById(Long novelId){
        SqlSession session = ConnectFactory.connectMysql();
        try {
            NovelDao novelDao = DaoFactory.getNovelDao(session);
            NovelPO novel = novelDao.findById(novelId);
            return novel;
        } catch (SQLException e) {
            logger.error("数据库查找id为\"" + novelId +"\"出错-----> \n",e);
        } finally {
            session.close();
        }
        return null;
    }

    public static WebsiteNovelPO findCatalogById(Long novelId, Long websiteId){
        SqlSession session = ConnectFactory.connectMysql();
        try {
            WebsiteNovelDao websiteNovelDao = DaoFactory.getWebsiteNovelDao(websiteId, session);
            WebsiteNovelPO websiteNovelPO = websiteNovelDao.findByNovelId(novelId);
            return websiteNovelPO;
        } catch (SQLException e) {
            logger.error("数据库查找id为\"" + novelId +"\"出错-----> \n",e);
        } finally {
            session.close();
        }
        return null;
    }

}
