package spider;

import com.alibaba.fastjson.JSONObject;
import dao.*;
import dbc.ConnectFactory;
import entity.*;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * version 2.0 的爬虫控制器
 * 网站爬虫的单线程版本，主要因为多线程版总是停在莫名其妙的地方
 * create at 2019-05-04 by MaXin
 */
public class SpiderControllerSingle {

    private final WebsiteConfigPO websiteConfig;
    //    private final PageParse pageParse;
    private final Logger logger = LoggerFactory.getLogger(SpiderControllerSingle.class);
    /**
     * 默认的线程数量
     */
    public final static int DEFAULT = 10;

    /**
     * 输入的线程数量
     */
    private final int threadNum;

    /**
     * 存储需要下载的URL
     */
    private Deque<UrlDTO> urlList = new LinkedList<>();

    /**
     * 存储解析到的小说连接
     */
    private Deque<UrlDTO> novelList = new LinkedList<>();

    /**
     * 允许为空的次数
     */
    private final static int MAX_NUM = 20;


    public SpiderControllerSingle(WebsiteConfigPO websiteConfigPO) {
        this(websiteConfigPO, DEFAULT);
    }

    public SpiderControllerSingle(WebsiteConfigPO websiteConfigPO, int threadNum) {
        this.websiteConfig = websiteConfigPO;
//        this.pageParse = new PageParse(websiteConfigPO);
        this.threadNum = threadNum;
    }


    /**
     * 爬虫主控制台
     */
    public void controlSpider() {
        WebsiteConfigPO websiteConfigPO;
        synchronized (websiteConfig) {
            websiteConfigPO = websiteConfig;
        }
        String url = websiteConfigPO.getWebsiteUrl();
        UrlDTO dto = new UrlDTO(url, "");
        Jedis jedis = ConnectFactory.connectRedis();
        RedisDao redis = DaoFactory.getRedisDao(jedis);
        redis.deleteAllPage(websiteConfigPO.getWebsiteId());
        redis.deleteAllNovel(websiteConfigPO.getWebsiteId());
        logger.debug("清空页面数据仓库：" + websiteConfigPO.getWebsiteId());
        jedis.close();
        urlList.push(dto);
        //循环判断
        ThreadPoolExecutor threadPool1 = new ThreadPoolExecutor(20, 40, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        ThreadPoolExecutor threadPool2 = new ThreadPoolExecutor(20, 40, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < threadNum; i++) {
            threadPool1.execute(() -> {
                download();
            });
            threadPool2.execute(() -> {
                parseNovel();
            });
        }
//        while (!threadPool.isShutdown()){
//            try {
//                Thread.sleep(10_000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

    /**
     * 如果需要下载的列表为空，表示已经爬完所有的页面链接
     *
     * @throws Exception
     */
    public void download() {
        WebsiteConfigPO websiteConfigPO;
        synchronized (websiteConfig) {
            websiteConfigPO = websiteConfig;
        }
        Jedis jedis = ConnectFactory.connectRedis();
        RedisDao redisDao = DaoFactory.getRedisDao(jedis);
        PageParse pageParse = new PageParse(websiteConfigPO);
        try {

            //记录下载列表为空的次数，达到10次则停止运行
            int i = 0;
            while (i <= MAX_NUM) {
                if (i > 0 && i <= MAX_NUM) {
                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        logger.error("下载页面时获取到中断信号：-----------------> \n", e);
                    }
                }
                String url;
                synchronized (urlList) {
                    if (urlList.isEmpty()) {
                        i++;
                        continue;
                    }
                    i = 0;
                    url = urlList.removeFirst().getUrl();
                }

                Document document = GetPage.getDocument(websiteConfigPO.getWebsiteId(), url);
                if (document == null)
                    continue;
                Map<String, Set<UrlDTO>> map = null;
                try {
                    map = pageParse.getListUrl(document);
                } catch (Exception e) {
                    logger.error("下载页面时错误：-----------------> \n", e);
                    continue;
                }
                Set<UrlDTO> novel = map.get("novel");
                Set<UrlDTO> other = map.get("other");
                synchronized (urlList) {
                    urlList.addAll(other);
                }
                Collection<UrlDTO> urlDtoS = redisDao.insertNovel(websiteConfigPO.getWebsiteId(), novel);
                if (!urlDtoS.isEmpty()) {
                    synchronized (novelList) {
                        novelList.addAll(urlDtoS);
                        logger.debug("小说连接增加成功");
                    }
                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        } finally {
            jedis.close();
        }
    }

    public void parseNovel() {
        try {
            //先睡1秒，使下载进程能够先运行
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            logger.error("解析页面获取到中断：-----------------> \n", e);
        }
        WebsiteConfigPO websiteConfigPO;
        synchronized (websiteConfig) {
            websiteConfigPO = websiteConfig;
        }
        SqlSession session = ConnectFactory.connectMysql();
        NovelDao novelDao = DaoFactory.getNovelDao(session);
        WebsiteNovelDao websiteNovelDao = DaoFactory.getWebsiteNovelDao(websiteConfigPO.getWebsiteId(), session);
        PageParse pageParse = new PageParse(websiteConfigPO);
        int i = 0;
        while (i <= MAX_NUM) {
            if (i > 0 && i <= MAX_NUM) {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    logger.error("解析页面获取到中断信号：-----------------> \n", e);
                }
            }
            UrlDTO urlDTO = null;
            synchronized (novelList) {
                logger.debug("解析方法获取到novelList");
                if (novelList.isEmpty()) {
                    logger.debug("novelList为空");
                    i++;
                    continue;
                }
                i = 0;
                logger.debug("novelList不为空");
                urlDTO = novelList.removeFirst();
                logger.debug("获取到小说连接 -> " + urlDTO.getUrl());
            }
            try {
                NovelDTO novelDTO = pageParse.getNovelDTO(urlDTO.getUrl());
                logger.debug("获取到小说：" + novelDTO.getAuthor() + "->" + novelDTO.getTitle());
                NovelPO novelPO = new NovelPO();
                novelPO.setTitle(novelDTO.getTitle());
                novelPO.setAuthor(novelDTO.getAuthor());
                novelPO.setCategory(novelDTO.getCategory());
                novelPO.setDescription(novelDTO.getDescription());
                novelPO.setIsEnd(novelDTO.getIsEnd());
                java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
                novelPO.setGmtCreate(date);
                novelPO.setGmtModified(date);
                Long id = novelDao.existByNovelPO(novelPO);
                if (id == null) {
                    id = novelDao.doInsert(novelPO);
                }
                WebsiteNovelPO websiteNovelPO = new WebsiteNovelPO();
                String json = JSONObject.toJSONString(novelDTO.getChapter());
                websiteNovelPO.setNovelId(id);
                websiteNovelPO.setChapter(json);
                websiteNovelPO.setGmtCreate(date);
                websiteNovelPO.setGmtModified(date);
                WebsiteNovelPO websiteNovelByNovelId = websiteNovelDao.findByNovelId(id);

                if (websiteNovelByNovelId == null)
                    websiteNovelDao.insert(websiteNovelPO);
                else {
                    websiteNovelPO.setWebsiteNovelId(websiteNovelByNovelId.getWebsiteNovelId());
                    websiteNovelPO.setGmtCreate(websiteNovelByNovelId.getGmtCreate());
                    websiteNovelDao.update(websiteNovelPO);
                }
            } catch (Exception e) {
                logger.error("解析页面时错误：-----------------> \n", e);
            } finally {
                session.commit();
            }
        }
        session.close();

    }


}
