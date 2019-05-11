package spider;

import dao.DaoFactory;
import dao.RedisDao;
import dao.WebsiteConfigDao;
import dbc.ConnectFactory;
import entity.UrlDTO;
import entity.WebsiteConfigPO;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.*;

/**
 * 爬虫的控制器，此类中进行主线程控制，并创建线程来连接解析数据
 * 此类有很大错误，废弃
 * @deprecated
 * create at 2019-04-22 by MaXin
 */
public class SpiderController {

    private final Logger logger = LoggerFactory.getLogger(SpiderController.class);

    private final WebsiteConfigPO websiteConfig;
    private final PageParse pageParse;
    private int indexPage = 0;  //获取页面的数量
    private final Deque<UrlDTO> needPage = new LinkedList<>();  //存放需要获取页面的链接
    private final Object LOCK_PAGE = new Object();  //需要获取页面的队列的处理的锁，保证一次只有一个线程对其操作
    private int indexParse = 0; //存放需要解析的页面
    private final Deque<UrlDTO> needParse = new LinkedList<>(); //需要解析的页面的数量
    private final Object LOCK_PARSE = new Object(); //需要解析页面的队列的处理的锁，保证一次只有一个线程对其操作
    private int indexFail =0;//页面获取失败的链接，或者已经获取的链接
    private final Deque<UrlDTO> pageFail = new LinkedList<>();
    private final Object LOCK_FAIL = new Object();
    private final Set<UrlDTO> novelList = new LinkedHashSet<UrlDTO>();//放置解析到的章节页面
    private final Object LOCK_NOVEL = new Object();

    private int flagDownload =0;//循环次数的标记
    private final int MAX_FLAG = 100;//如果有100次空，则结束运行
    private int flagParse =0;
//    private Queue<UrlDTO>
    /**
     * 线程数量
     */
    public static final int DEFAULT_SIZE = 10;

    private final int maxSize;


    public SpiderController (WebsiteConfigPO websiteConfig){
        this(websiteConfig , DEFAULT_SIZE);
    }

    public SpiderController(WebsiteConfigPO websiteConfig, int size) {
        this.websiteConfig = websiteConfig;
        this.maxSize = size;
        pageParse = new PageParse(websiteConfig);
    }

    public static void main(String[] args){
        SqlSession session = ConnectFactory.connectMysql();
        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
        List<WebsiteConfigPO> list = null;
        try {
            list = websiteConfigDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (WebsiteConfigPO websiteConfigPO : list) {
            new Thread(()->{
//                LOGGER.debug("对网站创建爬虫控制器：" + websiteConfigPO.getWebsiteName());
                SpiderController spiderController = new SpiderController(websiteConfigPO);
                spiderController.controlSpider();
            }).start();
        }
    }


    public void controlSpider() {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(websiteConfig.getWebsiteUrl());
        needPage.push(urlDTO);
        indexPage++;
        Thread thread1 = new Thread(() -> {
            while (flagDownload < MAX_FLAG){
                download();
                if (flagDownload > 0) {
                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            while (flagParse < MAX_FLAG){
                parsePage();
                if (flagParse > 0) {
                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //解析获取到了小说网站的文章列表
        Jedis jedis = ConnectFactory.connectRedis();
        RedisDao redis = DaoFactory.getRedisDao(jedis);
        redis.insertNovel(websiteConfig.getWebsiteId(), novelList);
        jedis.close();
    }

    public void download (){
        UrlDTO urlDTO = null;
        synchronized (LOCK_PAGE) {
            if (indexPage > 0) {
                flagDownload = 0;
                urlDTO = needPage.removeFirst();
                indexPage--;
            } else {
                flagDownload++;
                return;
            }
        }
        if (urlDTO != null) {
            Document document = GetPage.getDocument(websiteConfig.getWebsiteId(), urlDTO.getUrl());
            if (document != null) {
//                urlDTO.setPage(document);
                System.out.println(urlDTO);
                synchronized (LOCK_PARSE) {
                    needParse.addLast(urlDTO);
                    indexParse++;
                }
            } else {
                synchronized (LOCK_FAIL) {
                    pageFail.addLast(urlDTO);
                    indexFail++;
                }
            }
        }
    }

    public void parsePage (){
        UrlDTO urlDTO = null;
        synchronized (LOCK_PARSE){
            if (indexParse > 0) {
                flagParse = 0;
                urlDTO = needParse.removeFirst();
                indexParse--;
            } else {
                flagParse++;
                return;
            }
        }
        if (urlDTO != null) {
            try {
//                Map<String, Set<UrlDTO>> map = pageParse.getListUrl(urlDTO.getPage());
//                Set<UrlDTO> novel = map.get("novel");
//                Set<UrlDTO> other = map.get("other");
//                synchronized (LOCK_PAGE) {
//                    needPage.addAll(other);
//                    indexPage = indexPage + other.size();
//                }
//                synchronized (LOCK_NOVEL) {
//                    novelList.addAll(novel);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
