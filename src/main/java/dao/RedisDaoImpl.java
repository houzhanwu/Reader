package dao;

import dbc.ConnectFactory;
import entity.UrlDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * create at 2019-05-03 by MaXin
 */
public class RedisDaoImpl implements RedisDao {
    private static final Logger logger = LoggerFactory.getLogger(RedisDaoImpl.class);
    private Jedis jedis;
    /**
     * redis中存储页面数据的数据库
     */
    private final static int PAGE_DATABASE = 1;
    /**
     * redis中存储已经获取到的页面数据的数据库
     */
//    private final static String PAGE_SET = "page";
    /**
     * 存储解析到的小说链接的数据库
     */
    private final static int NOVEL_DATABASE = 2;

    public RedisDaoImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void deleteAll() {
        jedis.flushAll();
        logger.debug("清空redis数据库");
    }

    @Override
    public void deletePageDatabase() {
        jedis.select(PAGE_DATABASE);
        jedis.flushDB();
    }

    @Override
    public void deleteAllPage(Long id) {
        jedis.select(PAGE_DATABASE);
        jedis.del(id.toString());
    }

    @Override
    public boolean existUrl(Long id, String url) {
        jedis.select(PAGE_DATABASE);
        if (!jedis.exists(id.toString()))
            return false;
        Boolean exists = jedis.sismember(id.toString(), url);
        logger.debug("URL -> "+ url + "是否存在：" + exists);
        return exists;
    }

    @Override
    public void insertPage(Long id, String url) {
        jedis.select(PAGE_DATABASE);
        Long i = jedis.sadd(id.toString(), url);
    }



    @Override
    public boolean deleteAllNovel(Long id) {
        jedis.select(NOVEL_DATABASE);
        Long del = jedis.del(id.toString());
        logger.debug("删除网站ID为："+id + "对应的小说链接信息");
        if (del == 1)
            return true;
        return false;
    }



    @Override
    public boolean insertNovel(Long websiteId, String url) {
        jedis.select(NOVEL_DATABASE);
        Long i = jedis.sadd(websiteId.toString(), url);
        logger.debug("增加解析到的novel连接：URL -> " + url);
        if (i == 1L)
            return true;
        return false;
    }

    @Override
    public Collection<UrlDTO> insertNovel(Long websiteId, Collection<UrlDTO> collection) {
        List<UrlDTO> list = new ArrayList<>();
        jedis.select(NOVEL_DATABASE);
        for (UrlDTO url : collection) {
            Long i = jedis.sadd(websiteId.toString(), url.getUrl());
            if (i >= 1 ){
                list.add(url);
                logger.debug("新增的novel连接：URL -> " + url.getUrl());
            }
        }
        return list;
    }

    @Override
    public Set<String> getAllNovel(Long websiteId) {
        jedis.select(NOVEL_DATABASE);
        Set<String> set = jedis.smembers(websiteId.toString());
        logger.debug("获取所有的Novel连接。数量为：" + set.size());
        return set;
    }
}
