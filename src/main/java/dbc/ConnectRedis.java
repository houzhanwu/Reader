package dbc;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.setting.dialect.Props;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.InputStream;

/**
 * Redis数据库连接类
 * create at 2019-04-16 by MaXin
 */
public class ConnectRedis {

    private static final String RESOURCE = "redis.properties";
    private static final String URL ;
    private static final int PORT;
    private static final String PASSWORD;
    private static JedisPool pool ;

    static {
        Props props = new Props("redis.properties");
        URL = props.getStr("URL");
        PORT = props.getInt("PORT");
        PASSWORD = props.getStr("PASSWORD");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxTotal(100);
        config.setMinIdle(3);
        config.setMaxWaitMillis(100_000);
        pool = new JedisPool(config,URL , PORT, 2_000, PASSWORD);
    }

    public static Jedis getConnection (){
        return pool.getResource();
    }

    public static void releaseConnection (Jedis jedis){
        jedis.close();
    }
}
