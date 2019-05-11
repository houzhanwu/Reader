package spider;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import dao.RedisDao;
import dao.RedisDaoImpl;
import dbc.ConnectFactory;
import entity.UrlDTO;
import entity.WebsiteConfigPO;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.URL;
import java.util.*;


/**
 * 获取网页页面信息
 * 所有爬虫的网络连接都通过此类
 */
public class GetPage {

    private final static Logger logger = LoggerFactory.getLogger(GetPage.class);

    public static List<UrlDTO> getListUrl(Document document) {
        List<UrlDTO> urls = new ArrayList<UrlDTO>();
        Elements link = document.select("a");
        for (Element element : link) {
            String url = element.attr("abs:href");
            String text = element.text();
            if ("".equals(url))
                continue;
            UrlDTO urlDTO = new UrlDTO(url, text);
            urls.add(urlDTO);
        }
        link = document.select("option");
        for (Element element : link) {
            String url = element.attr("abs:value");
            if ("".equals(url))
                continue;
            String text = element.text();
            UrlDTO urlDTO = new UrlDTO(url, text);
            urls.add(urlDTO);
        }

        return urls;
    }

    /**
     * 如果返回值为null，则表示网页未获取到，或者
     *
     * @param url
     * @return
     */

    public static Document getDocument(Long id, String url) {
        Jedis jedis = ConnectFactory.connectRedis();
        try {
            RedisDao redisDao = new RedisDaoImpl(jedis);
            if (redisDao.existUrl(id, url))
                return null;
            Document document = Jsoup.connect(url).get();
            String page = document.outerHtml();
            redisDao.insertPage(id, url);
            jedis.close();
            return document;
        } catch (IOException e) {
            logger.error("页面获取时错误：-----------------> \n", e);
            return null;
        } finally {
            jedis.close();
        }
    }

}