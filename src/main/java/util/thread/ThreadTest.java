package util.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import dao.DaoFactory;
import dao.WebsiteConfigDao;
import dbc.ConnectFactory;
import entity.ContentDTO;
import entity.NovelDTO;
import entity.UrlDTO;
import entity.WebsiteConfigPO;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import spider.GetPage;
import spider.PageParse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 线程学习的实践类
 * create at 2019-04-25 by MaXin
 */
public class ThreadTest {
    public static void main(String[] args) throws Exception {

        SqlSession session = ConnectFactory.connectMysql();

        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
        WebsiteConfigPO websiteConfig = websiteConfigDao.findByID(9L);
        PageParse pageParse = new PageParse(websiteConfig);

        TimeInterval timer = DateUtil.timer();


        NovelDTO novelDTO = pageParse.getNovelDTO("https://m.biquge.info/10_10582/");
        System.out.println(timer.interval());
        System.out.println(novelDTO);

//        List<WebsiteConfigPO> list = websiteConfigDao.getAll();
//        for (WebsiteConfigPO configPO : list) {
//
//        }
    }




}
