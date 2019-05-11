package dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 数据层工厂类
 * 使业务层不需要知道数据层的具体子类
 */
public class DaoFactory {

    private final static Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    public static NovelDao getNovelDao (SqlSession session) {
        logger.debug("获取Novel操作类");
        return new NovelDaoImplByMybatis(session);
    }

    public static WebsiteConfigDao getWebsiteConfigDao (SqlSession session) {
        logger.debug("获取WebsiteConfig操作类");
        return new WebsiteConfigDaoImpByMybatis(session);
    }
    public static RedisDao getRedisDao (Jedis jedis){
        logger.debug("获取Redis操作类");
        return new RedisDaoImpl(jedis);
    }
    public static WebsiteNovelDao getWebsiteNovelDao (Long websiteId, SqlSession session){
        logger.debug("获取websiteNovelDao操作类");
        return new WebsiteNovelDaoImplByMybatis(websiteId, session);
    }
}
